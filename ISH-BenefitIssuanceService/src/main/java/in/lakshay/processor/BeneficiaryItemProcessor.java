package in.lakshay.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import in.lakshay.model.Beneficiary;

public class BeneficiaryItemProcessor implements ItemProcessor<Beneficiary, Beneficiary> {
    
    private static final Logger log = LoggerFactory.getLogger(BeneficiaryItemProcessor.class);

    @Override
    public Beneficiary process(Beneficiary beneficiary) throws Exception {
        // Log the beneficiary details
        log.info("Processing beneficiary: {}", beneficiary);
        
        // Here you could add business logic to transform or validate the beneficiary
        // For example, you might want to mask the SSN for security
        
        return beneficiary;
    }
}
