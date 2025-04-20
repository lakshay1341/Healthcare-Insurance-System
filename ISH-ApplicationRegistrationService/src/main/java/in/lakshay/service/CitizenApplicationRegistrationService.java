package in.lakshay.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;

import in.lakshay.bindings.CitizenAppRegistrationInputs;
import in.lakshay.entity.CitizenAppRegistrationEntity;
import in.lakshay.exceptions.InvalidSSNException;
import in.lakshay.repository.CitizenRepository;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

@Service
public class CitizenApplicationRegistrationService implements ICitizenApplicationRegistrationService {

	private static final Logger logger = LoggerFactory.getLogger(CitizenApplicationRegistrationService.class);

	@Autowired
	private CitizenRepository citizenrepo;

	@Value("${ar.ssa-web.url}")
	private String endpointUrl;
	@Value("${ar.state}")
	private String targetState;

	@Autowired
	@Qualifier("webclient")
	private WebClient client;

	@Override
	@CircuitBreaker(name = "ssaWebService", fallbackMethod = "registerCitizenApplicationFallback")
	@Retry(name = "ssaWebService", fallbackMethod = "registerCitizenApplicationFallback")
	@Bulkhead(name = "ssaWebService", fallbackMethod = "registerCitizenApplicationFallback")
	public Integer registerCitizenApplication(CitizenAppRegistrationInputs inputs) throws InvalidSSNException {
		logger.info("Processing application registration for SSN: {}", inputs.getSsn());

		// Perform webservice call to check if SSN is valid and to get the state name
		try {
			logger.debug("Making request to SSA Web API for SSN validation");

			// Use WebClient to make a non-blocking request to the SSA web API
			String stateName = client.get()
				.uri(endpointUrl, inputs.getSsn())
				.retrieve()
				.onStatus(HttpStatus.BAD_REQUEST::equals,
					res -> res.bodyToMono(String.class)
						.map(ex -> {
							logger.error("SSA Web API returned bad request: {}", ex);
							return new InvalidSSNException("Invalid SSN: " + ex);
						}))
				.bodyToMono(String.class)
				.block(); // Using block() as the service interface is not reactive yet

			logger.debug("SSA Web API returned state: {} for SSN: {}", stateName, inputs.getSsn());

			// register citizen if they belong to the target state
			if(stateName.equalsIgnoreCase(targetState)) {
				logger.info("Citizen belongs to target state: {}", targetState);

				// prepare the entity object
				CitizenAppRegistrationEntity entity = new CitizenAppRegistrationEntity();
				entity.setStateName(stateName); // Set the state name from the API response
				BeanUtils.copyProperties(inputs, entity);

				// save the object
				int appId = citizenrepo.save(entity).getAppId();
				logger.info("Citizen application registered successfully with ID: {}", appId);
				return appId;
			}

			logger.warn("Citizen does not belong to target state. Found: {}, Required: {}", stateName, targetState);
			throw new InvalidSSNException("Citizen does not belong to " + targetState + " state");
		} catch (InvalidSSNException e) {
			logger.error("Invalid SSN exception: {}", e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error("Error validating SSN: {}", e.getMessage(), e);
			throw new InvalidSSNException("Error validating SSN: " + e.getMessage());
		}
	}

	/**
	 * Fallback method for registerCitizenApplication
	 *
	 * @param inputs The citizen application registration inputs
	 * @param ex The exception that triggered the fallback
	 * @return A temporary application ID
	 * @throws InvalidSSNException If the SSN is invalid
	 */
	public Integer registerCitizenApplicationFallback(CitizenAppRegistrationInputs inputs, Exception ex) throws InvalidSSNException {
		// Log the exception
		logger.warn("Fallback method called for SSN: {}, Error: {}", inputs.getSsn(), ex.getMessage());
		logger.debug("Exception details:", ex);

		// Create a temporary registration with a note about the SSA service being unavailable
		CitizenAppRegistrationEntity entity = new CitizenAppRegistrationEntity();
		BeanUtils.copyProperties(inputs, entity);
		entity.setStateName("UNKNOWN - SSA Service Unavailable");
		entity.setRemark("TEMPORARY REGISTRATION - SSA Service Unavailable: " + ex.getMessage());

		logger.info("Creating temporary registration for SSN: {} due to SSA service unavailability", inputs.getSsn());

		// Save the temporary registration
		int appId = citizenrepo.save(entity).getAppId();
		logger.info("Temporary registration created with ID: {}", appId);

		// Return the temporary application ID
		return appId;
	}

}
