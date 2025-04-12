package in.lakshay.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    
    private Integer reportId;
    private String reportName;
    private String reportType;
    private String reportFormat;
    private String reportStatus;
    private String reportDescription;
    private String reportFilePath;
    private Integer caseNumber;
    private Integer applicationId;
    private String benefitType;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
