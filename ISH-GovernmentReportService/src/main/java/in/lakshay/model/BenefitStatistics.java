package in.lakshay.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for benefit statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitStatistics {

    private Integer totalBenefitsIssued;
    private Double totalAmountIssued;
    private Double averageBenefitAmount;
    private Integer activeRecipients;

    // Breakdowns
    private Map<String, Double> amountByState;
    private Map<String, Double> amountByPlan;
    private Map<String, Double> amountByAgeGroup;
    private Map<String, Double> amountByIncomeLevel;

    // Monthly trends
    private Map<String, Double> monthlyIssuanceTrends;

    // Getters and setters
    public Integer getTotalBenefitsIssued() {
        return totalBenefitsIssued;
    }

    public void setTotalBenefitsIssued(Integer totalBenefitsIssued) {
        this.totalBenefitsIssued = totalBenefitsIssued;
    }

    public Double getTotalAmountIssued() {
        return totalAmountIssued;
    }

    public void setTotalAmountIssued(Double totalAmountIssued) {
        this.totalAmountIssued = totalAmountIssued;
    }

    public Double getAverageBenefitAmount() {
        return averageBenefitAmount;
    }

    public void setAverageBenefitAmount(Double averageBenefitAmount) {
        this.averageBenefitAmount = averageBenefitAmount;
    }

    public Integer getActiveRecipients() {
        return activeRecipients;
    }

    public void setActiveRecipients(Integer activeRecipients) {
        this.activeRecipients = activeRecipients;
    }

    public Map<String, Double> getAmountByState() {
        return amountByState;
    }

    public void setAmountByState(Map<String, Double> amountByState) {
        this.amountByState = amountByState;
    }

    public Map<String, Double> getAmountByPlan() {
        return amountByPlan;
    }

    public void setAmountByPlan(Map<String, Double> amountByPlan) {
        this.amountByPlan = amountByPlan;
    }

    public Map<String, Double> getAmountByAgeGroup() {
        return amountByAgeGroup;
    }

    public void setAmountByAgeGroup(Map<String, Double> amountByAgeGroup) {
        this.amountByAgeGroup = amountByAgeGroup;
    }

    public Map<String, Double> getAmountByIncomeLevel() {
        return amountByIncomeLevel;
    }

    public void setAmountByIncomeLevel(Map<String, Double> amountByIncomeLevel) {
        this.amountByIncomeLevel = amountByIncomeLevel;
    }

    public Map<String, Double> getMonthlyIssuanceTrends() {
        return monthlyIssuanceTrends;
    }

    public void setMonthlyIssuanceTrends(Map<String, Double> monthlyIssuanceTrends) {
        this.monthlyIssuanceTrends = monthlyIssuanceTrends;
    }
}
