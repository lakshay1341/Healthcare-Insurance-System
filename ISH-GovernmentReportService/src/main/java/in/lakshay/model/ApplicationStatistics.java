package in.lakshay.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for application statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatistics {

    private Integer totalApplications;
    private Integer newApplications;
    private Integer inProgressApplications;
    private Integer completedApplications;
    private Double averageCompletionTimeInDays;

    // Breakdowns
    private Map<String, Integer> applicationsByState;
    private Map<String, Integer> applicationsByPlan;
    private Map<String, Integer> applicationsByAgeGroup;
    private Map<String, Integer> applicationsByIncomeLevel;

    // Monthly trends
    private Map<String, Integer> monthlyApplicationTrends;

    // Application methods
    private Map<String, Integer> applicationMethods; // Online, Paper, Phone, etc.

    // Getters and setters
    public Integer getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(Integer totalApplications) {
        this.totalApplications = totalApplications;
    }

    public Integer getNewApplications() {
        return newApplications;
    }

    public void setNewApplications(Integer newApplications) {
        this.newApplications = newApplications;
    }

    public Integer getInProgressApplications() {
        return inProgressApplications;
    }

    public void setInProgressApplications(Integer inProgressApplications) {
        this.inProgressApplications = inProgressApplications;
    }

    public Integer getCompletedApplications() {
        return completedApplications;
    }

    public void setCompletedApplications(Integer completedApplications) {
        this.completedApplications = completedApplications;
    }

    public Double getAverageCompletionTimeInDays() {
        return averageCompletionTimeInDays;
    }

    public void setAverageCompletionTimeInDays(Double averageCompletionTimeInDays) {
        this.averageCompletionTimeInDays = averageCompletionTimeInDays;
    }

    public Map<String, Integer> getApplicationsByState() {
        return applicationsByState;
    }

    public void setApplicationsByState(Map<String, Integer> applicationsByState) {
        this.applicationsByState = applicationsByState;
    }

    public Map<String, Integer> getApplicationsByPlan() {
        return applicationsByPlan;
    }

    public void setApplicationsByPlan(Map<String, Integer> applicationsByPlan) {
        this.applicationsByPlan = applicationsByPlan;
    }

    public Map<String, Integer> getApplicationsByAgeGroup() {
        return applicationsByAgeGroup;
    }

    public void setApplicationsByAgeGroup(Map<String, Integer> applicationsByAgeGroup) {
        this.applicationsByAgeGroup = applicationsByAgeGroup;
    }

    public Map<String, Integer> getApplicationsByIncomeLevel() {
        return applicationsByIncomeLevel;
    }

    public void setApplicationsByIncomeLevel(Map<String, Integer> applicationsByIncomeLevel) {
        this.applicationsByIncomeLevel = applicationsByIncomeLevel;
    }

    public Map<String, Integer> getMonthlyApplicationTrends() {
        return monthlyApplicationTrends;
    }

    public void setMonthlyApplicationTrends(Map<String, Integer> monthlyApplicationTrends) {
        this.monthlyApplicationTrends = monthlyApplicationTrends;
    }

    public Map<String, Integer> getApplicationMethods() {
        return applicationMethods;
    }

    public void setApplicationMethods(Map<String, Integer> applicationMethods) {
        this.applicationMethods = applicationMethods;
    }
}
