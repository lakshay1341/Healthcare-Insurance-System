package in.lakshay.service;

import java.util.List;
import java.util.Set;

import in.lakshay.model.SearchInputs;
import in.lakshay.model.SearchResults;
import jakarta.servlet.http.HttpServletResponse;

public interface IReportService {

    Set<String> getAllCategories();

    Set<String> getAllFormats();

    List<SearchResults> searchByFilters(SearchInputs inputs);

    void generatePdfReport(SearchInputs inputs, HttpServletResponse response) throws Exception;

    void generateExcelReport(SearchInputs inputs, HttpServletResponse response) throws Exception;

    void generateCompletePdfReport(HttpServletResponse response) throws Exception;

    void generateCompleteExcelReport(HttpServletResponse response) throws Exception;
}
