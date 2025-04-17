package in.lakshay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.lakshay.entity.EligibilityDetailsEntity;

/**
 * Repository for eligibility determination operations
 */
@Repository
public interface EligibilityDetailsRepository extends JpaRepository<EligibilityDetailsEntity, Integer> {
    
    /**
     * Find eligibility details by case number
     */
    List<EligibilityDetailsEntity> findByCaseNo(Long caseNo);
    
    /**
     * Find eligibility details by plan name
     */
    List<EligibilityDetailsEntity> findByPlanName(String planName);
    
    /**
     * Find eligibility details by plan status
     */
    List<EligibilityDetailsEntity> findByPlanStatus(String planStatus);
    
    /**
     * Find eligibility details by plan start date between
     */
    List<EligibilityDetailsEntity> findByPlanStartDateBetween(LocalDate startDate, LocalDate endDate);
    
    /**
     * Count eligibility details by plan status
     */
    Long countByPlanStatus(String planStatus);
    
    /**
     * Count eligibility details by plan name
     */
    Long countByPlanName(String planName);
    
    /**
     * Count eligibility details by denial reason
     */
    Long countByDenialReason(String denialReason);
    
    /**
     * Get eligibility count by plan
     */
    @Query("SELECT e.planName, COUNT(e) FROM EligibilityDetailsEntity e GROUP BY e.planName")
    List<Object[]> getEligibilityCountByPlan();
    
    /**
     * Get eligibility count by status
     */
    @Query("SELECT e.planStatus, COUNT(e) FROM EligibilityDetailsEntity e GROUP BY e.planStatus")
    List<Object[]> getEligibilityCountByStatus();
    
    /**
     * Get eligibility count by month
     */
    @Query("SELECT FUNCTION('MONTH', e.planStartDate) as month, COUNT(e) FROM EligibilityDetailsEntity e WHERE e.planStartDate BETWEEN :startDate AND :endDate GROUP BY FUNCTION('MONTH', e.planStartDate)")
    List<Object[]> getEligibilityCountByMonth(LocalDate startDate, LocalDate endDate);
    
    /**
     * Get average benefit amount by plan
     */
    @Query("SELECT e.planName, AVG(e.benefitAmt) FROM EligibilityDetailsEntity e WHERE e.benefitAmt IS NOT NULL GROUP BY e.planName")
    List<Object[]> getAverageBenefitAmountByPlan();
    
    /**
     * Get denial reasons count
     */
    @Query("SELECT e.denialReason, COUNT(e) FROM EligibilityDetailsEntity e WHERE e.denialReason IS NOT NULL GROUP BY e.denialReason")
    List<Object[]> getDenialReasonsCount();
}
