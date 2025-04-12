package in.lakshay.ms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.exceptions.ResourceNotFoundException;
import in.lakshay.exceptions.ApplicationException;

import in.lakshay.bindings.ChildInputs;
import in.lakshay.bindings.DcSummaryReport;
import in.lakshay.bindings.EducationInputs;
import in.lakshay.bindings.IncomeInputs;
import in.lakshay.bindings.PlanSelectionInputs;
import in.lakshay.service.IDcMgmtService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/dc-api")
@Tag(name="dc-api",description = "data collection module microservice")
public class DataCollectionOperationController {
	@Autowired
	private IDcMgmtService dcservice;

	@GetMapping("/planNames")
	public ResponseEntity<?> displayPlanNames(){
		try {
			// use service
			List<String> listPlanNames = dcservice.showAllPlanNames();
			return ResponseEntity.ok(listPlanNames);
		} catch (Exception ex) {
			throw new ApplicationException("Error retrieving plan names: " + ex.getMessage(), ex);
		}
	}

	@PostMapping("/loadCaseNo/{appId}")
	public ResponseEntity<?> generateCaseNo(@PathVariable Integer appId){
		try {
			// use service
			Long caseNo = dcservice.generateCaseNo(appId);
			if (caseNo == 0L) {
				throw new ResourceNotFoundException("Application", "appId", appId);
			}
			return ResponseEntity.ok(caseNo);
		} catch (ResourceNotFoundException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ApplicationException("Error generating case number: " + ex.getMessage(), ex);
		}
	}

	@PutMapping("/updatePlanSelection")
	public ResponseEntity<?> savePlanSelection(@RequestBody PlanSelectionInputs inputs){
		//use service
		Long caseNo=dcservice.savePlanSelection(inputs);
		return ResponseEntity.ok(caseNo);
	}

	@PostMapping("/saveIncome")
	public ResponseEntity<?> saveIncomeDetails(@RequestBody IncomeInputs income){
		// use service
		Long caseNo=dcservice.saveIncomeDetails(income);
		return ResponseEntity.ok(caseNo);
	}

	@PostMapping("/saveEducation")
	public ResponseEntity<?> saveEducationDetails(@RequestBody EducationInputs education){
		Long caseNo=dcservice.saveEducationDetails(education);
		return ResponseEntity.ok(caseNo);
	}

	@PostMapping("/saveChilds")
	public ResponseEntity<?> saveChildrenDetails(@RequestBody List<ChildInputs> childs){
		Long caseNo=dcservice.saveChildrenDetails(childs);
		return ResponseEntity.ok(caseNo);
	}

	@GetMapping("/summary/{caseNo}")
	public ResponseEntity<?> showSummaryReports(@PathVariable Long caseNo){
		// use service
		DcSummaryReport report=dcservice.showDcSummary(caseNo);
		return ResponseEntity.ok(report);
	}


}
