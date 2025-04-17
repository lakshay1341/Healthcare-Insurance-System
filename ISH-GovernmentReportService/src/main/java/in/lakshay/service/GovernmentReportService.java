package in.lakshay.service;

import java.util.List;

import in.lakshay.model.GovernmentReportRequest;
import in.lakshay.model.GovernmentReportResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Service interface for government report operations
 */
public interface GovernmentReportService {
    
    /**
     * Generate a government report
     */
    GovernmentReportResponse generateReport(GovernmentReportRequest request);
    
    /**
     * Get all reports
     */
    List<GovernmentReportResponse> getAllReports();
    
    /**
     * Get report by ID
     */
    GovernmentReportResponse getReportById(Integer reportId);
    
    /**
     * Get reports by type
     */
    List<GovernmentReportResponse> getReportsByType(String reportType);
    
    /**
     * Get reports by department
     */
    List<GovernmentReportResponse> getReportsByDepartment(String departmentName);
    
    /**
     * Get reports by period
     */
    List<GovernmentReportResponse> getReportsByPeriod(String periodCovered);
    
    /**
     * Download report
     */
    void downloadReport(Integer reportId, HttpServletResponse response);
    
    /**
     * Delete report
     */
    void deleteReport(Integer reportId);
}
