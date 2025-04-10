package in.lakshay.binding;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
public class ElgibilityDetails {
	private  Integer  caseNo;
	private   String holderName;
	private    Long   holderSSN;
	private    String  planName;
	private    String  planStatus;
	private   Double  benifitAmt;
	private   String  bankName;
	private   Long  accountNumber;
	

}
