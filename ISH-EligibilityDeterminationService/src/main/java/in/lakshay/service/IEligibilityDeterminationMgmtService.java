package in.lakshay.service;

import in.lakshay.bindings.EligibilityDetailsOutput;

public interface IEligibilityDeterminationMgmtService {

    public EligibilityDetailsOutput determineEligibility(Integer caseNo);

}
