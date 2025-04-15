package in.lakshay.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for eligibility reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityReportDTO {
    
    private Integer reportId;
    private String reportName;
    private String reportFormat;
    private String reportStatus;
    private String reportDescription;
    private String reportFilePath;
    private Long caseNo;
    private Integer appId;
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private Double benefitAmt;
    private String denialReason;
    private String holderName;
    private Long holderSSN;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
