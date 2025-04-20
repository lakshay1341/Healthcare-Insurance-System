package in.lakshay.bindings;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CitizenAppRegistrationInputs {

	private String fullName;
	private String email;
	private String gender;
	private Long phoneNo;
	private Long ssn;
	private LocalDate dob;
	private String stateName;
	private String remark;
	private String createdBy;
	private String updatedBy;
}
