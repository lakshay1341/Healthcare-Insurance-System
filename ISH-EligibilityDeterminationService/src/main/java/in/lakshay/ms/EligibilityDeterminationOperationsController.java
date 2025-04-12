package in.lakshay.ms;

import in.lakshay.bindings.EligibilityDetailsOutput;
import in.lakshay.service.IEligibilityDeterminationMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ed-api")
public class EligibilityDeterminationOperationsController {

    @Autowired
    private IEligibilityDeterminationMgmtService edservice;

    @GetMapping("/determine/{caseNo}")
    public ResponseEntity<?> checkEligibility(@PathVariable Long caseNo){
        // use service
        EligibilityDetailsOutput output=edservice.determineEligibility(caseNo);
        return new ResponseEntity<EligibilityDetailsOutput>(output, HttpStatus.CREATED);

    }
}
