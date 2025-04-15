package in.lakshay.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for data collection reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataCollectionReportDTO {
    
    private Integer reportId;
    private String reportName;
    private String reportFormat;
    private String reportStatus;
    private String reportDescription;
    private String reportFilePath;
    private Long caseNo;
    private Integer appId;
    private Integer planId;
    private String planName;
    private Double empIncome;
    private Double propertyIncome;
    private String highestQlfy;
    private Integer passOutYear;
    private Integer childCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
