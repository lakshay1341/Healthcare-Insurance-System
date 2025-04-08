package in.lakshay.service;

import in.lakshay.bindings.EligibilityDetailsOutput;
import in.lakshay.entity.*;
import in.lakshay.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EligibilityDeterminationMgmtServiceImpl implements IEligibilityDeterminationMgmtService {

    @Autowired
    private IDcCaseRepository caseRepo;

    @Autowired
    private IPlanRepository planRepo;

    @Autowired
    private IDcInComeRepository incomeRepo;

    @Autowired
    private IDcChildrenRepository childrenRepo;

    @Autowired
    private IApplicationRegistrationRepository citizenRepo;

    @Autowired
    private IDcEducationRepository educationRepo;

    @Autowired
    private ICOTriggerRepository triggerRepo;

    @Autowired
    private IEligibilityDeterminationRepository elgiRepo;

    @Override
    public EligibilityDetailsOutput determineEligibility(Integer caseNo) {
        Integer appId = null;
        Integer planId = null;

        // Retrieve planId and appId based on caseNo
        Optional<DcCaseEntity> caseEntityOptional = caseRepo.findById(caseNo);
        if (caseEntityOptional.isPresent()) {
            DcCaseEntity caseEntity = caseEntityOptional.get();
            planId = caseEntity.getPlanId();
            appId = caseEntity.getAppId();
        }

        // Retrieve plan name using planId
        String planName = null;
        Optional<PlanEntity> planEntityOptional = planRepo.findById(planId);
        if (planEntityOptional.isPresent()) {
            planName = planEntityOptional.get().getPlanName();
        }

        // Calculate citizen age from date of birth using appId
        Optional<CitizenAppRegistrationEntity> citizenEntityOptional = citizenRepo.findById(appId);
        int citizenAge = 0;
        String citizenName = null;
        Long citizenSSN = 0L;

        if (citizenEntityOptional.isPresent()) {
            CitizenAppRegistrationEntity citizenEntity = citizenEntityOptional.get();
            LocalDate dob = citizenEntity.getDob();
            citizenAge = Period.between(dob, LocalDate.now()).getYears();
            citizenName = citizenEntity.getFullName();
            citizenSSN = citizenEntity.getSsn();
        }

        // Determine eligibility based on plan conditions
        EligibilityDetailsOutput eligibilityOutput = applyPlanConditions(caseNo, planName, citizenAge);

        // Set the citizen name (remains null as per current logic)
        eligibilityOutput.setHolderName(citizenName);

        // Save the eligibility details entity
        EligibilityDetailsEntity eligibilityEntity = new EligibilityDetailsEntity();
        BeanUtils.copyProperties(eligibilityOutput, eligibilityEntity);
        eligibilityEntity.setCaseNo(caseNo);
        eligibilityEntity.setHolderSSN(citizenSSN);
        elgiRepo.save(eligibilityEntity);

        // Save the co-triggers entity
        CoTriggersEntity triggersEntity = new CoTriggersEntity();
        triggersEntity.setCaseNo(caseNo);
        triggersEntity.setTriggerStatus("pending");
        triggerRepo.save(triggersEntity);

        return eligibilityOutput;
    }

    private EligibilityDetailsOutput applyPlanConditions(Integer caseNo, String planName, int citizenAge) {
        EligibilityDetailsOutput output = new EligibilityDetailsOutput();
        output.setPlanName(planName);

        // Retrieve income details for the case
        DcIncomeEntity incomeEntity = incomeRepo.findByCaseNo(caseNo);
        double empIncome = incomeEntity.getEmpIncome();
        double propertyIncome = incomeEntity.getPropertyIncome();

        // Process conditions for each plan type
        if (planName.equalsIgnoreCase("SNAP")) {
            if (empIncome < 300) {
                output.setPlanStatus("Approved");
                output.setBenefitAmt(200.0);
            } else {
                output.setPlanStatus("Denied");
                output.setDenialReason("High Income");
            }
        } else if (planName.equalsIgnoreCase("CCAP")) {
            boolean hasEligibleKids = false;
            boolean allKidsUnderLimit = true;

            List<DcChildrenEntity> childrenList = childrenRepo.findByCaseNo(caseNo);
            if (!childrenList.isEmpty()) {
                hasEligibleKids = true;
                for (DcChildrenEntity child : childrenList) {
                    int kidAge = Period.between(child.getChildDOB(), LocalDate.now()).getYears();
                    if (kidAge > 16) {
                        allKidsUnderLimit = false;
                        break;
                    }
                }
            }

            if (empIncome < 300 && hasEligibleKids && allKidsUnderLimit) {
                output.setPlanStatus("Approved");
                output.setBenefitAmt(300.0);
            } else {
                output.setPlanStatus("Denied");
                output.setDenialReason("CCAP rules are not satisfied");
            }
        } else if (planName.equalsIgnoreCase("MEDCARE")) {
            if (citizenAge >= 65) {
                output.setPlanStatus("Approved");
                output.setBenefitAmt(350.0);
            } else {
                output.setPlanStatus("Denied");
                output.setDenialReason("MEDCARE rules are not satisfied");
            }
        } else if (planName.equalsIgnoreCase("MEDAID")) {
            if (empIncome < 300 && propertyIncome == 0) {
                output.setPlanStatus("Approved");
                output.setBenefitAmt(200.0);
            } else {
                output.setPlanStatus("Denied");
                output.setDenialReason("MEDAID rules are not satisfied");
            }
        } else if (planName.equalsIgnoreCase("CAJW")) {
            DcEducationEntity educationEntity = educationRepo.findByCaseNo(caseNo);
            int passOutYear = educationEntity.getPassOutYear();

            if (empIncome == 0 && passOutYear <= LocalDate.now().getYear()) {
                output.setPlanStatus("Approved");
                output.setBenefitAmt(300.0);
            } else {
                output.setPlanStatus("Denied");
                output.setDenialReason("CAJW rules are not satisfied");
            }
        } else if (planName.equalsIgnoreCase("QHP")) {
            if (citizenAge >= 25) {
                output.setPlanStatus("Approved");
            } else {
                output.setPlanStatus("Denied");
                output.setDenialReason("QHP rules are not satisfied");
            }
        }

        // If approved, set the plan's start and end dates
        if (output.getPlanStatus().equalsIgnoreCase("Approved")) {
            output.setPlanStartDate(LocalDate.now());
            output.setPlanEndDate(LocalDate.now().plusYears(2));
        }

        return output;
    }
}
