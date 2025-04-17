package in.lakshay.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.model.GovernmentReportRequest;
import in.lakshay.model.GovernmentReportResponse;
import in.lakshay.service.GovernmentReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller for government report operations
 */
@RestController
@RequestMapping("/report-api/government")
@Tag(name = "Government Report Controller", description = "API for government report operations")
public class GovernmentReportController {
    
    private static final Logger logger = Logger.getLogger(GovernmentReportController.class.getName());

    @Autowired
    private GovernmentReportService reportService;
    
    /**
     * Generate a government report
     */
    @PostMapping("/generate")
    @Operation(summary = "Generate a government report", description = "Generates a new government report based on the provided request")
    public ResponseEntity<GovernmentReportResponse> generateReport(@RequestBody GovernmentReportRequest request) {
        logger.info("Received request to generate government report: " + request);
        GovernmentReportResponse response = reportService.generateReport(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Get all reports
     */
    @GetMapping
    @Operation(summary = "Get all reports", description = "Returns a list of all government reports")
    public ResponseEntity<List<GovernmentReportResponse>> getAllReports() {
        logger.info("Received request to get all government reports");
        List<GovernmentReportResponse> reports = reportService.getAllReports();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    
    /**
     * Get report by ID
     */
    @GetMapping("/{reportId}")
    @Operation(summary = "Get report by ID", description = "Returns a government report by its ID")
    public ResponseEntity<GovernmentReportResponse> getReportById(@PathVariable Integer reportId) {
        logger.info("Received request to get government report by ID: " + reportId);
        GovernmentReportResponse report = reportService.getReportById(reportId);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    
    /**
     * Get reports by type
     */
    @GetMapping("/type/{reportType}")
    @Operation(summary = "Get reports by type", description = "Returns a list of government reports by type")
    public ResponseEntity<List<GovernmentReportResponse>> getReportsByType(@PathVariable String reportType) {
        logger.info("Received request to get government reports by type: " + reportType);
        List<GovernmentReportResponse> reports = reportService.getReportsByType(reportType);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    
    /**
     * Get reports by department
     */
    @GetMapping("/department")
    @Operation(summary = "Get reports by department", description = "Returns a list of government reports by department")
    public ResponseEntity<List<GovernmentReportResponse>> getReportsByDepartment(@RequestParam String departmentName) {
        logger.info("Received request to get government reports by department: " + departmentName);
        List<GovernmentReportResponse> reports = reportService.getReportsByDepartment(departmentName);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    
    /**
     * Get reports by period
     */
    @GetMapping("/period")
    @Operation(summary = "Get reports by period", description = "Returns a list of government reports by period")
    public ResponseEntity<List<GovernmentReportResponse>> getReportsByPeriod(@RequestParam String periodCovered) {
        logger.info("Received request to get government reports by period: " + periodCovered);
        List<GovernmentReportResponse> reports = reportService.getReportsByPeriod(periodCovered);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    
    /**
     * Download report
     */
    @GetMapping("/download/{reportId}")
    @Operation(summary = "Download report", description = "Downloads a government report by its ID")
    public void downloadReport(@PathVariable Integer reportId, HttpServletResponse response) {
        logger.info("Received request to download government report with ID: " + reportId);
        reportService.downloadReport(reportId, response);
    }
    
    /**
     * Delete report
     */
    @DeleteMapping("/{reportId}")
    @Operation(summary = "Delete report", description = "Deletes a government report by its ID")
    public ResponseEntity<Void> deleteReport(@PathVariable Integer reportId) {
        logger.info("Received request to delete government report with ID: " + reportId);
        reportService.deleteReport(reportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
