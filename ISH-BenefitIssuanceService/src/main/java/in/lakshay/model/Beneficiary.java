package in.lakshay.model;

import lombok.Data;

@Data
public class Beneficiary {
    private Long caseNo;
    private String holderName;
    private Long holderSsn;
    private String planName;
    private Double benefitAmount;
    private String bankName;
    private Long accountNumber;
}
