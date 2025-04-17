package in.lakshay.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for eligibility statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityStatistics {

    private Integer totalApplications;
    private Integer approvedApplications;
    private Integer deniedApplications;
    private Integer pendingApplications;
    private Double approvalRate;
    private Double denialRate;
    private Double averageProcessingTimeInDays;

    // Breakdowns
    private Map<String, Integer> breakdownByState;
    private Map<String, Integer> breakdownByPlan;
    private Map<String, Integer> breakdownByAgeGroup;
    private Map<String, Integer> breakdownByIncomeLevel;

    // Reasons for denial
    private Map<String, Integer> denialReasons;

    // Getters and setters
    public Integer getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Integer totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Integer getApprovedApplications() {
        return approvedApplications;
    }

    public void setApprovedApplications(Integer approvedApplications) {
        this.approvedApplications = approvedApplications;
    }

    public Integer getDeniedApplications() {
        return deniedApplications;
    }

    public void setDeniedApplications(Integer deniedApplications) {
        this.deniedApplications = deniedApplications;
    }

    public Integer getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(Integer pendingApplications) {
        this.pendingApplications = pendingApplications;
    }

    public Double getApprovalRate() {
        return approvalRate;
    }

    public void setApprovalRate(Double approvalRate) {
        this.approvalRate = approvalRate;
    }

    public Double getDenialRate() {
        return denialRate;
    }

    public void setDenialRate(Double denialRate) {
        this.denialRate = denialRate;
    }

    public Double getAverageProcessingTimeInDays() {
        return averageProcessingTimeInDays;
    }

    public void setAverageProcessingTimeInDays(Double averageProcessingTimeInDays) {
        this.averageProcessingTimeInDays = averageProcessingTimeInDays;
    }

    public Map<String, Integer> getBreakdownByState() {
        return breakdownByState;
    }

    public void setBreakdownByState(Map<String, Integer> breakdownByState) {
        this.breakdownByState = breakdownByState;
    }

    public Map<String, Integer> getBreakdownByPlan() {
        return breakdownByPlan;
    }

    public void setBreakdownByPlan(Map<String, Integer> breakdownByPlan) {
        this.breakdownByPlan = breakdownByPlan;
    }

    public Map<String, Integer> getBreakdownByAgeGroup() {
        return breakdownByAgeGroup;
    }

    public void setBreakdownByAgeGroup(Map<String, Integer> breakdownByAgeGroup) {
        this.breakdownByAgeGroup = breakdownByAgeGroup;
    }

    public Map<String, Integer> getBreakdownByIncomeLevel() {
        return breakdownByIncomeLevel;
    }

    public void setBreakdownByIncomeLevel(Map<String, Integer> breakdownByIncomeLevel) {
        this.breakdownByIncomeLevel = breakdownByIncomeLevel;
    }

    public Map<String, Integer> getDenialReasons() {
        return denialReasons;
    }

    public void setDenialReasons(Map<String, Integer> denialReasons) {
        this.denialReasons = denialReasons;
    }
}
