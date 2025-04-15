package in.lakshay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.BenefitReportEntity;

/**
 * Repository for benefit report operations
 */
public interface IBenefitReportRepository extends JpaRepository<BenefitReportEntity, Integer> {
    
    /**
     * Find reports by plan name
     */
    List<BenefitReportEntity> findByPlanName(String planName);
    
    /**
     * Find reports by issuance status
     */
    List<BenefitReportEntity> findByIssuanceStatus(String issuanceStatus);
    
    /**
     * Find reports by case number
     */
    List<BenefitReportEntity> findByCaseNo(Long caseNo);
    
    /**
     * Find reports created between dates
     */
    List<BenefitReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
