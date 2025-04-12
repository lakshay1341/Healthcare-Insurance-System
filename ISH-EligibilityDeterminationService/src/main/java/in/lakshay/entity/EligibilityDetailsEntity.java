package in.lakshay.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name="ELIGIBILITY_DETERMINATION")
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
