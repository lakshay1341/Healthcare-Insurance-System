package in.lakshay.rest;

import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.service.IBenifitIssuanceMgmtService;

@RestController
@RequestMapping("/bi-api")
public class BIOperationsRestController {
	@Autowired
	private  IBenifitIssuanceMgmtService  biService;

	@GetMapping("/send")
	public ResponseEntity<String> sendAmount() {
		try {
			//use service
			JobExecution execution = biService.sendAmountToBenificries();
			return new ResponseEntity<>(execution.getExitStatus().getExitDescription(), HttpStatus.OK);
		} catch (Exception ex) {
			throw new in.lakshay.exceptions.ApplicationException("Error sending benefits: " + ex.getMessage(), ex);
		}
	}

}