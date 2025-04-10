package in.lakshay.service;

import org.springframework.batch.core.JobExecution;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;

public interface IBenifitIssuanceMgmtService {
    public     JobExecution   sendAmountToBenificries()throws Exception;
}
