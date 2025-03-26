package in.lakshay.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.lakshay.bindings.ChildInputs;
import in.lakshay.bindings.CitizenAppRegistrationInputs;
import in.lakshay.bindings.DcSummaryReport;
import in.lakshay.bindings.EducationInputs;
import in.lakshay.bindings.IncomeInputs;
import in.lakshay.bindings.PlanSelectionInputs;
import in.lakshay.entity.CitizenAppRegistrationEntity;
import in.lakshay.entity.DcCaseEntity;
import in.lakshay.entity.DcChildrenEntity;
import in.lakshay.entity.DcEducationEntity;
import in.lakshay.entity.DcIncomeEntity;
import in.lakshay.entity.PlanEntity;
import in.lakshay.repository.IApplicationRegistrationRepository;
import in.lakshay.repository.IDcCaseRepository;
import in.lakshay.repository.IDcChildrenRepository;
import in.lakshay.repository.IDcEducationRepository;
import in.lakshay.repository.IDcInComeRepository;
import in.lakshay.repository.IPlanRepository;

@Service
public class DcMgmtServiceImpl implements IDcMgmtService {

    @Autowired
    private IDcCaseRepository caseRepo;

    @Autowired
    private IApplicationRegistrationRepository citizenAppRepo;

    @Autowired
    private IPlanRepository planRepo;

    @Autowired
    private IDcInComeRepository incomeRepo;

    @Autowired
    private IDcEducationRepository educationRepo;

    @Autowired
    private IDcChildrenRepository childrenRepo;

    @Override
    public Integer generateCaseNo(Integer appId) {
        // Load Citizen Data
        Optional<CitizenAppRegistrationEntity> appCitizen = citizenAppRepo.findById(appId);
        if (appCitizen.isPresent()) {
            DcCaseEntity caseEntity = new DcCaseEntity();
            caseEntity.setAppId(appId);
            return caseRepo.save(caseEntity).getCaseNo(); // save operation
        }
        return 0;
    }

	@Override
	public List<String> showAllPlanNames() {
		// TODO Auto-generated method stub
		List<PlanEntity> plansList=planRepo.findAll();
		List<String> planNamesList=plansList.stream().map(plan->plan.getPlanName()).toList();
		return planNamesList;
	}

	@Override
	public Integer savePlanSelection(PlanSelectionInputs plan) {
		// TODO Auto-generated method stub
		//load DcCaseEntity objects
		Optional<DcCaseEntity> opt=caseRepo.findById(plan.getCaseNo());
		if(opt.isPresent()) {
			DcCaseEntity caseEntity=opt.get();
			caseEntity.setPlanId(plan.getPlanId());
			// update the dccaseentity with planid
			caseRepo.save(caseEntity); // update obj operation
			return caseEntity.getCaseNo();
		}
		return 0;
	}

	@Override
	public Integer saveIncomeDetails(IncomeInputs income) {
	    // Convert binding obj data to Entity class obj data
	    DcIncomeEntity incomeEntity = new DcIncomeEntity();
	    BeanUtils.copyProperties(income, incomeEntity);
	    
	    // Save the income details
	    incomeRepo.save(incomeEntity);
	    
	    // Return caseNo
	    return income.getCaseNo();
	}


	@Override
	public Integer saveEducationDetails(EducationInputs education) {
	    // Convert Binding object to Entity object
	    DcEducationEntity educationEntity = new DcEducationEntity();
	    BeanUtils.copyProperties(education, educationEntity);
	    
	    // Save the obj
	    educationRepo.save(educationEntity);
	    
	    // Return the caseNumber
	    return education.getCaseNo();
	}

	@Override
	public Integer saveChildrenDetails(List<ChildInputs> children) {
	    // Convert each Binding class obj to each Entity class obj
	    children.forEach(child -> {
	        DcChildrenEntity childEntity = new DcChildrenEntity();
	        BeanUtils.copyProperties(child, childEntity);
	        
	        // Save each entity obj
	        childrenRepo.save(childEntity);
	    });
	    
	    // Return caseNo
	    return children.get(0).getCaseNo();
	}

	@Override
	public DcSummaryReport showDcSummary(Integer caseNo) {

	    // Get multiple entity objs based on caseNo
	    DcIncomeEntity incomeEntity = incomeRepo.findByCaseNo(caseNo);
	    DcEducationEntity educationEntity = educationRepo.findByCaseNo(caseNo);
	    List<DcChildrenEntity> childsEntityList = childrenRepo.findByCaseNo(caseNo);
	    Optional<DcCaseEntity> optCaseEntity = caseRepo.findById(caseNo);

	    // Get Plan Name
	    String planName = null;
	    Integer applId = null;

	    if (optCaseEntity.isPresent()) {
	        DcCaseEntity caseEntity = optCaseEntity.get();
	        Integer planId = caseEntity.getPlanId();
	        applId = caseEntity.getAppId();
	        Optional<PlanEntity> optPlanEntity = planRepo.findById(planId);
	        if (optPlanEntity.isPresent()) {
	            planName = optPlanEntity.get().getPlanName();
	        }
	    }

	    Optional<CitizenAppRegistrationEntity> optCitizenEntity = citizenAppRepo.findById(applId);
	    CitizenAppRegistrationEntity citizenEntity = null;
	    if (optCitizenEntity.isPresent()) {
	        citizenEntity = optCitizenEntity.get();
	    }

	    // Convert Entity objs to Binding objs
	    IncomeInputs income = new IncomeInputs();
	    BeanUtils.copyProperties(incomeEntity, income);

	    EducationInputs education = new EducationInputs();
	    BeanUtils.copyProperties(educationEntity, education);

	    List<ChildInputs> listChilds = new ArrayList<>();
	    childsEntityList.forEach(childEntity -> {
	        ChildInputs child = new ChildInputs();
	        BeanUtils.copyProperties(childEntity, child);
	        listChilds.add(child);
	    });

	    CitizenAppRegistrationInputs citizen = new CitizenAppRegistrationInputs();
	    BeanUtils.copyProperties(citizenEntity, citizen);

	    // Prepare DcSummaryReport obj
	    DcSummaryReport report = new DcSummaryReport();
	    report.setPlanName(planName);
	    report.setIncomeDetails(income);
	    report.setEducationDetails(education);
	    report.setCitizenDetails(citizen);
	    report.setChildrenDetails(listChilds);

	    return report;
	}

}
