package in.lakshay.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssa-web-api")
public class SSAWebOperationsRestController {
	
	@GetMapping("/find/{ssn}")
	public ResponseEntity<String> getStateBySSN(@PathVariable Integer ssn){
		if(String.valueOf(ssn).length()!=9) {
			return new ResponseEntity<String>("invalid ssn",HttpStatus.BAD_REQUEST);
		}
		
		//get state name
		int stateCode=ssn%100;
		String stateName=null;
		if(stateCode==1) {
			stateName="Washington DC";
		}else if(stateCode==2) {
			stateName="Ohio";
		}else if(stateCode==3) {
			stateName="Texas";
		}else if(stateCode==4) {
			stateName="California";
		}else if(stateCode==4) {
			stateName="Florida";
		}else {
			stateName="invalid snn";
		}
		
		return new ResponseEntity<String>(stateName,HttpStatus.OK);
	}

}
