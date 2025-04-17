package in.lakshay.service;

import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;
import in.lakshay.model.GovernmentReportRequest;

/**
 * Service interface for PDF generation operations
 */
public interface PdfGenerationService {
    
    /**
     * Generate eligibility report PDF
     */
    byte[] generateEligibilityReportPdf(GovernmentReportRequest request, EligibilityStatistics statistics);
    
    /**
     * Generate benefit report PDF
     */
    byte[] generateBenefitReportPdf(GovernmentReportRequest request, BenefitStatistics statistics);
    
    /**
     * Generate application report PDF
     */
    byte[] generateApplicationReportPdf(GovernmentReportRequest request, ApplicationStatistics statistics);
    
    /**
     * Generate comprehensive report PDF
     */
    byte[] generateComprehensiveReportPdf(GovernmentReportRequest request, 
                                         EligibilityStatistics eligibilityStats,
                                         BenefitStatistics benefitStats,
                                         ApplicationStatistics applicationStats);
}
