package in.lakshay.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.lakshay.entity.ReportEntity;

/**
 * Repository for report operations
 */
public interface IReportRepository extends JpaRepository<ReportEntity, Integer> {
    
    /**
     * Find reports by type
     */
    List<ReportEntity> findByReportType(String reportType);
    
    /**
     * Find reports by status
     */
    List<ReportEntity> findByReportStatus(String reportStatus);
    
    /**
     * Find reports by case number
     */
    List<ReportEntity> findByCaseNumber(Integer caseNumber);
    
    /**
     * Find reports by application ID
     */
    List<ReportEntity> findByApplicationId(Integer applicationId);
    
    /**
     * Find reports by benefit type
     */
    List<ReportEntity> findByBenefitType(String benefitType);
    
    /**
     * Find reports created between dates
     */
    List<ReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get all unique report types
     */
    @Query("SELECT DISTINCT r.reportType FROM ReportEntity r")
    Set<String> findAllReportTypes();
    
    /**
     * Get all unique report formats
     */
    @Query("SELECT DISTINCT r.reportFormat FROM ReportEntity r")
    Set<String> findAllReportFormats();
    
    /**
     * Get all unique benefit types
     */
    @Query("SELECT DISTINCT r.benefitType FROM ReportEntity r")
    Set<String> findAllBenefitTypes();
    
    /**
     * Find reports by multiple criteria
     */
    @Query("SELECT r FROM ReportEntity r WHERE " +
           "(:reportType IS NULL OR r.reportType = :reportType) AND " +
           "(:reportFormat IS NULL OR r.reportFormat = :reportFormat) AND " +
           "(:reportStatus IS NULL OR r.reportStatus = :reportStatus) AND " +
           "(:benefitType IS NULL OR r.benefitType = :benefitType) AND " +
           "(:startDate IS NULL OR r.createdDate >= :startDate) AND " +
           "(:endDate IS NULL OR r.createdDate <= :endDate)")
    List<ReportEntity> findReportsByMultipleCriteria(
            @Param("reportType") String reportType,
            @Param("reportFormat") String reportFormat,
            @Param("reportStatus") String reportStatus,
            @Param("benefitType") String benefitType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
