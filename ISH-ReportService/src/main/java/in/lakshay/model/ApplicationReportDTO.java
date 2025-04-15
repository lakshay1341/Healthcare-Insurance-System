package in.lakshay.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for application reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationReportDTO {
    
    private Integer reportId;
    private String reportName;
    private String reportFormat;
    private String reportStatus;
    private String reportDescription;
    private String reportFilePath;
    private Integer appId;
    private String fullName;
    private String email;
    private String gender;
    private Long phoneNo;
    private Long ssn;
    private String stateName;
    private LocalDate dob;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String updatedBy;
}
