package in.lakshay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.lakshay.entity.DcCaseEntity;

/**
 * Repository for data collection case operations
 */
@Repository
public interface DcCaseRepository extends JpaRepository<DcCaseEntity, Long> {
    
    /**
     * Find cases by application ID
     */
    List<DcCaseEntity> findByAppId(Integer appId);
    
    /**
     * Find cases by plan ID
     */
    List<DcCaseEntity> findByPlanId(Integer planId);
    
    /**
     * Count cases by plan ID
     */
    Long countByPlanId(Integer planId);
    
    /**
     * Get case count by plan
     */
    @Query("SELECT dc.planId, COUNT(dc) FROM DcCaseEntity dc GROUP BY dc.planId")
    List<Object[]> getCaseCountByPlan();
    
    /**
     * Get case count by application ID
     */
    @Query("SELECT dc.appId, COUNT(dc) FROM DcCaseEntity dc GROUP BY dc.appId")
    List<Object[]> getCaseCountByApplication();
}
