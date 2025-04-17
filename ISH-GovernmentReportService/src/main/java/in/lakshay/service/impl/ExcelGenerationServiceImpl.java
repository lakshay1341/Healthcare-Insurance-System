package in.lakshay.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;
import in.lakshay.model.GovernmentReportRequest;
import in.lakshay.service.ExcelGenerationService;

/**
 * Implementation of the Excel generation service
 */
@Service
public class ExcelGenerationServiceImpl implements ExcelGenerationService {
    
    private static final Logger logger = Logger.getLogger(ExcelGenerationServiceImpl.class.getName());

    @Override
    public byte[] generateEligibilityReportExcel(GovernmentReportRequest request, EligibilityStatistics statistics) {
        logger.info("Generating eligibility report Excel for period: " + request.getPeriodCovered());
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            
            // Create summary sheet
            Sheet summarySheet = workbook.createSheet("Eligibility Summary");
            
            // Add title
            addTitle(summarySheet, "Eligibility Report - " + request.getPeriodCovered(), titleStyle);
            
            // Add summary data
            int rowNum = 3;
            addSummaryRow(summarySheet, rowNum++, "Total Applications", statistics.getTotalApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Approved Applications", statistics.getApprovedApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Denied Applications", statistics.getDeniedApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Pending Applications", statistics.getPendingApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Approval Rate", statistics.getApprovalRate() + "%", dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Denial Rate", statistics.getDenialRate() + "%", dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Average Processing Time", statistics.getAverageProcessingTimeInDays() + " days", dataStyle);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                Sheet stateSheet = workbook.createSheet("State Breakdown");
                addBreakdownSheet(stateSheet, "Breakdown by State", statistics.getBreakdownByState(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                Sheet planSheet = workbook.createSheet("Plan Breakdown");
                addBreakdownSheet(planSheet, "Breakdown by Plan", statistics.getBreakdownByPlan(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByAge()) {
                Sheet ageSheet = workbook.createSheet("Age Breakdown");
                addBreakdownSheet(ageSheet, "Breakdown by Age Group", statistics.getBreakdownByAgeGroup(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByIncome()) {
                Sheet incomeSheet = workbook.createSheet("Income Breakdown");
                addBreakdownSheet(incomeSheet, "Breakdown by Income Level", statistics.getBreakdownByIncomeLevel(), headerStyle, dataStyle);
            }
            
            // Add denial reasons
            Sheet denialSheet = workbook.createSheet("Denial Reasons");
            addBreakdownSheet(denialSheet, "Denial Reasons", statistics.getDenialReasons(), headerStyle, dataStyle);
            
            // Auto-size columns for all sheets
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (int j = 0; j < 2; j++) {
                    sheet.autoSizeColumn(j);
                }
            }
            
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating eligibility report Excel: " + e.getMessage());
            throw new RuntimeException("Failed to generate eligibility report Excel", e);
        }
    }

    @Override
    public byte[] generateBenefitReportExcel(GovernmentReportRequest request, BenefitStatistics statistics) {
        logger.info("Generating benefit report Excel for period: " + request.getPeriodCovered());
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            
            // Create summary sheet
            Sheet summarySheet = workbook.createSheet("Benefit Summary");
            
            // Add title
            addTitle(summarySheet, "Benefit Issuance Report - " + request.getPeriodCovered(), titleStyle);
            
            // Add summary data
            int rowNum = 3;
            addSummaryRow(summarySheet, rowNum++, "Total Benefits Issued", statistics.getTotalBenefitsIssued().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Total Amount Issued", "$" + String.format("%,.2f", statistics.getTotalAmountIssued()), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Average Benefit Amount", "$" + String.format("%,.2f", statistics.getAverageBenefitAmount()), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Active Recipients", statistics.getActiveRecipients().toString(), dataStyle);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                Sheet stateSheet = workbook.createSheet("State Breakdown");
                addBreakdownSheet(stateSheet, "Amount by State", statistics.getAmountByState(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                Sheet planSheet = workbook.createSheet("Plan Breakdown");
                addBreakdownSheet(planSheet, "Amount by Plan", statistics.getAmountByPlan(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByAge()) {
                Sheet ageSheet = workbook.createSheet("Age Breakdown");
                addBreakdownSheet(ageSheet, "Amount by Age Group", statistics.getAmountByAgeGroup(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByIncome()) {
                Sheet incomeSheet = workbook.createSheet("Income Breakdown");
                addBreakdownSheet(incomeSheet, "Amount by Income Level", statistics.getAmountByIncomeLevel(), headerStyle, dataStyle);
            }
            
            // Add monthly trends
            Sheet trendsSheet = workbook.createSheet("Monthly Trends");
            addBreakdownSheet(trendsSheet, "Monthly Issuance Trends", statistics.getMonthlyIssuanceTrends(), headerStyle, dataStyle);
            
            // Auto-size columns for all sheets
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (int j = 0; j < 2; j++) {
                    sheet.autoSizeColumn(j);
                }
            }
            
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating benefit report Excel: " + e.getMessage());
            throw new RuntimeException("Failed to generate benefit report Excel", e);
        }
    }

    @Override
    public byte[] generateApplicationReportExcel(GovernmentReportRequest request, ApplicationStatistics statistics) {
        logger.info("Generating application report Excel for period: " + request.getPeriodCovered());
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            
            // Create summary sheet
            Sheet summarySheet = workbook.createSheet("Application Summary");
            
            // Add title
            addTitle(summarySheet, "Application Report - " + request.getPeriodCovered(), titleStyle);
            
            // Add summary data
            int rowNum = 3;
            addSummaryRow(summarySheet, rowNum++, "Total Applications", statistics.getTotalApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "New Applications", statistics.getNewApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "In Progress Applications", statistics.getInProgressApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Completed Applications", statistics.getCompletedApplications().toString(), dataStyle);
            addSummaryRow(summarySheet, rowNum++, "Average Completion Time", statistics.getAverageCompletionTimeInDays() + " days", dataStyle);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                Sheet stateSheet = workbook.createSheet("State Breakdown");
                addBreakdownSheet(stateSheet, "Applications by State", statistics.getApplicationsByState(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                Sheet planSheet = workbook.createSheet("Plan Breakdown");
                addBreakdownSheet(planSheet, "Applications by Plan", statistics.getApplicationsByPlan(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByAge()) {
                Sheet ageSheet = workbook.createSheet("Age Breakdown");
                addBreakdownSheet(ageSheet, "Applications by Age Group", statistics.getApplicationsByAgeGroup(), headerStyle, dataStyle);
            }
            
            if (request.isIncludeBreakdownByIncome()) {
                Sheet incomeSheet = workbook.createSheet("Income Breakdown");
                addBreakdownSheet(incomeSheet, "Applications by Income Level", statistics.getApplicationsByIncomeLevel(), headerStyle, dataStyle);
            }
            
            // Add monthly trends
            Sheet trendsSheet = workbook.createSheet("Monthly Trends");
            addBreakdownSheet(trendsSheet, "Monthly Application Trends", statistics.getMonthlyApplicationTrends(), headerStyle, dataStyle);
            
            // Add application methods
            Sheet methodsSheet = workbook.createSheet("Application Methods");
            addBreakdownSheet(methodsSheet, "Application Methods", statistics.getApplicationMethods(), headerStyle, dataStyle);
            
            // Auto-size columns for all sheets
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (int j = 0; j < 2; j++) {
                    sheet.autoSizeColumn(j);
                }
            }
            
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating application report Excel: " + e.getMessage());
            throw new RuntimeException("Failed to generate application report Excel", e);
        }
    }

    @Override
    public byte[] generateComprehensiveReportExcel(GovernmentReportRequest request, EligibilityStatistics eligibilityStats,
            BenefitStatistics benefitStats, ApplicationStatistics applicationStats) {
        logger.info("Generating comprehensive report Excel for period: " + request.getPeriodCovered());
        
        try (Workbook workbook = new XSSFWorkbook(); 
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            // Create styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dataStyle = createDataStyle(workbook);
            CellStyle titleStyle = createTitleStyle(workbook);
            
            // Create overview sheet
            Sheet overviewSheet = workbook.createSheet("Overview");
            
            // Add title
            addTitle(overviewSheet, "Comprehensive Government Report - " + request.getPeriodCovered(), titleStyle);
            
            // Add summary data
            int rowNum = 3;
            addSummaryRow(overviewSheet, rowNum++, "Report Period", request.getPeriodCovered(), dataStyle);
            addSummaryRow(overviewSheet, rowNum++, "Department", request.getDepartmentName(), dataStyle);
            addSummaryRow(overviewSheet, rowNum++, "Generated For", request.getGeneratedFor(), dataStyle);
            addSummaryRow(overviewSheet, rowNum++, "Generation Date", 
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), dataStyle);
            
            rowNum += 2;
            addSummaryRow(overviewSheet, rowNum++, "Total Applications", applicationStats.getTotalApplications().toString(), dataStyle);
            addSummaryRow(overviewSheet, rowNum++, "Approved Applications", eligibilityStats.getApprovedApplications().toString(), dataStyle);
            addSummaryRow(overviewSheet, rowNum++, "Total Benefits Issued", benefitStats.getTotalBenefitsIssued().toString(), dataStyle);
            addSummaryRow(overviewSheet, rowNum++, "Total Amount Issued", "$" + String.format("%,.2f", benefitStats.getTotalAmountIssued()), dataStyle);
            
            // Create application sheet
            Sheet applicationSheet = workbook.createSheet("Application Statistics");
            rowNum = 0;
            addTitle(applicationSheet, "Application Statistics", titleStyle);
            rowNum = 3;
            addSummaryRow(applicationSheet, rowNum++, "Total Applications", applicationStats.getTotalApplications().toString(), dataStyle);
            addSummaryRow(applicationSheet, rowNum++, "New Applications", applicationStats.getNewApplications().toString(), dataStyle);
            addSummaryRow(applicationSheet, rowNum++, "In Progress Applications", applicationStats.getInProgressApplications().toString(), dataStyle);
            addSummaryRow(applicationSheet, rowNum++, "Completed Applications", applicationStats.getCompletedApplications().toString(), dataStyle);
            addSummaryRow(applicationSheet, rowNum++, "Average Completion Time", applicationStats.getAverageCompletionTimeInDays() + " days", dataStyle);
            
            // Create eligibility sheet
            Sheet eligibilitySheet = workbook.createSheet("Eligibility Statistics");
            rowNum = 0;
            addTitle(eligibilitySheet, "Eligibility Statistics", titleStyle);
            rowNum = 3;
            addSummaryRow(eligibilitySheet, rowNum++, "Total Applications", eligibilityStats.getTotalApplications().toString(), dataStyle);
            addSummaryRow(eligibilitySheet, rowNum++, "Approved Applications", eligibilityStats.getApprovedApplications().toString(), dataStyle);
            addSummaryRow(eligibilitySheet, rowNum++, "Denied Applications", eligibilityStats.getDeniedApplications().toString(), dataStyle);
            addSummaryRow(eligibilitySheet, rowNum++, "Pending Applications", eligibilityStats.getPendingApplications().toString(), dataStyle);
            addSummaryRow(eligibilitySheet, rowNum++, "Approval Rate", eligibilityStats.getApprovalRate() + "%", dataStyle);
            addSummaryRow(eligibilitySheet, rowNum++, "Denial Rate", eligibilityStats.getDenialRate() + "%", dataStyle);
            
            // Create benefit sheet
            Sheet benefitSheet = workbook.createSheet("Benefit Statistics");
            rowNum = 0;
            addTitle(benefitSheet, "Benefit Issuance Statistics", titleStyle);
            rowNum = 3;
            addSummaryRow(benefitSheet, rowNum++, "Total Benefits Issued", benefitStats.getTotalBenefitsIssued().toString(), dataStyle);
            addSummaryRow(benefitSheet, rowNum++, "Total Amount Issued", "$" + String.format("%,.2f", benefitStats.getTotalAmountIssued()), dataStyle);
            addSummaryRow(benefitSheet, rowNum++, "Average Benefit Amount", "$" + String.format("%,.2f", benefitStats.getAverageBenefitAmount()), dataStyle);
            addSummaryRow(benefitSheet, rowNum++, "Active Recipients", benefitStats.getActiveRecipients().toString(), dataStyle);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                Sheet stateSheet = workbook.createSheet("State Breakdown");
                rowNum = 0;
                addTitle(stateSheet, "State Breakdown", titleStyle);
                rowNum = 3;
                addBreakdownTable(stateSheet, rowNum, "Applications by State", applicationStats.getApplicationsByState(), headerStyle, dataStyle);
                rowNum += applicationStats.getApplicationsByState().size() + 3;
                addBreakdownTable(stateSheet, rowNum, "Eligibility by State", eligibilityStats.getBreakdownByState(), headerStyle, dataStyle);
                rowNum += eligibilityStats.getBreakdownByState().size() + 3;
                addBreakdownTable(stateSheet, rowNum, "Benefits by State", benefitStats.getAmountByState(), headerStyle, dataStyle);
            }
            
            // Auto-size columns for all sheets
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (int j = 0; j < 2; j++) {
                    sheet.autoSizeColumn(j);
                }
            }
            
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating comprehensive report Excel: " + e.getMessage());
            throw new RuntimeException("Failed to generate comprehensive report Excel", e);
        }
    }
    
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        style.setFont(font);
        
        return style;
    }
    
    private CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        
        return style;
    }
    
    private CellStyle createTitleStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        
        return style;
    }
    
    private void addTitle(Sheet sheet, String title, CellStyle style) {
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(style);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 1));
    }
    
    private void addSummaryRow(Sheet sheet, int rowNum, String label, String value, CellStyle style) {
        Row row = sheet.createRow(rowNum);
        
        Cell labelCell = row.createCell(0);
        labelCell.setCellValue(label);
        labelCell.setCellStyle(style);
        
        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        valueCell.setCellStyle(style);
    }
    
    private <T> void addBreakdownSheet(Sheet sheet, String title, Map<String, T> data, CellStyle headerStyle, CellStyle dataStyle) {
        // Add title
        addTitle(sheet, title, headerStyle);
        
        // Add header row
        Row headerRow = sheet.createRow(2);
        Cell categoryCell = headerRow.createCell(0);
        categoryCell.setCellValue("Category");
        categoryCell.setCellStyle(headerStyle);
        
        Cell valueCell = headerRow.createCell(1);
        valueCell.setCellValue("Value");
        valueCell.setCellStyle(headerStyle);
        
        // Add data rows
        int rowNum = 3;
        for (Map.Entry<String, T> entry : data.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            
            Cell keyCell = row.createCell(0);
            keyCell.setCellValue(entry.getKey());
            keyCell.setCellStyle(dataStyle);
            
            Cell valCell = row.createCell(1);
            if (entry.getValue() instanceof Double) {
                valCell.setCellValue("$" + String.format("%,.2f", entry.getValue()));
            } else {
                valCell.setCellValue(entry.getValue().toString());
            }
            valCell.setCellStyle(dataStyle);
        }
    }
    
    private <T> void addBreakdownTable(Sheet sheet, int startRow, String title, Map<String, T> data, CellStyle headerStyle, CellStyle dataStyle) {
        // Add title
        Row titleRow = sheet.createRow(startRow);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(startRow, startRow, 0, 1));
        
        // Add header row
        Row headerRow = sheet.createRow(startRow + 1);
        Cell categoryCell = headerRow.createCell(0);
        categoryCell.setCellValue("Category");
        categoryCell.setCellStyle(headerStyle);
        
        Cell valueCell = headerRow.createCell(1);
        valueCell.setCellValue("Value");
        valueCell.setCellStyle(headerStyle);
        
        // Add data rows
        int rowNum = startRow + 2;
        for (Map.Entry<String, T> entry : data.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            
            Cell keyCell = row.createCell(0);
            keyCell.setCellValue(entry.getKey());
            keyCell.setCellStyle(dataStyle);
            
            Cell valCell = row.createCell(1);
            if (entry.getValue() instanceof Double) {
                valCell.setCellValue("$" + String.format("%,.2f", entry.getValue()));
            } else {
                valCell.setCellValue(entry.getValue().toString());
            }
            valCell.setCellStyle(dataStyle);
        }
    }
}
