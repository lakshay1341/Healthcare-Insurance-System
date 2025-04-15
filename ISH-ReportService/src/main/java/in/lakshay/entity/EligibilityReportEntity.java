package in.lakshay.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing an eligibility report
 */
@Entity
@Table(name = "ISH_ELIGIBILITY_REPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityReportEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;
    
    @Column(length = 100, nullable = false)
    private String reportName;
    
    @Column(length = 50)
    private String reportFormat;
    
    @Column(length = 50)
    private String reportStatus;
    
    @Column(length = 255)
    private String reportDescription;
    
    @Column(length = 255)
    private String reportFilePath;
    
    private Long caseNo;
    
    private Integer appId;
    
    @Column(length = 50)
    private String planName;
    
    @Column(length = 50)
    private String planStatus;
    
    private LocalDate planStartDate;
    
    private LocalDate planEndDate;
    
    private Double benefitAmt;
    
    @Column(length = 100)
    private String denialReason;
    
    @Column(length = 100)
    private String holderName;
    
    private Long holderSSN;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;
    
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    
    @Column(length = 50)
    private String createdBy;
    
    @Column(length = 50)
    private String updatedBy;
}
