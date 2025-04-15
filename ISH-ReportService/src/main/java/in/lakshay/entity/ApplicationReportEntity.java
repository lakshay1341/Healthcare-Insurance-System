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
 * Entity representing an application report
 */
@Entity
@Table(name = "ISH_APPLICATION_REPORTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationReportEntity {
    
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
    
    private Integer appId;
    
    @Column(length = 100)
    private String fullName;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 1)
    private String gender;
    
    private Long phoneNo;
    
    private Long ssn;
    
    @Column(length = 50)
    private String stateName;
    
    private LocalDate dob;
    
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
