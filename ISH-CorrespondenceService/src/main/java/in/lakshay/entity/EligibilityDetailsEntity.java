package in.lakshay.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "ELIGIBILITY_DETERMINATION")
@Data
@Entity
public class EligibilityDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer edTraceId;
    private Long caseNo;
    @Column(length = 30)
    private String holderName;
    private Long holderSSN;
    @Column(length = 30)
    private String planName;
    @Column(length = 30)
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private Double benifitAmt;
    @Column(length = 30)
    private String denialReason;
}
