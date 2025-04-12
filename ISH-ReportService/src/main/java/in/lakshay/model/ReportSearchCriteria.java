package in.lakshay.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for report search criteria
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportSearchCriteria {
    
    private String reportType;
    private String reportFormat;
    private String reportStatus;
    private String benefitType;
    private Integer caseNumber;
    private Integer applicationId;
    private LocalDate startDate;
    private LocalDate endDate;
}
