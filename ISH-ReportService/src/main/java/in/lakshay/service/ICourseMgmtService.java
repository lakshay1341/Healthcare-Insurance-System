//ICourseMgmtService.java (Service Interface)
package in.lakshay.service;

import java.util.List;
import java.util.Set;

import in.lakshay.model.SearchInputs;
import in.lakshay.model.SearchResults;

import jakarta.servlet.http.HttpServletResponse;

public interface ICourseMgmtService {
    public  Set<String>    showAllCourseCategories();
    public  Set<String>    showAllTrainingModes();
    public   Set<String>    showAllFaculties();
    
    public   List<SearchResults> showCoursesByFilters(SearchInputs inputs);
    
    public   void    generatePdfReport(SearchInputs inputs,HttpServletResponse res)throws Exception;
    public   void    generateExcelReport(SearchInputs inputs,HttpServletResponse res)throws Exception;
    
    public     void    generatePdfReportAllData(HttpServletResponse  res)throws Exception;
    public     void    generateExcelReportAllData(HttpServletResponse  res)throws Exception;
    
}
