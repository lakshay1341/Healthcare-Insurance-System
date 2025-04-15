package in.lakshay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple application for the ISH Report Service
 */
@SpringBootApplication
public class ISHReportServiceSimple {

    public static void main(String[] args) {
        SpringApplication.run(ISHReportServiceSimple.class, args);
    }

    /**
     * Controller for report operations in the Insurance System for Health
     */
    @RestController
    @RequestMapping("/report-api")
    public static class ReportController {
        
        /**
         * Get all report types
         */
        @GetMapping("/report-types")
        public ResponseEntity<List<String>> getAllReportTypes() {
            List<String> reportTypes = new ArrayList<>();
            reportTypes.add("Eligibility");
            reportTypes.add("Benefit");
            reportTypes.add("Application");
            reportTypes.add("Data Collection");
            return new ResponseEntity<>(reportTypes, HttpStatus.OK);
        }
        
        /**
         * Get all report formats
         */
        @GetMapping("/report-formats")
        public ResponseEntity<List<String>> getAllReportFormats() {
            List<String> formats = new ArrayList<>();
            formats.add("PDF");
            formats.add("Excel");
            formats.add("CSV");
            formats.add("JSON");
            return new ResponseEntity<>(formats, HttpStatus.OK);
        }
        
        /**
         * Get all plan names
         */
        @GetMapping("/plan-names")
        public ResponseEntity<List<String>> getAllPlanNames() {
            List<String> planNames = new ArrayList<>();
            planNames.add("SNAP");
            planNames.add("CCAP");
            planNames.add("Medicaid");
            planNames.add("Medicare");
            planNames.add("QHP");
            return new ResponseEntity<>(planNames, HttpStatus.OK);
        }
        
        /**
         * Get all states
         */
        @GetMapping("/states")
        public ResponseEntity<List<String>> getAllStates() {
            List<String> states = new ArrayList<>();
            states.add("California");
            states.add("Texas");
            states.add("Florida");
            states.add("New York");
            states.add("Washington DC");
            states.add("Ohio");
            return new ResponseEntity<>(states, HttpStatus.OK);
        }
        
        /**
         * Get eligibility summary
         */
        @GetMapping("/eligibility/summary")
        public ResponseEntity<Map<String, Object>> getEligibilitySummary() {
            Map<String, Object> summary = new HashMap<>();
            summary.put("totalApplications", 1000);
            summary.put("approved", 750);
            summary.put("denied", 250);
            summary.put("pendingReview", 50);
            
            Map<String, Integer> planBreakdown = new HashMap<>();
            planBreakdown.put("SNAP", 300);
            planBreakdown.put("CCAP", 200);
            planBreakdown.put("Medicaid", 150);
            planBreakdown.put("Medicare", 50);
            planBreakdown.put("QHP", 50);
            
            summary.put("planBreakdown", planBreakdown);
            
            return new ResponseEntity<>(summary, HttpStatus.OK);
        }
        
        /**
         * Get benefit issuance summary
         */
        @GetMapping("/benefit/summary")
        public ResponseEntity<Map<String, Object>> getBenefitSummary() {
            Map<String, Object> summary = new HashMap<>();
            summary.put("totalBenefitsIssued", 750);
            summary.put("totalAmount", 1500000.00);
            
            Map<String, Double> planBreakdown = new HashMap<>();
            planBreakdown.put("SNAP", 600000.00);
            planBreakdown.put("CCAP", 400000.00);
            planBreakdown.put("Medicaid", 300000.00);
            planBreakdown.put("Medicare", 100000.00);
            planBreakdown.put("QHP", 100000.00);
            
            summary.put("planBreakdown", planBreakdown);
            
            return new ResponseEntity<>(summary, HttpStatus.OK);
        }
        
        /**
         * Get application summary
         */
        @GetMapping("/application/summary")
        public ResponseEntity<Map<String, Object>> getApplicationSummary() {
            Map<String, Object> summary = new HashMap<>();
            summary.put("totalApplications", 1000);
            summary.put("newApplications", 100);
            summary.put("inProgressApplications", 200);
            summary.put("completedApplications", 700);
            
            Map<String, Integer> stateBreakdown = new HashMap<>();
            stateBreakdown.put("California", 500);
            stateBreakdown.put("Texas", 200);
            stateBreakdown.put("Florida", 100);
            stateBreakdown.put("New York", 100);
            stateBreakdown.put("Washington DC", 50);
            stateBreakdown.put("Ohio", 50);
            
            summary.put("stateBreakdown", stateBreakdown);
            
            return new ResponseEntity<>(summary, HttpStatus.OK);
        }
        
        /**
         * Get data collection summary
         */
        @GetMapping("/data-collection/summary")
        public ResponseEntity<Map<String, Object>> getDataCollectionSummary() {
            Map<String, Object> summary = new HashMap<>();
            summary.put("totalCases", 1000);
            summary.put("incomeVerified", 800);
            summary.put("educationVerified", 750);
            summary.put("childrenVerified", 600);
            
            Map<String, Double> incomeBreakdown = new HashMap<>();
            incomeBreakdown.put("0-10000", 200.0);
            incomeBreakdown.put("10001-20000", 300.0);
            incomeBreakdown.put("20001-30000", 250.0);
            incomeBreakdown.put("30001-40000", 150.0);
            incomeBreakdown.put("40001+", 100.0);
            
            summary.put("incomeBreakdown", incomeBreakdown);
            
            return new ResponseEntity<>(summary, HttpStatus.OK);
        }
    }
}
