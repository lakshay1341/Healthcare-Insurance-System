package in.lakshay.service;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

import in.lakshay.model.SearchInputs;
import in.lakshay.model.SearchResults;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Implementation of the report service
 */
@Service
public class ReportServiceImpl implements IReportService {

    @Override
    public Set<String> getAllCategories() {
        // Simulated data - in a real implementation, this would come from a database
        Set<String> categories = new HashSet<>();
        categories.add("Health Insurance");
        categories.add("Medicaid");
        categories.add("Medicare");
        categories.add("CHIP");
        categories.add("Eligibility");
        return categories;
    }

    @Override
    public Set<String> getAllFormats() {
        // Simulated data - in a real implementation, this would come from a database
        Set<String> formats = new HashSet<>();
        formats.add("PDF");
        formats.add("Excel");
        formats.add("CSV");
        formats.add("JSON");
        return formats;
    }

    @Override
    public List<SearchResults> searchByFilters(SearchInputs inputs) {
        // Simulated data - in a real implementation, this would query a database
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
        // Get search results
        List<SearchResults> results = searchByFilters(inputs);
        
        // Create PDF document
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        
        // Add title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        titleFont.setSize(18);
        titleFont.setColor(Color.BLUE);
        
        Paragraph title = new Paragraph("Insurance System for Health - Report", titleFont);
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
        table.setWidths(new float[] {1.0f, 3.0f, 2.0f, 1.5f, 2.0f, 1.5f});
        
        // Add table header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headerFont.setColor(Color.WHITE);
        
        PdfPCell headerCell = new PdfPCell();
        headerCell.setBackgroundColor(Color.BLUE);
        headerCell.setPadding(5);
        
        headerCell.setPhrase(new Phrase("ID", headerFont));
        table.addCell(headerCell);
        
        headerCell.setPhrase(new Phrase("Name", headerFont));
        table.addCell(headerCell);
        
        headerCell.setPhrase(new Phrase("Category", headerFont));
        table.addCell(headerCell);
        
        headerCell.setPhrase(new Phrase("Format", headerFont));
        table.addCell(headerCell);
        
        headerCell.setPhrase(new Phrase("Created Date", headerFont));
        table.addCell(headerCell);
        
        headerCell.setPhrase(new Phrase("Status", headerFont));
        table.addCell(headerCell);
        
        // Add data rows
        for (SearchResults result : results) {
            table.addCell(String.valueOf(result.getId()));
            table.addCell(result.getName());
            table.addCell(result.getCategory());
            table.addCell(result.getFormat());
            table.addCell(result.getCreatedDate().toString());
            table.addCell(result.getStatus());
        }
        
        document.add(table);
        document.close();
    }

    @Override
    public void generateExcelReport(SearchInputs inputs, HttpServletResponse response) throws Exception {
        // Get search results
        List<SearchResults> results = searchByFilters(inputs);
        
        // Create Excel workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Report Results");
        
        // Create header row
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Category");
        headerRow.createCell(3).setCellValue("Format");
        headerRow.createCell(4).setCellValue("Created Date");
        headerRow.createCell(5).setCellValue("Status");
        
        // Add data rows
        int rowNum = 1;
        for (SearchResults result : results) {
            HSSFRow row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(result.getId());
            row.createCell(1).setCellValue(result.getName());
            row.createCell(2).setCellValue(result.getCategory());
            row.createCell(3).setCellValue(result.getFormat());
            row.createCell(4).setCellValue(result.getCreatedDate().toString());
            row.createCell(5).setCellValue(result.getStatus());
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
    public void generateCompletePdfReport(HttpServletResponse response) throws Exception {
        // For demonstration, we'll use the same implementation as the filtered report
        // In a real application, this would retrieve all data without filters
        generatePdfReport(new SearchInputs(), response);
    }

    @Override
    public void generateCompleteExcelReport(HttpServletResponse response) throws Exception {
        // For demonstration, we'll use the same implementation as the filtered report
        // In a real application, this would retrieve all data without filters
        generateExcelReport(new SearchInputs(), response);
    }
}
