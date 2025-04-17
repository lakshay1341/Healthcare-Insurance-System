package in.lakshay.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import in.lakshay.model.ApplicationStatistics;
import in.lakshay.model.BenefitStatistics;
import in.lakshay.model.EligibilityStatistics;
import in.lakshay.model.GovernmentReportRequest;
import in.lakshay.service.PdfGenerationService;

/**
 * Implementation of the PDF generation service
 */
@Service
public class PdfGenerationServiceImpl implements PdfGenerationService {
    
    private static final Logger logger = Logger.getLogger(PdfGenerationServiceImpl.class.getName());

    private static final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
    private static final Font SUBTITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
    private static final Font SECTION_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
    private static final Font NORMAL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
    private static final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);

    @Override
    public byte[] generateEligibilityReportPdf(GovernmentReportRequest request, EligibilityStatistics statistics) {
        logger.info("Generating eligibility report PDF for period: " + request.getPeriodCovered());
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Add title
            addTitle(document, "Eligibility Report", request);
            
            // Add summary
            addEligibilitySummary(document, statistics);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                addBreakdownTable(document, "Breakdown by State", statistics.getBreakdownByState());
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                addBreakdownTable(document, "Breakdown by Plan", statistics.getBreakdownByPlan());
            }
            
            if (request.isIncludeBreakdownByAge()) {
                addBreakdownTable(document, "Breakdown by Age Group", statistics.getBreakdownByAgeGroup());
            }
            
            if (request.isIncludeBreakdownByIncome()) {
                addBreakdownTable(document, "Breakdown by Income Level", statistics.getBreakdownByIncomeLevel());
            }
            
            // Add denial reasons
            addBreakdownTable(document, "Denial Reasons", statistics.getDenialReasons());
            
            // Add footer
            addFooter(document);
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating eligibility report PDF: " + e.getMessage());
            throw new RuntimeException("Failed to generate eligibility report PDF", e);
        }
    }

    @Override
    public byte[] generateBenefitReportPdf(GovernmentReportRequest request, BenefitStatistics statistics) {
        logger.info("Generating benefit report PDF for period: " + request.getPeriodCovered());
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Add title
            addTitle(document, "Benefit Issuance Report", request);
            
            // Add summary
            addBenefitSummary(document, statistics);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                addBreakdownTable(document, "Amount by State", statistics.getAmountByState());
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                addBreakdownTable(document, "Amount by Plan", statistics.getAmountByPlan());
            }
            
            if (request.isIncludeBreakdownByAge()) {
                addBreakdownTable(document, "Amount by Age Group", statistics.getAmountByAgeGroup());
            }
            
            if (request.isIncludeBreakdownByIncome()) {
                addBreakdownTable(document, "Amount by Income Level", statistics.getAmountByIncomeLevel());
            }
            
            // Add monthly trends
            addBreakdownTable(document, "Monthly Issuance Trends", statistics.getMonthlyIssuanceTrends());
            
            // Add footer
            addFooter(document);
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating benefit report PDF: " + e.getMessage());
            throw new RuntimeException("Failed to generate benefit report PDF", e);
        }
    }

    @Override
    public byte[] generateApplicationReportPdf(GovernmentReportRequest request, ApplicationStatistics statistics) {
        logger.info("Generating application report PDF for period: " + request.getPeriodCovered());
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Add title
            addTitle(document, "Application Report", request);
            
            // Add summary
            addApplicationSummary(document, statistics);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                addBreakdownTable(document, "Applications by State", statistics.getApplicationsByState());
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                addBreakdownTable(document, "Applications by Plan", statistics.getApplicationsByPlan());
            }
            
            if (request.isIncludeBreakdownByAge()) {
                addBreakdownTable(document, "Applications by Age Group", statistics.getApplicationsByAgeGroup());
            }
            
            if (request.isIncludeBreakdownByIncome()) {
                addBreakdownTable(document, "Applications by Income Level", statistics.getApplicationsByIncomeLevel());
            }
            
            // Add monthly trends
            addBreakdownTable(document, "Monthly Application Trends", statistics.getMonthlyApplicationTrends());
            
            // Add application methods
            addBreakdownTable(document, "Application Methods", statistics.getApplicationMethods());
            
            // Add footer
            addFooter(document);
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating application report PDF: " + e.getMessage());
            throw new RuntimeException("Failed to generate application report PDF", e);
        }
    }

    @Override
    public byte[] generateComprehensiveReportPdf(GovernmentReportRequest request, EligibilityStatistics eligibilityStats,
            BenefitStatistics benefitStats, ApplicationStatistics applicationStats) {
        logger.info("Generating comprehensive report PDF for period: " + request.getPeriodCovered());
        
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Add title
            addTitle(document, "Comprehensive Government Report", request);
            
            // Add application section
            addSection(document, "Application Statistics");
            addApplicationSummary(document, applicationStats);
            
            // Add eligibility section
            addSection(document, "Eligibility Statistics");
            addEligibilitySummary(document, eligibilityStats);
            
            // Add benefit section
            addSection(document, "Benefit Issuance Statistics");
            addBenefitSummary(document, benefitStats);
            
            // Add breakdowns if requested
            if (request.isIncludeBreakdownByState()) {
                addSection(document, "State Breakdowns");
                addBreakdownTable(document, "Applications by State", applicationStats.getApplicationsByState());
                addBreakdownTable(document, "Eligibility by State", eligibilityStats.getBreakdownByState());
                addBreakdownTable(document, "Benefits by State", benefitStats.getAmountByState());
            }
            
            if (request.isIncludeBreakdownByPlan()) {
                addSection(document, "Plan Breakdowns");
                addBreakdownTable(document, "Applications by Plan", applicationStats.getApplicationsByPlan());
                addBreakdownTable(document, "Eligibility by Plan", eligibilityStats.getBreakdownByPlan());
                addBreakdownTable(document, "Benefits by Plan", benefitStats.getAmountByPlan());
            }
            
            // Add footer
            addFooter(document);
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.severe("Error generating comprehensive report PDF: " + e.getMessage());
            throw new RuntimeException("Failed to generate comprehensive report PDF", e);
        }
    }
    
    private void addTitle(Document document, String title, GovernmentReportRequest request) throws DocumentException {
        Paragraph titlePara = new Paragraph(title, TITLE_FONT);
        titlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(titlePara);
        
        Paragraph subtitlePara = new Paragraph("Period: " + request.getPeriodCovered(), SUBTITLE_FONT);
        subtitlePara.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitlePara);
        
        Paragraph departmentPara = new Paragraph("Department: " + request.getDepartmentName(), SUBTITLE_FONT);
        departmentPara.setAlignment(Element.ALIGN_CENTER);
        document.add(departmentPara);
        
        document.add(Chunk.NEWLINE);
    }
    
    private void addSection(Document document, String sectionTitle) throws DocumentException {
        Paragraph sectionPara = new Paragraph(sectionTitle, SECTION_FONT);
        sectionPara.setSpacingBefore(10);
        document.add(sectionPara);
    }
    
    private void addEligibilitySummary(Document document, EligibilityStatistics statistics) throws DocumentException {
        addSection(document, "Eligibility Summary");
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        addSummaryRow(table, "Total Applications", statistics.getTotalApplications().toString());
        addSummaryRow(table, "Approved Applications", statistics.getApprovedApplications().toString());
        addSummaryRow(table, "Denied Applications", statistics.getDeniedApplications().toString());
        addSummaryRow(table, "Pending Applications", statistics.getPendingApplications().toString());
        addSummaryRow(table, "Approval Rate", statistics.getApprovalRate() + "%");
        addSummaryRow(table, "Denial Rate", statistics.getDenialRate() + "%");
        addSummaryRow(table, "Average Processing Time", statistics.getAverageProcessingTimeInDays() + " days");
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addBenefitSummary(Document document, BenefitStatistics statistics) throws DocumentException {
        addSection(document, "Benefit Issuance Summary");
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        addSummaryRow(table, "Total Benefits Issued", statistics.getTotalBenefitsIssued().toString());
        addSummaryRow(table, "Total Amount Issued", "$" + String.format("%,.2f", statistics.getTotalAmountIssued()));
        addSummaryRow(table, "Average Benefit Amount", "$" + String.format("%,.2f", statistics.getAverageBenefitAmount()));
        addSummaryRow(table, "Active Recipients", statistics.getActiveRecipients().toString());
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addApplicationSummary(Document document, ApplicationStatistics statistics) throws DocumentException {
        addSection(document, "Application Summary");
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        addSummaryRow(table, "Total Applications", statistics.getTotalApplications().toString());
        addSummaryRow(table, "New Applications", statistics.getNewApplications().toString());
        addSummaryRow(table, "In Progress Applications", statistics.getInProgressApplications().toString());
        addSummaryRow(table, "Completed Applications", statistics.getCompletedApplications().toString());
        addSummaryRow(table, "Average Completion Time", statistics.getAverageCompletionTimeInDays() + " days");
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addSummaryRow(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, NORMAL_FONT));
        labelCell.setBorder(PdfPCell.NO_BORDER);
        labelCell.setPadding(5);
        
        PdfPCell valueCell = new PdfPCell(new Phrase(value, NORMAL_FONT));
        valueCell.setBorder(PdfPCell.NO_BORDER);
        valueCell.setPadding(5);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        table.addCell(labelCell);
        table.addCell(valueCell);
    }
    
    private <T> void addBreakdownTable(Document document, String title, Map<String, T> data) throws DocumentException {
        addSection(document, title);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        // Add header
        PdfPCell categoryCell = new PdfPCell(new Phrase("Category", HEADER_FONT));
        categoryCell.setBackgroundColor(BaseColor.DARK_GRAY);
        categoryCell.setPadding(5);
        
        PdfPCell valueCell = new PdfPCell(new Phrase("Value", HEADER_FONT));
        valueCell.setBackgroundColor(BaseColor.DARK_GRAY);
        valueCell.setPadding(5);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        table.addCell(categoryCell);
        table.addCell(valueCell);
        
        // Add data rows
        for (Map.Entry<String, T> entry : data.entrySet()) {
            PdfPCell keyCell = new PdfPCell(new Phrase(entry.getKey(), NORMAL_FONT));
            keyCell.setPadding(5);
            
            String valueStr;
            if (entry.getValue() instanceof Double) {
                valueStr = "$" + String.format("%,.2f", entry.getValue());
            } else {
                valueStr = entry.getValue().toString();
            }
            
            PdfPCell valCell = new PdfPCell(new Phrase(valueStr, NORMAL_FONT));
            valCell.setPadding(5);
            valCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            table.addCell(keyCell);
            table.addCell(valCell);
        }
        
        document.add(table);
        document.add(Chunk.NEWLINE);
    }
    
    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("Generated on: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), NORMAL_FONT);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
        
        Paragraph disclaimer = new Paragraph("This is an official government report. " +
                "The information contained in this report is confidential and intended only for authorized personnel.", NORMAL_FONT);
        disclaimer.setAlignment(Element.ALIGN_CENTER);
        document.add(disclaimer);
    }
}
