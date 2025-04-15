package in.lakshay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.DataCollectionReportEntity;

/**
 * Repository for data collection report operations
 */
public interface IDataCollectionReportRepository extends JpaRepository<DataCollectionReportEntity, Integer> {
    
    /**
     * Find reports by case number
     */
    List<DataCollectionReportEntity> findByCaseNo(Long caseNo);
    
    /**
     * Find reports by application ID
     */
    List<DataCollectionReportEntity> findByAppId(Integer appId);
    
    /**
     * Find reports by plan ID
     */
    List<DataCollectionReportEntity> findByPlanId(Integer planId);
    
    /**
     * Find reports by plan name
     */
    List<DataCollectionReportEntity> findByPlanName(String planName);
    
    /**
     * Find reports created between dates
     */
    List<DataCollectionReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
