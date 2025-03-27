package in.lakshay.bindings;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EligibilityDetailsOutput {
    private String holderName;
    private String planName;
    private String planStatus;
    private LocalDate planStartDate;
    private LocalDate planEndDate;
    private Double benifitAmt;
    private String denialReason;

}
