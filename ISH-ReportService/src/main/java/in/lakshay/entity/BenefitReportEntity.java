package in.lakshay.entity;

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
 * Entity representing a benefit issuance report
 */
@Entity
@Table(name = "ISH_BENEFIT_REPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitReportEntity {
    
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
    
    @Column(length = 100)
    private String holderName;
    
    private Long holderSSN;
    
    @Column(length = 50)
    private String planName;
    
    private Double benefitAmount;
    
    @Column(length = 100)
    private String bankName;
    
    private Long accountNumber;
    
    @Column(length = 50)
    private String issuanceStatus;
    
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
