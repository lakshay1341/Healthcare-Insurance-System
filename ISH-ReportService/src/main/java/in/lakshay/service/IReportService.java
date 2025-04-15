package in.lakshay.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import in.lakshay.model.ApplicationReportDTO;
import in.lakshay.model.BenefitReportDTO;
import in.lakshay.model.DataCollectionReportDTO;
import in.lakshay.model.EligibilityReportDTO;
import in.lakshay.model.ReportSearchCriteria;
import in.lakshay.model.SearchInputs;
import in.lakshay.model.SearchResults;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Service interface for report operations
 */
public interface IReportService {

    /**
     * Get all report types
     */
    Set<String> getAllReportTypes();

    /**
     * Get all report formats
     */
    Set<String> getAllReportFormats();

    /**
     * Get all plan names
     */
    Set<String> getAllPlanNames();

    /**
     * Get all states
     */
    Set<String> getAllStates();

    /**
     * Search for eligibility reports based on criteria
     */
    List<EligibilityReportDTO> searchEligibilityReports(ReportSearchCriteria criteria);

    /**
     * Search for benefit reports based on criteria
     */
    List<BenefitReportDTO> searchBenefitReports(ReportSearchCriteria criteria);

    /**
     * Search for application reports based on criteria
     */
    List<ApplicationReportDTO> searchApplicationReports(ReportSearchCriteria criteria);

    /**
     * Search for data collection reports based on criteria
     */
    List<DataCollectionReportDTO> searchDataCollectionReports(ReportSearchCriteria criteria);

    /**
     * Generate eligibility report in PDF format
     */
    void generateEligibilityPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate eligibility report in Excel format
     */
    void generateEligibilityExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate benefit report in PDF format
     */
    void generateBenefitPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate benefit report in Excel format
     */
    void generateBenefitExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate application report in PDF format
     */
    void generateApplicationPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate application report in Excel format
     */
    void generateApplicationExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate data collection report in PDF format
     */
    void generateDataCollectionPdfReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    /**
     * Generate data collection report in Excel format
     */
    void generateDataCollectionExcelReport(ReportSearchCriteria criteria, HttpServletResponse response) throws Exception;

    // Legacy methods for backward compatibility
    Set<String> getAllCategories();
    List<SearchResults> searchByFilters(SearchInputs inputs);
    void generatePdfReport(SearchInputs inputs, HttpServletResponse response) throws Exception;
    void generateExcelReport(SearchInputs inputs, HttpServletResponse response) throws Exception;
    void generateCompletePdfReport(HttpServletResponse response) throws Exception;
    void generateCompleteExcelReport(HttpServletResponse response) throws Exception;
    Set<String> getAllFormats();
}
