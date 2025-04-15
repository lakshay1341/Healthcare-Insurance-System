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
 * Entity representing a data collection report
 */
@Entity
@Table(name = "ISH_DATA_COLLECTION_REPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataCollectionReportEntity {
    
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
    
    private Integer planId;
    
    @Column(length = 50)
    private String planName;
    
    private Double empIncome;
    
    private Double propertyIncome;
    
    @Column(length = 100)
    private String highestQlfy;
    
    private Integer passOutYear;
    
    private Integer childCount;
    
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
