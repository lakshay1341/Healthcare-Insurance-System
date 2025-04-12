package in.lakshay.service;

import org.springframework.batch.core.JobExecution;

public interface IBenefitIssuanceMgmtService {
    JobExecution sendAmountToBenificries() throws Exception;
}
