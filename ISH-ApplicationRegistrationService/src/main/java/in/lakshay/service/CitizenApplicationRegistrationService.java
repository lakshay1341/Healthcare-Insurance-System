package in.lakshay.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import in.lakshay.bindings.CitizenAppRegistrationInputs;
import in.lakshay.entity.CitizenAppRegistrationEntity;
import in.lakshay.exceptions.InvalidSSNException;
import in.lakshay.repository.CitizenRepository;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class CitizenApplicationRegistrationService implements ICitizenApplicationRegistrationService{

	@Autowired
	private CitizenRepository citizenrepo;

	// Using WebClient instead of RestTemplate for reactive programming

	@Value("${ar.ssa-web.url}")
	private String endpointUrl;
	@Value("${ar.state}")
	private String targetState;

	@Autowired
	private WebClient client;

	@Override
	public Integer registerCitizenApplication(CitizenAppRegistrationInputs inputs) throws InvalidSSNException {
		// Perform webservice call to check if SSN is valid and to get the state name
		try {
			// Use WebClient to make a non-blocking request to the SSA web API
			String stateName = client.get()
				.uri(endpointUrl, inputs.getSsn())
				.retrieve()
				.onStatus(HttpStatus.BAD_REQUEST::equals,
					res -> res.bodyToMono(String.class)
						.map(ex -> new InvalidSSNException("Invalid SSN: " + ex)))
				.bodyToMono(String.class)
				.block(); // Using block() as the service interface is not reactive yet

			// register citizen if they belong to the target state
			if(stateName.equalsIgnoreCase(targetState)) {
				// prepare the entity object
				CitizenAppRegistrationEntity entity = new CitizenAppRegistrationEntity();
				entity.setStateName(stateName); // Set the state name from the API response
				BeanUtils.copyProperties(inputs, entity);
				// save the object
				int appId = citizenrepo.save(entity).getAppId();
				return appId;
			}
			throw new InvalidSSNException("Citizen does not belong to " + targetState + " state");
		} catch (InvalidSSNException e) {
			throw e;
		} catch (Exception e) {
			throw new InvalidSSNException("Error validating SSN: " + e.getMessage());
		}
	}

}
