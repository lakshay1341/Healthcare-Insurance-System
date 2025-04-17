package in.lakshay.service;

import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;
import in.lakshay.model.GovernmentReportRequest;

/**
 * Service interface for Excel generation operations
 */
public interface ExcelGenerationService {
    
    /**
     * Generate eligibility report Excel
     */
    byte[] generateEligibilityReportExcel(GovernmentReportRequest request, EligibilityStatistics statistics);
    
    /**
     * Generate benefit report Excel
     */
    byte[] generateBenefitReportExcel(GovernmentReportRequest request, BenefitStatistics statistics);
    
    /**
     * Generate application report Excel
     */
    byte[] generateApplicationReportExcel(GovernmentReportRequest request, ApplicationStatistics statistics);
    
    /**
     * Generate comprehensive report Excel
     */
    byte[] generateComprehensiveReportExcel(GovernmentReportRequest request, 
                                           EligibilityStatistics eligibilityStats,
                                           BenefitStatistics benefitStats,
                                           ApplicationStatistics applicationStats);
}
