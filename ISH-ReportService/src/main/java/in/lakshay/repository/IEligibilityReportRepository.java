package in.lakshay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.EligibilityReportEntity;

/**
 * Repository for eligibility report operations
 */
public interface IEligibilityReportRepository extends JpaRepository<EligibilityReportEntity, Integer> {
    
    /**
     * Find reports by plan name
     */
    List<EligibilityReportEntity> findByPlanName(String planName);
    
    /**
     * Find reports by plan status
     */
    List<EligibilityReportEntity> findByPlanStatus(String planStatus);
    
    /**
     * Find reports by case number
     */
    List<EligibilityReportEntity> findByCaseNo(Long caseNo);
    
    /**
     * Find reports by application ID
     */
    List<EligibilityReportEntity> findByAppId(Integer appId);
    
    /**
     * Find reports created between dates
     */
    List<EligibilityReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
