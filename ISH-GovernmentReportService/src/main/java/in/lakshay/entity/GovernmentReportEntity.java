package in.lakshay.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a government report
 */
@Entity
@Table(name = "ISH_GOVERNMENT_REPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GovernmentReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @Column(length = 100, nullable = false)
    private String reportName;

    @Column(length = 50)
    private String reportType;

    @Column(length = 50)
    private String reportFormat;

    @Column(length = 50)
    private String reportStatus;

    @Column(length = 255)
    private String reportDescription;

    @Column(length = 255)
    private String reportFilePath;

    @Column(length = 100)
    private String generatedFor;

    @Column(length = 100)
    private String departmentName;

    @Column(length = 100)
    private String periodCovered;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] reportContent;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

    @Column(length = 50)
    private String createdBy;

    @Column(length = 50)
    private String updatedBy;

    // Getters and setters
    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

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

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public String getReportFilePath() {
        return reportFilePath;
    }

    public void setReportFilePath(String reportFilePath) {
        this.reportFilePath = reportFilePath;
    }

    public String getGeneratedFor() {
        return generatedFor;
    }

    public void setGeneratedFor(String generatedFor) {
        this.generatedFor = generatedFor;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPeriodCovered() {
        return periodCovered;
    }

    public void setPeriodCovered(String periodCovered) {
        this.periodCovered = periodCovered;
    }

    public byte[] getReportContent() {
        return reportContent;
    }

    public void setReportContent(byte[] reportContent) {
        this.reportContent = reportContent;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
