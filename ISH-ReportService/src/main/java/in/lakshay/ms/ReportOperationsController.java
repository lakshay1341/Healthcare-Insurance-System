package in.lakshay.ms;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.model.SearchInputs;
import in.lakshay.model.SearchResults;
import in.lakshay.service.IReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller for report generation operations
 */
@RestController
@RequestMapping("/report-api")
@Tag(name = "Report API", description = "API for generating various reports")
public class ReportOperationsController {
    
    @Autowired
    private IReportService reportService;
    
    @Operation(
        summary = "Get available report categories",
        description = "Returns a list of all available report categories",
        responses = {
            @ApiResponse(
                description = "List of categories",
                responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Set.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500")
        }
    )
    @GetMapping("/categories")
    public ResponseEntity<?> getReportCategories() {
        try {
            Set<String> categories = reportService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
        summary = "Search for reports",
        description = "Search for reports based on provided criteria",
        responses = {
            @ApiResponse(
                description = "Search results",
                responseCode = "200",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
            ),
            @ApiResponse(description = "Internal server error", responseCode = "500")
        }
    )
    @PostMapping("/search")
    public ResponseEntity<?> searchReports(@RequestBody SearchInputs inputs) {
        try {
            List<SearchResults> results = reportService.searchByFilters(inputs);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @Operation(
        summary = "Generate PDF report",
        description = "Generate a PDF report based on search criteria"
    )
    @PostMapping("/pdf")
    public void generatePdfReport(@RequestBody SearchInputs inputs, HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=report.pdf");
            reportService.generatePdfReport(inputs, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Operation(
        summary = "Generate Excel report",
        description = "Generate an Excel report based on search criteria"
    )
    @PostMapping("/excel")
    public void generateExcelReport(@RequestBody SearchInputs inputs, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=report.xls");
            reportService.generateExcelReport(inputs, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Operation(
        summary = "Generate complete PDF report",
        description = "Generate a PDF report with all data"
    )
    @GetMapping("/pdf/all")
    public void generateCompletePdfReport(HttpServletResponse response) {
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=complete-report.pdf");
            reportService.generateCompletePdfReport(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Operation(
        summary = "Generate complete Excel report",
        description = "Generate an Excel report with all data"
    )
    @GetMapping("/excel/all")
    public void generateCompleteExcelReport(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=complete-report.xls");
            reportService.generateCompleteExcelReport(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
