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
 * Entity representing a report in the system
 */
@Entity
@Table(name = "ISH_REPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;
    
    @Column(length = 100, nullable = false)
    private String reportName;
    
    @Column(length = 50, nullable = false)
    private String reportType;
    
    @Column(length = 50, nullable = false)
    private String reportFormat;
    
    @Column(length = 50, nullable = false)
    private String reportStatus;
    
    @Column(length = 255)
    private String reportDescription;
    
    @Column(length = 255)
    private String reportFilePath;
    
    @Column
    private Integer caseNumber;
    
    @Column
    private Integer applicationId;
    
    @Column(length = 50)
    private String benefitType;
    
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
