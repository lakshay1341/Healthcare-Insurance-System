package in.lakshay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for report generation requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportGenerationRequest {
    
    private String reportType;
    private String reportFormat;
    private Integer caseNumber;
    private Integer applicationId;
    private String benefitType;
    private String reportName;
    private String reportDescription;
}
