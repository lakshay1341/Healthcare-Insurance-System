package in.lakshay.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;
import in.lakshay.repository.CitizenAppRegistrationRepository;
import in.lakshay.repository.DcCaseRepository;
import in.lakshay.repository.DcIncomeRepository;
import in.lakshay.repository.EligibilityDetailsRepository;
import in.lakshay.service.StatisticsService;

/**
 * Implementation of the statistics service that connects to the real database
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    
    private static final Logger logger = Logger.getLogger(StatisticsServiceImpl.class.getName());

    @Autowired
    private CitizenAppRegistrationRepository citizenRepo;
    
    @Autowired
    private EligibilityDetailsRepository eligibilityRepo;
    
    @Autowired
    private DcCaseRepository caseRepo;
    
    @Autowired
    private DcIncomeRepository incomeRepo;
    
    @Override
    public EligibilityStatistics getEligibilityStatistics(String periodCovered) {
        logger.info("Fetching eligibility statistics for period: " + periodCovered);
        
        // Parse period to get date range
        LocalDate[] dateRange = parsePeriod(periodCovered);
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];
        
        EligibilityStatistics stats = new EligibilityStatistics();
        
        // Get total applications
        Long totalApplications = citizenRepo.countByCreationDateBetween(startDate, endDate);
        stats.setTotalApplications(totalApplications.intValue());
        
        // Get approved applications
        Long approvedApplications = eligibilityRepo.countByPlanStatus("Approved");
        stats.setApprovedApplications(approvedApplications.intValue());
        
        // Get denied applications
        Long deniedApplications = eligibilityRepo.countByPlanStatus("Denied");
        stats.setDeniedApplications(deniedApplications.intValue());
        
        // Get pending applications
        Long pendingApplications = eligibilityRepo.countByPlanStatus("Pending");
        stats.setPendingApplications(pendingApplications.intValue());
        
        // Calculate approval and denial rates
        if (totalApplications > 0) {
            double approvalRate = (approvedApplications.doubleValue() / totalApplications.doubleValue()) * 100;
            double denialRate = (deniedApplications.doubleValue() / totalApplications.doubleValue()) * 100;
            stats.setApprovalRate(Math.round(approvalRate * 10) / 10.0);
            stats.setDenialRate(Math.round(denialRate * 10) / 10.0);
        } else {
            stats.setApprovalRate(0.0);
            stats.setDenialRate(0.0);
        }
        
        // Set average processing time (this would require additional data not available in the current model)
        stats.setAverageProcessingTimeInDays(14.5); // Default value
        
        // Get state breakdown
        Map<String, Integer> stateBreakdown = new HashMap<>();
        List<Object[]> stateData = citizenRepo.getApplicationCountByState();
        for (Object[] data : stateData) {
            String state = (String) data[0];
            Long count = (Long) data[1];
            stateBreakdown.put(state, count.intValue());
        }
        stats.setBreakdownByState(stateBreakdown);
        
        // Get plan breakdown
        Map<String, Integer> planBreakdown = new HashMap<>();
        List<Object[]> planData = eligibilityRepo.getEligibilityCountByPlan();
        for (Object[] data : planData) {
            String plan = (String) data[0];
            Long count = (Long) data[1];
            planBreakdown.put(plan, count.intValue());
        }
        stats.setBreakdownByPlan(planBreakdown);
        
        // Get age group breakdown
        Map<String, Integer> ageBreakdown = new HashMap<>();
        List<Object[]> ageData = citizenRepo.getApplicationCountByAgeGroup();
        for (Object[] data : ageData) {
            String ageGroup = (String) data[0];
            Long count = (Long) data[1];
            ageBreakdown.put(ageGroup, count.intValue());
        }
        stats.setBreakdownByAgeGroup(ageBreakdown);
        
        // Get income level breakdown
        Map<String, Integer> incomeBreakdown = new HashMap<>();
        List<Object[]> incomeData = incomeRepo.getIncomeDistributionByRange();
        for (Object[] data : incomeData) {
            String incomeRange = (String) data[0];
            Long count = (Long) data[1];
            incomeBreakdown.put(incomeRange, count.intValue());
        }
        stats.setBreakdownByIncomeLevel(incomeBreakdown);
        
        // Get denial reasons
        Map<String, Integer> denialReasons = new HashMap<>();
        List<Object[]> denialData = eligibilityRepo.getDenialReasonsCount();
        for (Object[] data : denialData) {
            String reason = (String) data[0];
            Long count = (Long) data[1];
            denialReasons.put(reason, count.intValue());
        }
        stats.setDenialReasons(denialReasons);
        
        return stats;
    }

    @Override
    public BenefitStatistics getBenefitStatistics(String periodCovered) {
        logger.info("Fetching benefit statistics for period: " + periodCovered);
        
        // Parse period to get date range
        LocalDate[] dateRange = parsePeriod(periodCovered);
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];
        
        BenefitStatistics stats = new BenefitStatistics();
        
        // Get total benefits issued (approved applications with benefit amount)
        Long approvedApplications = eligibilityRepo.countByPlanStatus("Approved");
        stats.setTotalBenefitsIssued(approvedApplications.intValue());
        
        // Get total amount issued
        Double totalAmount = 0.0;
        List<Object[]> benefitData = eligibilityRepo.getAverageBenefitAmountByPlan();
        for (Object[] data : benefitData) {
            String plan = (String) data[0];
            Double avgAmount = (Double) data[1];
            Long planCount = eligibilityRepo.countByPlanName(plan);
            totalAmount += avgAmount * planCount;
        }
        stats.setTotalAmountIssued(totalAmount);
        
        // Calculate average benefit amount
        if (approvedApplications > 0) {
            stats.setAverageBenefitAmount(totalAmount / approvedApplications);
        } else {
            stats.setAverageBenefitAmount(0.0);
        }
        
        // Set active recipients (same as approved applications for now)
        stats.setActiveRecipients(approvedApplications.intValue());
        
        // Get state breakdown
        Map<String, Double> stateBreakdown = new HashMap<>();
        List<Object[]> stateData = citizenRepo.getApplicationCountByState();
        for (Object[] data : stateData) {
            String state = (String) data[0];
            Long count = (Long) data[1];
            // Estimate amount by state based on proportion of applications
            double stateAmount = totalAmount * (count.doubleValue() / approvedApplications.doubleValue());
            stateBreakdown.put(state, stateAmount);
        }
        stats.setAmountByState(stateBreakdown);
        
        // Get plan breakdown
        Map<String, Double> planBreakdown = new HashMap<>();
        for (Object[] data : benefitData) {
            String plan = (String) data[0];
            Double avgAmount = (Double) data[1];
            Long planCount = eligibilityRepo.countByPlanName(plan);
            planBreakdown.put(plan, avgAmount * planCount);
        }
        stats.setAmountByPlan(planBreakdown);
        
        // Get age group breakdown (estimated based on application distribution)
        Map<String, Double> ageBreakdown = new HashMap<>();
        List<Object[]> ageData = citizenRepo.getApplicationCountByAgeGroup();
        for (Object[] data : ageData) {
            String ageGroup = (String) data[0];
            Long count = (Long) data[1];
            // Estimate amount by age group based on proportion of applications
            double ageAmount = totalAmount * (count.doubleValue() / approvedApplications.doubleValue());
            ageBreakdown.put(ageGroup, ageAmount);
        }
        stats.setAmountByAgeGroup(ageBreakdown);
        
        // Get income level breakdown
        Map<String, Double> incomeBreakdown = new HashMap<>();
        List<Object[]> incomeData = incomeRepo.getIncomeDistributionByRange();
        for (Object[] data : incomeData) {
            String incomeRange = (String) data[0];
            Long count = (Long) data[1];
            // Estimate amount by income level based on proportion of applications
            double incomeAmount = totalAmount * (count.doubleValue() / approvedApplications.doubleValue());
            incomeBreakdown.put(incomeRange, incomeAmount);
        }
        stats.setAmountByIncomeLevel(incomeBreakdown);
        
        // Get monthly trends
        Map<String, Double> monthlyTrends = new HashMap<>();
        List<Object[]> monthlyData = eligibilityRepo.getEligibilityCountByMonth(startDate, endDate);
        for (Object[] data : monthlyData) {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            String monthName = getMonthName(month);
            // Estimate monthly amount based on proportion of approvals
            double monthlyAmount = totalAmount * (count.doubleValue() / approvedApplications.doubleValue());
            monthlyTrends.put(monthName, monthlyAmount);
        }
        stats.setMonthlyIssuanceTrends(monthlyTrends);
        
        return stats;
    }

    @Override
    public ApplicationStatistics getApplicationStatistics(String periodCovered) {
        logger.info("Fetching application statistics for period: " + periodCovered);
        
        // Parse period to get date range
        LocalDate[] dateRange = parsePeriod(periodCovered);
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];
        
        ApplicationStatistics stats = new ApplicationStatistics();
        
        // Get total applications
        Long totalApplications = citizenRepo.countByCreationDateBetween(startDate, endDate);
        stats.setTotalApplications(totalApplications.intValue());
        
        // Get new applications (last 30 days)
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        Long newApplications = citizenRepo.countByCreationDateBetween(thirtyDaysAgo, LocalDate.now());
        stats.setNewApplications(newApplications.intValue());
        
        // Get in progress applications (applications without eligibility determination)
        Long inProgressApplications = totalApplications - eligibilityRepo.count();
        if (inProgressApplications < 0) inProgressApplications = 0L;
        stats.setInProgressApplications(inProgressApplications.intValue());
        
        // Get completed applications (applications with eligibility determination)
        Long completedApplications = eligibilityRepo.count();
        stats.setCompletedApplications(completedApplications.intValue());
        
        // Set average completion time (this would require additional data not available in the current model)
        stats.setAverageCompletionTimeInDays(21.5); // Default value
        
        // Get state breakdown
        Map<String, Integer> stateBreakdown = new HashMap<>();
        List<Object[]> stateData = citizenRepo.getApplicationCountByState();
        for (Object[] data : stateData) {
            String state = (String) data[0];
            Long count = (Long) data[1];
            stateBreakdown.put(state, count.intValue());
        }
        stats.setApplicationsByState(stateBreakdown);
        
        // Get plan breakdown
        Map<String, Integer> planBreakdown = new HashMap<>();
        List<Object[]> planData = eligibilityRepo.getEligibilityCountByPlan();
        for (Object[] data : planData) {
            String plan = (String) data[0];
            Long count = (Long) data[1];
            planBreakdown.put(plan, count.intValue());
        }
        stats.setApplicationsByPlan(planBreakdown);
        
        // Get age group breakdown
        Map<String, Integer> ageBreakdown = new HashMap<>();
        List<Object[]> ageData = citizenRepo.getApplicationCountByAgeGroup();
        for (Object[] data : ageData) {
            String ageGroup = (String) data[0];
            Long count = (Long) data[1];
            ageBreakdown.put(ageGroup, count.intValue());
        }
        stats.setApplicationsByAgeGroup(ageBreakdown);
        
        // Get income level breakdown
        Map<String, Integer> incomeBreakdown = new HashMap<>();
        List<Object[]> incomeData = incomeRepo.getIncomeDistributionByRange();
        for (Object[] data : incomeData) {
            String incomeRange = (String) data[0];
            Long count = (Long) data[1];
            incomeBreakdown.put(incomeRange, count.intValue());
        }
        stats.setApplicationsByIncomeLevel(incomeBreakdown);
        
        // Get monthly trends
        Map<String, Integer> monthlyTrends = new HashMap<>();
        List<Object[]> monthlyData = citizenRepo.getApplicationCountByMonth(startDate, endDate);
        for (Object[] data : monthlyData) {
            Integer month = (Integer) data[0];
            Long count = (Long) data[1];
            String monthName = getMonthName(month);
            monthlyTrends.put(monthName, count.intValue());
        }
        stats.setMonthlyApplicationTrends(monthlyTrends);
        
        // Set application methods (this data is not available in the current model)
        Map<String, Integer> applicationMethods = new HashMap<>();
        applicationMethods.put("Online", (int)(totalApplications * 0.6)); // Estimate 60% online
        applicationMethods.put("Paper", (int)(totalApplications * 0.2)); // Estimate 20% paper
        applicationMethods.put("Phone", (int)(totalApplications * 0.15)); // Estimate 15% phone
        applicationMethods.put("In-Person", (int)(totalApplications * 0.05)); // Estimate 5% in-person
        stats.setApplicationMethods(applicationMethods);
        
        return stats;
    }
    
    /**
     * Parse period string to get date range
     */
    private LocalDate[] parsePeriod(String periodCovered) {
        LocalDate startDate;
        LocalDate endDate;
        
        if (periodCovered.contains("Q")) {
            // Quarter format: 2023-Q1, 2023-Q2, etc.
            String[] parts = periodCovered.split("-Q");
            int year = Integer.parseInt(parts[0]);
            int quarter = Integer.parseInt(parts[1]);
            
            switch (quarter) {
                case 1:
                    startDate = LocalDate.of(year, 1, 1);
                    endDate = LocalDate.of(year, 3, 31);
                    break;
                case 2:
                    startDate = LocalDate.of(year, 4, 1);
                    endDate = LocalDate.of(year, 6, 30);
                    break;
                case 3:
                    startDate = LocalDate.of(year, 7, 1);
                    endDate = LocalDate.of(year, 9, 30);
                    break;
                case 4:
                    startDate = LocalDate.of(year, 10, 1);
                    endDate = LocalDate.of(year, 12, 31);
                    break;
                default:
                    startDate = LocalDate.of(year, 1, 1);
                    endDate = LocalDate.of(year, 12, 31);
            }
        } else if (periodCovered.length() == 4) {
            // Year format: 2023
            int year = Integer.parseInt(periodCovered);
            startDate = LocalDate.of(year, 1, 1);
            endDate = LocalDate.of(year, 12, 31);
        } else if (periodCovered.length() == 7) {
            // Month format: 2023-01
            startDate = LocalDate.parse(periodCovered + "-01");
            endDate = startDate.plusMonths(1).minusDays(1);
        } else {
            // Default to current year
            int currentYear = LocalDate.now().getYear();
            startDate = LocalDate.of(currentYear, 1, 1);
            endDate = LocalDate.of(currentYear, 12, 31);
        }
        
        return new LocalDate[] { startDate, endDate };
    }
    
    /**
     * Get month name from month number
     */
    private String getMonthName(int month) {
        return DateTimeFormatter.ofPattern("MMMM").format(LocalDate.of(2000, month, 1));
    }
}
