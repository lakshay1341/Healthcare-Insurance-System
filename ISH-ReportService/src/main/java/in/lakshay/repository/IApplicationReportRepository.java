package in.lakshay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.ApplicationReportEntity;

/**
 * Repository for application report operations
 */
public interface IApplicationReportRepository extends JpaRepository<ApplicationReportEntity, Integer> {
    
    /**
     * Find reports by application ID
     */
    List<ApplicationReportEntity> findByAppId(Integer appId);
    
    /**
     * Find reports by state name
     */
    List<ApplicationReportEntity> findByStateName(String stateName);
    
    /**
     * Find reports by SSN
     */
    List<ApplicationReportEntity> findBySsn(Long ssn);
    
    /**
     * Find reports created between dates
     */
    List<ApplicationReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
