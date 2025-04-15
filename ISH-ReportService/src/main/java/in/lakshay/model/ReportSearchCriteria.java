package in.lakshay.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Criteria for searching reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportSearchCriteria {

    private String reportType;
    private String reportFormat;
    private String planName;
    private String planStatus;
    private String stateName;
    private Long caseNo;
    private Integer appId;
    private Long ssn;
    private LocalDate fromDate;
    private LocalDate toDate;
}
