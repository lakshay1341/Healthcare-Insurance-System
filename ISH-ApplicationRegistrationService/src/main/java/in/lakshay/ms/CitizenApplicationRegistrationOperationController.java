package in.lakshay.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.bindings.CitizenAppRegistrationInputs;
import in.lakshay.service.ICitizenApplicationRegistrationService;

@RestController
@RequestMapping("/CitizenAR-api")
public class CitizenApplicationRegistrationOperationController {

	@Autowired
	private ICitizenApplicationRegistrationService registrationService;

	@PostMapping("/save")
	public ResponseEntity<String> saveCitizenApplication(@RequestBody CitizenAppRegistrationInputs inputs) throws Exception{
			int appId=registrationService.registerCitizenApplication(inputs);
			return new ResponseEntity<String>("citizen application is registered with id::"+appId,HttpStatus.CREATED);
	}//method

}//class
