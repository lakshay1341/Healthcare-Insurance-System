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
		// use service
		List<String> listPlanNames=dcservice.showAllPlanNames();
		return ResponseEntity.ok(listPlanNames);
	}
	
	@PostMapping("/loadCaseNo/{appId}")
	public ResponseEntity<?> generateCaseNo(@PathVariable Integer appId){
		// use service 
		Integer caseNo=dcservice.generateCaseNo(appId);
		return ResponseEntity.ok(caseNo);
	}
	
	@PutMapping("/updatePlanSelection")
	public ResponseEntity<?> savePlanSelection(@RequestBody PlanSelectionInputs inputs){
		//use service
		Integer caseNo=dcservice.savePlanSelection(inputs);
		return ResponseEntity.ok(caseNo);
	}
	
	@PostMapping("/saveIncome")
	public ResponseEntity<?> saveIncomeDetails(@RequestBody IncomeInputs income){
		// use service
		Integer caseNo=dcservice.saveIncomeDetails(income);
		return ResponseEntity.ok(caseNo);
	}
	
	@PostMapping("/saveEducation")
	public ResponseEntity<?> saveEducationDetails(@RequestBody EducationInputs education){
		Integer caseNo=dcservice.saveEducationDetails(education);
		return ResponseEntity.ok(caseNo);
	}
	
	@PostMapping("/saveChilds")
	public ResponseEntity<?> saveChildrenDetails(@RequestBody List<ChildInputs> childs){
		Integer caseNo=dcservice.saveChildrenDetails(childs);
		return ResponseEntity.ok(caseNo);
	}
	
	@GetMapping("/summary/{caseNo}")
	public ResponseEntity<?> showSummaryReports(@PathVariable Integer caseNo){
		// use service
		DcSummaryReport report=dcservice.showDcSummary(caseNo);
		return ResponseEntity.ok(report);
	}
	

}
