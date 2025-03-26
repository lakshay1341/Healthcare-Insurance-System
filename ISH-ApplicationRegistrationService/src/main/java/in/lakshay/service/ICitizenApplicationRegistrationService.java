package in.lakshay.service;

import in.lakshay.bindings.CitizenAppRegistrationInputs;
import in.lakshay.exceptions.InvalidSSNException;

public interface ICitizenApplicationRegistrationService {
	
	public Integer registerCitizenApplication(CitizenAppRegistrationInputs inputs)throws InvalidSSNException;



}
