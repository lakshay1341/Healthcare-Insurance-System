package in.lakshay.service;

import java.awt.Color;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.lakshay.model.ApplicationReportDTO;
import in.lakshay.model.BenefitReportDTO;
import in.lakshay.model.DataCollectionReportDTO;
import in.lakshay.model.EligibilityReportDTO;
import in.lakshay.model.ReportSearchCriteria;
import in.lakshay.model.SearchInputs;
import in.lakshay.model.SearchResults;
import in.lakshay.repository.IApplicationReportRepository;
import in.lakshay.repository.IBenefitReportRepository;
import in.lakshay.repository.IDataCollectionReportRepository;
import in.lakshay.repository.IEligibilityReportRepository;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implementation of the report service
 */
@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IEligibilityReportRepository eligibilityReportRepository;

    @Autowired
    private IBenefitReportRepository benefitReportRepository;

    @Autowired
    private IApplicationReportRepository applicationReportRepository;

    @Autowired
    private IDataCollectionReportRepository dataCollectionReportRepository;

    @Override
    public Set<String> getAllReportTypes() {
        Set<String> reportTypes = new HashSet<>();
        reportTypes.add("Eligibility");
        reportTypes.add("Benefit");
        reportTypes.add("Application");
        reportTypes.add("Data Collection");
        return reportTypes;
    }

    @Override
    public Set<String> getAllReportFormats() {
        Set<String> formats = new HashSet<>();
        formats.add("PDF");
        formats.add("Excel");
        formats.add("CSV");
        formats.add("JSON");
        return formats;
    }

    @Override
    public Set<String> getAllPlanNames() {
        Set<String> planNames = new HashSet<>();
        planNames.add("SNAP");
        planNames.add("CCAP");
        planNames.add("Medicaid");
        planNames.add("Medicare");
        planNames.add("QHP");
        return planNames;
    }

    @Override
    public Set<String> getAllStates() {
        Set<String> states = new HashSet<>();
        states.add("California");
        states.add("Texas");
        states.add("Florida");
        states.add("New York");
        states.add("Washington DC");
        states.add("Ohio");
        return states;
    }

    @Override
    public Set<String> getAllCategories() {
        // Legacy method - redirects to getAllReportTypes
        return getAllReportTypes();
    }

    @Override
    public Set<String> getAllFormats() {
        // Legacy method - redirects to getAllReportFormats
        return getAllReportFormats();
    }

    @Override
    public List<EligibilityReportDTO> searchEligibilityReports(ReportSearchCriteria criteria) {
        // Simulated data - in a real implementation, this would query the database
        List<EligibilityReportDTO> results = new ArrayList<>();

        // Add some sample data
        results.add(new EligibilityReportDTO(
                1, "Monthly Eligibility Report", "PDF", "Completed",
                "Monthly report of eligibility determinations", "/reports/eligibility/monthly.pdf",
                1L, 1000, "SNAP", "Approved", LocalDate.now().minusDays(5),
                LocalDate.now().plusYears(2), 200.0, null, "John Doe", 123456704L,
                LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5), "admin", "admin"));

        results.add(new EligibilityReportDTO(
                2, "Weekly Eligibility Report", "Excel", "Completed",
                "Weekly report of eligibility determinations", "/reports/eligibility/weekly.xlsx",
                2L, 1001, "Medicaid", "Denied", null,
                null, null, "High Income", "Jane Smith", 987654304L,
                LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(7), "admin", "admin"));

        return results;
    }

    @Override
    public List<BenefitReportDTO> searchBenefitReports(ReportSearchCriteria criteria) {
        // Simulated data - in a real implementation, this would query the database
        List<BenefitReportDTO> results = new ArrayList<>();

        // Add some sample data
        results.add(new BenefitReportDTO(
                1, "Monthly Benefit Report", "PDF", "Completed",
                "Monthly report of benefits issued", "/reports/benefits/monthly.pdf",
                1L, "John Doe", 123456704L, "SNAP", 200.0, "Chase Bank", 123456789012L,
                "Issued", LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5),
                "admin", "admin"));

        return results;
    }

    @Override
    public List<ApplicationReportDTO> searchApplicationReports(ReportSearchCriteria criteria) {
        // Simulated data - in a real implementation, this would query the database
        List<ApplicationReportDTO> results = new ArrayList<>();

        // Add some sample data
        results.add(new ApplicationReportDTO(
                1, "Monthly Application Report", "PDF", "Completed",
                "Monthly report of applications", "/reports/applications/monthly.pdf",
                1000, "John Doe", "john.doe@example.com", "M", 5551234567L, 123456704L,
                "California", LocalDate.parse("1985-05-15"),
                LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5),
                "admin", "admin"));

        return results;
    }

    @Override
    public List<DataCollectionReportDTO> searchDataCollectionReports(ReportSearchCriteria criteria) {
        // Simulated data - in a real implementation, this would query the database
        List<DataCollectionReportDTO> results = new ArrayList<>();

        // Add some sample data
        results.add(new DataCollectionReportDTO(
                1, "Monthly Data Collection Report", "PDF", "Completed",
                "Monthly report of data collection", "/reports/datacollection/monthly.pdf",
                1L, 1000, 1, "SNAP", 2500.0, 500.0, "Bachelor's Degree", 2018, 1,
                LocalDateTime.now().minusDays(5), LocalDateTime.now().minusDays(5),
                "admin", "admin"));

        return results;
    }

    @Override
    public List<SearchResults> searchByFilters(SearchInputs inputs) {
        // Legacy method - simulated data for backward compatibility
        List<SearchResults> results = new ArrayList<>();

        // Add some sample data
        results.add(new SearchResults(1, "Monthly Eligibility Report", "Eligibility", "PDF",
                LocalDateTime.now().minusDays(5), "Completed", "Monthly report of eligibility determinations"));

        results.add(new SearchResults(2, "Quarterly Benefit Issuance", "Benefits", "Excel",
                LocalDateTime.now().minusDays(30), "Completed", "Quarterly summary of benefits issued"));

        results.add(new SearchResults(3, "Annual Program Summary", "Program", "PDF",
                LocalDateTime.now().minusDays(90), "Completed", "Annual summary of all program activities"));

        return results;
    }

    @Override
    public void generatePdfReport(SearchInputs inputs, HttpServletResponse response) throws Exception {
        // Legacy method - redirects to generateEligibilityPdfReport
        ReportSearchCriteria criteria = new ReportSearchCriteria();
        criteria.setReportType("Eligibility");
        generateEligibilityPdfReport(criteria, response);
    }

    @Override
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse response) throws Exception {
        // Legacy method - redirects to generateEligibilityExcelReport
        ReportSearchCriteria criteria = new ReportSearchCriteria();
        criteria.setReportType("Eligibility");
        generateEligibilityExcelReport(criteria, response);
    }



    @Override
    public void generateEligibilityPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get eligibility reports
        List<EligibilityReportDTO> reports = searchEligibilityReports(criteria);

        // Create PDF document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Insurance System for Health - Eligibility Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Add subtitle with filter information
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA);
        subtitleFont.setSize(12);

        Paragraph subtitle = new Paragraph("Report generated on: " + LocalDateTime.now(), subtitleFont);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subtitle);

        // Add empty line
        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.0f, 1.5f, 1.5f, 1.5f, 1.5f, 2.0f});

        // Add table header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setColor(Color.WHITE);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(Color.BLUE);
        headerCell.setPadding(5);

        headerCell.setPhrase(new Phrase("Case No", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Holder Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Plan Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Status", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Start Date", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("End Date", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Benefit Amount", headerFont));
        table.addCell(headerCell);

        // Add data rows
        for (EligibilityReportDTO report : reports) {
            table.addCell(String.valueOf(report.getCaseNo()));
            table.addCell(report.getHolderName());
            table.addCell(report.getPlanName());
            table.addCell(report.getPlanStatus());
            table.addCell(report.getPlanStartDate() != null ? report.getPlanStartDate().toString() : "");
            table.addCell(report.getPlanEndDate() != null ? report.getPlanEndDate().toString() : "");
            table.addCell(report.getBenefitAmt() != null ? String.valueOf(report.getBenefitAmt()) : "");
        }

        document.add(table);
        document.close();
    }

    @Override
    public void generateEligibilityExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get eligibility reports
        List<EligibilityReportDTO> reports = searchEligibilityReports(criteria);

        // Create Excel workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Eligibility Report");

        // Create header row
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Case No");
        headerRow.createCell(1).setCellValue("Holder Name");
        headerRow.createCell(2).setCellValue("Plan Name");
        headerRow.createCell(3).setCellValue("Status");
        headerRow.createCell(4).setCellValue("Start Date");
        headerRow.createCell(5).setCellValue("End Date");
        headerRow.createCell(6).setCellValue("Benefit Amount");

        // Add data rows
        int rowNum = 1;
        for (EligibilityReportDTO report : reports) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getCaseNo());
            row.createCell(1).setCellValue(report.getHolderName());
            row.createCell(2).setCellValue(report.getPlanName());
            row.createCell(3).setCellValue(report.getPlanStatus());
            row.createCell(4).setCellValue(report.getPlanStartDate() != null ? report.getPlanStartDate().toString() : "");
            row.createCell(5).setCellValue(report.getPlanEndDate() != null ? report.getPlanEndDate().toString() : "");
            row.createCell(6).setCellValue(report.getBenefitAmt() != null ? report.getBenefitAmt() : 0.0);
        }

        // Auto-size columns
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to response
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @Override
    public void generateBenefitPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get benefit reports
        List<BenefitReportDTO> reports = searchBenefitReports(criteria);

        // Create PDF document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Insurance System for Health - Benefit Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Add subtitle with filter information
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA);
        subtitleFont.setSize(12);

        Paragraph subtitle = new Paragraph("Report generated on: " + LocalDateTime.now(), subtitleFont);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subtitle);

        // Add empty line
        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.0f, 1.5f, 1.5f, 2.0f, 1.5f});

        // Add table header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setColor(Color.WHITE);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(Color.BLUE);
        headerCell.setPadding(5);

        headerCell.setPhrase(new Phrase("Case No", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Holder Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Plan Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Benefit Amount", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Bank Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Status", headerFont));
        table.addCell(headerCell);

        // Add data rows
        for (BenefitReportDTO report : reports) {
            table.addCell(String.valueOf(report.getCaseNo()));
            table.addCell(report.getHolderName());
            table.addCell(report.getPlanName());
            table.addCell(String.valueOf(report.getBenefitAmount()));
            table.addCell(report.getBankName());
            table.addCell(report.getIssuanceStatus());
        }

        document.add(table);
        document.close();
    }

    @Override
    public void generateBenefitExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get benefit reports
        List<BenefitReportDTO> reports = searchBenefitReports(criteria);

        // Create Excel workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Benefit Report");

        // Create header row
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Case No");
        headerRow.createCell(1).setCellValue("Holder Name");
        headerRow.createCell(2).setCellValue("Plan Name");
        headerRow.createCell(3).setCellValue("Benefit Amount");
        headerRow.createCell(4).setCellValue("Bank Name");
        headerRow.createCell(5).setCellValue("Status");

        // Add data rows
        int rowNum = 1;
        for (BenefitReportDTO report : reports) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getCaseNo());
            row.createCell(1).setCellValue(report.getHolderName());
            row.createCell(2).setCellValue(report.getPlanName());
            row.createCell(3).setCellValue(report.getBenefitAmount());
            row.createCell(4).setCellValue(report.getBankName());
            row.createCell(5).setCellValue(report.getIssuanceStatus());
        }

        // Auto-size columns
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to response
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @Override
    public void generateApplicationPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get application reports
        List<ApplicationReportDTO> reports = searchApplicationReports(criteria);

        // Create PDF document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Insurance System for Health - Application Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Add subtitle with filter information
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA);
        subtitleFont.setSize(12);

        Paragraph subtitle = new Paragraph("Report generated on: " + LocalDateTime.now(), subtitleFont);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subtitle);

        // Add empty line
        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.0f, 2.0f, 1.0f, 1.5f, 1.5f});

        // Add table header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setColor(Color.WHITE);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(Color.BLUE);
        headerCell.setPadding(5);

        headerCell.setPhrase(new Phrase("App ID", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Full Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Email", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Gender", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("State", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("DOB", headerFont));
        table.addCell(headerCell);

        // Add data rows
        for (ApplicationReportDTO report : reports) {
            table.addCell(String.valueOf(report.getAppId()));
            table.addCell(report.getFullName());
            table.addCell(report.getEmail());
            table.addCell(report.getGender());
            table.addCell(report.getStateName());
            table.addCell(report.getDob().toString());
        }

        document.add(table);
        document.close();
    }

    @Override
    public void generateApplicationExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get application reports
        List<ApplicationReportDTO> reports = searchApplicationReports(criteria);

        // Create Excel workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Application Report");

        // Create header row
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("App ID");
        headerRow.createCell(1).setCellValue("Full Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Gender");
        headerRow.createCell(4).setCellValue("State");
        headerRow.createCell(5).setCellValue("DOB");

        // Add data rows
        int rowNum = 1;
        for (ApplicationReportDTO report : reports) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getAppId());
            row.createCell(1).setCellValue(report.getFullName());
            row.createCell(2).setCellValue(report.getEmail());
            row.createCell(3).setCellValue(report.getGender());
            row.createCell(4).setCellValue(report.getStateName());
            row.createCell(5).setCellValue(report.getDob().toString());
        }

        // Auto-size columns
        for (int i = 0; i < 6; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to response
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @Override
    public void generateDataCollectionPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get data collection reports
        List<DataCollectionReportDTO> reports = searchDataCollectionReports(criteria);

        // Create PDF document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLUE);

        Paragraph title = new Paragraph("Insurance System for Health - Data Collection Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        // Add subtitle with filter information
        Font subtitleFont = FontFactory.getFont(FontFactory.HELVETICA);
        subtitleFont.setSize(12);

        Paragraph subtitle = new Paragraph("Report generated on: " + LocalDateTime.now(), subtitleFont);
        subtitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(subtitle);

        // Add empty line
        document.add(new Paragraph(" "));

        // Create table
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 1.0f, 1.5f, 1.5f, 1.5f, 2.0f, 1.5f});

        // Add table header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setColor(Color.WHITE);

        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(Color.BLUE);
        headerCell.setPadding(5);

        headerCell.setPhrase(new Phrase("Case No", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("App ID", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Plan Name", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Emp Income", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Property Income", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Education", headerFont));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Child Count", headerFont));
        table.addCell(headerCell);

        // Add data rows
        for (DataCollectionReportDTO report : reports) {
            table.addCell(String.valueOf(report.getCaseNo()));
            table.addCell(String.valueOf(report.getAppId()));
            table.addCell(report.getPlanName());
            table.addCell(String.valueOf(report.getEmpIncome()));
            table.addCell(String.valueOf(report.getPropertyIncome()));
            table.addCell(report.getHighestQlfy());
            table.addCell(String.valueOf(report.getChildCount()));
        }

        document.add(table);
        document.close();
    }

    @Override
    public void generateDataCollectionExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception {
        // Get data collection reports
        List<DataCollectionReportDTO> reports = searchDataCollectionReports(criteria);

        // Create Excel workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Data Collection Report");

        // Create header row
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Case No");
        headerRow.createCell(1).setCellValue("App ID");
        headerRow.createCell(2).setCellValue("Plan Name");
        headerRow.createCell(3).setCellValue("Emp Income");
        headerRow.createCell(4).setCellValue("Property Income");
        headerRow.createCell(5).setCellValue("Education");
        headerRow.createCell(6).setCellValue("Child Count");

        // Add data rows
        int rowNum = 1;
        for (DataCollectionReportDTO report : reports) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getCaseNo());
            row.createCell(1).setCellValue(report.getAppId());
            row.createCell(2).setCellValue(report.getPlanName());
            row.createCell(3).setCellValue(report.getEmpIncome());
            row.createCell(4).setCellValue(report.getPropertyIncome());
            row.createCell(5).setCellValue(report.getHighestQlfy());
            row.createCell(6).setCellValue(report.getChildCount());
        }

        // Auto-size columns
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to response
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
    }

    @Override
    public void generateCompletePdfReport(HttpServletResponse response) throws Exception {
        // Legacy method - redirects to generateEligibilityPdfReport
        generateEligibilityPdfReport(new ReportSearchCriteria(), response);
    }

    @Override
    public void generateCompleteExcelReport(HttpServletResponse response) throws Exception {
        // Legacy method - redirects to generateEligibilityExcelReport
        generateEligibilityExcelReport(new ReportSearchCriteria(), response);
    }
}
