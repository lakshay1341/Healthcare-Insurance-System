package in.lakshay.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.lakshay.entity.GovernmentReportEntity;

/**
 * Repository for government report operations
 */
@Repository
public interface GovernmentReportRepository extends JpaRepository<GovernmentReportEntity, Integer> {
    
    /**
     * Find reports by type
     */
    List<GovernmentReportEntity> findByReportType(String reportType);
    
    /**
     * Find reports by format
     */
    List<GovernmentReportEntity> findByReportFormat(String reportFormat);
    
    /**
     * Find reports by status
     */
    List<GovernmentReportEntity> findByReportStatus(String reportStatus);
    
    /**
     * Find reports by department name
     */
    List<GovernmentReportEntity> findByDepartmentName(String departmentName);
    
    /**
     * Find reports by generated for
     */
    List<GovernmentReportEntity> findByGeneratedFor(String generatedFor);
    
    /**
     * Find reports by period covered
     */
    List<GovernmentReportEntity> findByPeriodCovered(String periodCovered);
    
    /**
     * Find reports created between dates
     */
    List<GovernmentReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
