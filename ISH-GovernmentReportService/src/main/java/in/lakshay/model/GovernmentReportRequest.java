package in.lakshay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for generating government reports
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GovernmentReportRequest {

    private String reportType;
    private String reportFormat;
    private String periodCovered;
    private String departmentName;
    private String generatedFor;
    private boolean includeCharts;
    private boolean includeStatistics;
    private boolean includeBreakdownByState;
    private boolean includeBreakdownByPlan;
    private boolean includeBreakdownByAge;
    private boolean includeBreakdownByIncome;

    // Getters and setters
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public String getPeriodCovered() {
        return periodCovered;
    }

    public void setPeriodCovered(String periodCovered) {
        this.periodCovered = periodCovered;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGeneratedFor() {
        return generatedFor;
    }

    public void setGeneratedFor(String generatedFor) {
        this.generatedFor = generatedFor;
    }

    public boolean isIncludeCharts() {
        return includeCharts;
    }

    public void setIncludeCharts(boolean includeCharts) {
        this.includeCharts = includeCharts;
    }

    public boolean isIncludeStatistics() {
        return includeStatistics;
    }

    public void setIncludeStatistics(boolean includeStatistics) {
        this.includeStatistics = includeStatistics;
    }

    public boolean isIncludeBreakdownByState() {
        return includeBreakdownByState;
    }

    public void setIncludeBreakdownByState(boolean includeBreakdownByState) {
        this.includeBreakdownByState = includeBreakdownByState;
    }

    public boolean isIncludeBreakdownByPlan() {
        return includeBreakdownByPlan;
    }

    public void setIncludeBreakdownByPlan(boolean includeBreakdownByPlan) {
        this.includeBreakdownByPlan = includeBreakdownByPlan;
    }

    public boolean isIncludeBreakdownByAge() {
        return includeBreakdownByAge;
    }

    public void setIncludeBreakdownByAge(boolean includeBreakdownByAge) {
        this.includeBreakdownByAge = includeBreakdownByAge;
    }

    public boolean isIncludeBreakdownByIncome() {
        return includeBreakdownByIncome;
    }

    public void setIncludeBreakdownByIncome(boolean includeBreakdownByIncome) {
        this.includeBreakdownByIncome = includeBreakdownByIncome;
    }
}
