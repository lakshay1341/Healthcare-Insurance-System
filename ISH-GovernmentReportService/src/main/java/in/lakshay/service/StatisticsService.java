package in.lakshay.service;

import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;

/**
 * Service interface for statistics operations
 */
public interface StatisticsService {
    
    /**
     * Get eligibility statistics
     */
    EligibilityStatistics getEligibilityStatistics(String periodCovered);
    
    /**
     * Get benefit statistics
     */
    BenefitStatistics getBenefitStatistics(String periodCovered);
    
    /**
     * Get application statistics
     */
    ApplicationStatistics getApplicationStatistics(String periodCovered);
}
