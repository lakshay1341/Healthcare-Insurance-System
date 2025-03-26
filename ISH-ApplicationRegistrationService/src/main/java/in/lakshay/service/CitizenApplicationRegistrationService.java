package in.lakshay.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import in.lakshay.bindings.CitizenAppRegistrationInputs;
import in.lakshay.entity.CitizenAppRegistrationEntity;
import in.lakshay.exceptions.InvalidSSNException;
import in.lakshay.repository.CitizenRepository;
import reactor.core.publisher.Mono;

public class CitizenApplicationRegistrationService implements ICitizenApplicationRegistrationService{
	
	@Autowired
	private CitizenRepository citizenrepo;
	
	@Autowired
	private RestTemplate template;
	
	@Value("${ar.ssa-web.url}")
	private String endpointUrl;
	@Value("$ar.state")
	private String targetState;
	
	@Autowired
	private WebClient client;

	@Override
	public Integer registerCitizenApplication(CitizenAppRegistrationInputs inputs) throws InvalidSSNException {
		// TODO Auto-generated method stub
			
		// perform webservice call to check weather SSN is valid or not and to get the state name
		//ResponseEntity<String> response=template.exchange(endpointUrl, HttpMethod.GET,null,String.class,inputs.getSsn());
		// get state name
		//String stateName=response.getBody();
		//String stateName=client.get().uri(endpointUrl,inputs.getSsn()).retrieve().bodyToMono(String.class).block();
		
		Mono<String> response=client.get().uri(endpointUrl,inputs.getSsn()).retrieve()
				.onStatus(HttpStatus.BAD_REQUEST::equals, res->res.bodyToMono(String.class)
						.map(ex-> new InvalidSSNException("Invalid ssn")))
				.bodyToMono(String.class);
		String stateName=response.block();
		// register citizen if he belongs to California state(CA)
		if(stateName.equalsIgnoreCase(targetState)) {
			// prepare the entity object
			CitizenAppRegistrationEntity entity=new CitizenAppRegistrationEntity();
			BeanUtils.copyProperties(inputs,entity);
			// save the object 
			int appId=citizenrepo.save(entity).getAppId();
			return appId;
		}
		throw new InvalidSSNException("invalid ssn");
	}

}
