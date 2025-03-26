package in.lakshay.bindings;

import java.util.List;

import lombok.Data;

@Data
public class DcSummaryReport {
	private PlanSelectionInputs planDetails;
	private EducationInputs educationDetails;
	private List<ChildInputs> childrenDetails;
	private IncomeInputs incomeDetails;
	private CitizenAppRegistrationInputs citizenDetails;
	private String planName;

}
