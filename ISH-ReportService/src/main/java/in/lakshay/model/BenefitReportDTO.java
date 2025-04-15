package in.lakshay.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for benefit reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitReportDTO {
    
    private Integer reportId;
    private String reportName;
    private String reportFormat;
    private String reportStatus;
    private String reportDescription;
    private String reportFilePath;
    private Long caseNo;
    private String holderName;
    private Long holderSSN;
    private String planName;
    private Double benefitAmount;
    private String bankName;
    private Long accountNumber;
    private String issuanceStatus;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
