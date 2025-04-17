package in.lakshay.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import in.lakshay.entity.GovernmentReportEntity;
import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;
import in.lakshay.model.GovernmentReportRequest;
import in.lakshay.model.GovernmentReportResponse;
import in.lakshay.repository.GovernmentReportRepository;
import in.lakshay.service.ExcelGenerationService;
import in.lakshay.service.GovernmentReportService;
import in.lakshay.service.PdfGenerationService;
import in.lakshay.service.StatisticsService;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implementation of the government report service
 */
@Service
public class GovernmentReportServiceImpl implements GovernmentReportService {
    
    private static final Logger logger = Logger.getLogger(GovernmentReportServiceImpl.class.getName());

    @Autowired
    private GovernmentReportRepository reportRepository;
    
    @Autowired
    private StatisticsService statisticsService;
    
    @Autowired
    private PdfGenerationService pdfGenerationService;
    
    @Autowired
    private ExcelGenerationService excelGenerationService;
    
    @Value("${gov-report.output-directory:reports/government}")
    private String outputDirectory;
    
    @Override
    public GovernmentReportResponse generateReport(GovernmentReportRequest request) {
        logger.info("Generating government report: " + request);
        
        try {
            // Create output directory if it doesn't exist
            Path dirPath = Paths.get(outputDirectory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            
            // Generate report name
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportName = request.getReportType() + "_" + request.getPeriodCovered() + "_" + timestamp;
            String fileName = reportName + "." + request.getReportFormat().toLowerCase();
            String filePath = outputDirectory + File.separator + fileName;
            
            // Get statistics based on report type
            byte[] reportContent;
            
            if ("comprehensive".equalsIgnoreCase(request.getReportType())) {
                EligibilityStatistics eligibilityStats = statisticsService.getEligibilityStatistics(request.getPeriodCovered());
                BenefitStatistics benefitStats = statisticsService.getBenefitStatistics(request.getPeriodCovered());
                ApplicationStatistics applicationStats = statisticsService.getApplicationStatistics(request.getPeriodCovered());
                
                if ("pdf".equalsIgnoreCase(request.getReportFormat())) {
                    reportContent = pdfGenerationService.generateComprehensiveReportPdf(
                            request, eligibilityStats, benefitStats, applicationStats);
                } else {
                    reportContent = excelGenerationService.generateComprehensiveReportExcel(
                            request, eligibilityStats, benefitStats, applicationStats);
                }
            } else if ("eligibility".equalsIgnoreCase(request.getReportType())) {
                EligibilityStatistics statistics = statisticsService.getEligibilityStatistics(request.getPeriodCovered());
                
                if ("pdf".equalsIgnoreCase(request.getReportFormat())) {
                    reportContent = pdfGenerationService.generateEligibilityReportPdf(request, statistics);
                } else {
                    reportContent = excelGenerationService.generateEligibilityReportExcel(request, statistics);
                }
            } else if ("benefit".equalsIgnoreCase(request.getReportType())) {
                BenefitStatistics statistics = statisticsService.getBenefitStatistics(request.getPeriodCovered());
                
                if ("pdf".equalsIgnoreCase(request.getReportFormat())) {
                    reportContent = pdfGenerationService.generateBenefitReportPdf(request, statistics);
                } else {
                    reportContent = excelGenerationService.generateBenefitReportExcel(request, statistics);
                }
            } else if ("application".equalsIgnoreCase(request.getReportType())) {
                ApplicationStatistics statistics = statisticsService.getApplicationStatistics(request.getPeriodCovered());
                
                if ("pdf".equalsIgnoreCase(request.getReportFormat())) {
                    reportContent = pdfGenerationService.generateApplicationReportPdf(request, statistics);
                } else {
                    reportContent = excelGenerationService.generateApplicationReportExcel(request, statistics);
                }
            } else {
                throw new IllegalArgumentException("Unsupported report type: " + request.getReportType());
            }
            
            // Save report content to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(reportContent);
            }
            
            // Save report metadata to database
            GovernmentReportEntity reportEntity = new GovernmentReportEntity();
            reportEntity.setReportName(reportName);
            reportEntity.setReportType(request.getReportType());
            reportEntity.setReportFormat(request.getReportFormat());
            reportEntity.setReportStatus("Generated");
            reportEntity.setReportDescription("Government report for " + request.getPeriodCovered());
            reportEntity.setReportFilePath(filePath);
            reportEntity.setGeneratedFor(request.getGeneratedFor());
            reportEntity.setDepartmentName(request.getDepartmentName());
            reportEntity.setPeriodCovered(request.getPeriodCovered());
            reportEntity.setReportContent(reportContent);
            reportEntity.setCreatedBy("system");
            reportEntity.setUpdatedBy("system");
            
            reportEntity = reportRepository.save(reportEntity);
            
            // Create response
            GovernmentReportResponse response = new GovernmentReportResponse();
            response.setReportId(reportEntity.getReportId());
            response.setReportName(reportEntity.getReportName());
            response.setReportType(reportEntity.getReportType());
            response.setReportFormat(reportEntity.getReportFormat());
            response.setReportStatus(reportEntity.getReportStatus());
            response.setReportDescription(reportEntity.getReportDescription());
            response.setReportFilePath(reportEntity.getReportFilePath());
            response.setGeneratedFor(reportEntity.getGeneratedFor());
            response.setDepartmentName(reportEntity.getDepartmentName());
            response.setPeriodCovered(reportEntity.getPeriodCovered());
            response.setCreatedDate(reportEntity.getCreatedDate());
            response.setCreatedBy(reportEntity.getCreatedBy());
            response.setDownloadUrl("/report-api/government/download/" + reportEntity.getReportId());
            
            return response;
        } catch (Exception e) {
            logger.severe("Error generating government report: " + e.getMessage());
            throw new RuntimeException("Failed to generate government report", e);
        }
    }

    @Override
    public List<GovernmentReportResponse> getAllReports() {
        logger.info("Getting all government reports");
        
        List<GovernmentReportEntity> reportEntities = reportRepository.findAll();
        return reportEntities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GovernmentReportResponse getReportById(Integer reportId) {
        logger.info("Getting government report by ID: " + reportId);
        
        Optional<GovernmentReportEntity> reportEntityOpt = reportRepository.findById(reportId);
        if (reportEntityOpt.isPresent()) {
            return convertToResponse(reportEntityOpt.get());
        } else {
            throw new IllegalArgumentException("Report not found with ID: " + reportId);
        }
    }

    @Override
    public List<GovernmentReportResponse> getReportsByType(String reportType) {
        logger.info("Getting government reports by type: " + reportType);
        
        List<GovernmentReportEntity> reportEntities = reportRepository.findByReportType(reportType);
        return reportEntities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GovernmentReportResponse> getReportsByDepartment(String departmentName) {
        logger.info("Getting government reports by department: " + departmentName);
        
        List<GovernmentReportEntity> reportEntities = reportRepository.findByDepartmentName(departmentName);
        return reportEntities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<GovernmentReportResponse> getReportsByPeriod(String periodCovered) {
        logger.info("Getting government reports by period: " + periodCovered);
        
        List<GovernmentReportEntity> reportEntities = reportRepository.findByPeriodCovered(periodCovered);
        return reportEntities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void downloadReport(Integer reportId, HttpServletResponse response) {
        logger.info("Downloading government report with ID: " + reportId);
        
        try {
            Optional<GovernmentReportEntity> reportEntityOpt = reportRepository.findById(reportId);
            if (reportEntityOpt.isPresent()) {
                GovernmentReportEntity reportEntity = reportEntityOpt.get();
                
                // Set response headers
                response.setContentType(getContentType(reportEntity.getReportFormat()));
                response.setHeader("Content-Disposition", "attachment; filename=\"" + reportEntity.getReportName() + 
                        "." + reportEntity.getReportFormat().toLowerCase() + "\"");
                
                // Write report content to response
                FileCopyUtils.copy(reportEntity.getReportContent(), response.getOutputStream());
            } else {
                throw new IllegalArgumentException("Report not found with ID: " + reportId);
            }
        } catch (Exception e) {
            logger.severe("Error downloading government report: " + e.getMessage());
            throw new RuntimeException("Failed to download government report", e);
        }
    }

    @Override
    public void deleteReport(Integer reportId) {
        logger.info("Deleting government report with ID: " + reportId);
        
        Optional<GovernmentReportEntity> reportEntityOpt = reportRepository.findById(reportId);
        if (reportEntityOpt.isPresent()) {
            GovernmentReportEntity reportEntity = reportEntityOpt.get();
            
            // Delete file if it exists
            try {
                Path filePath = Paths.get(reportEntity.getReportFilePath());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (Exception e) {
                logger.warning("Error deleting report file: " + e.getMessage());
            }
            
            // Delete from database
            reportRepository.deleteById(reportId);
        } else {
            throw new IllegalArgumentException("Report not found with ID: " + reportId);
        }
    }
    
    private GovernmentReportResponse convertToResponse(GovernmentReportEntity entity) {
        GovernmentReportResponse response = new GovernmentReportResponse();
        response.setReportId(entity.getReportId());
        response.setReportName(entity.getReportName());
        response.setReportType(entity.getReportType());
        response.setReportFormat(entity.getReportFormat());
        response.setReportStatus(entity.getReportStatus());
        response.setReportDescription(entity.getReportDescription());
        response.setReportFilePath(entity.getReportFilePath());
        response.setGeneratedFor(entity.getGeneratedFor());
        response.setDepartmentName(entity.getDepartmentName());
        response.setPeriodCovered(entity.getPeriodCovered());
        response.setCreatedDate(entity.getCreatedDate());
        response.setCreatedBy(entity.getCreatedBy());
        response.setDownloadUrl("/report-api/government/download/" + entity.getReportId());
        
        return response;
    }
    
    private String getContentType(String reportFormat) {
        if ("pdf".equalsIgnoreCase(reportFormat)) {
            return "application/pdf";
        } else if ("excel".equalsIgnoreCase(reportFormat)) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else {
            return "application/octet-stream";
        }
    }
}
