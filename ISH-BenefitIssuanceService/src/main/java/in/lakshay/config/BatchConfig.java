package in.lakshay.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import in.lakshay.model.Beneficiary;
import in.lakshay.processor.BeneficiaryItemProcessor;

@Configuration
public class BatchConfig {
    
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private PlatformTransactionManager transactionManager;
    
    @Value("${benefit.issuance.output-file}")
    private String outputFile;
    
    // Reader bean
    @Bean
    public JdbcCursorItemReader<Beneficiary> reader() {
        JdbcCursorItemReader<Beneficiary> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT ed.case_no as caseNo, ed.holder_name as holderName, " +
                     "ed.holder_ssn as holderSsn, ed.plan_name as planName, " +
                     "ed.benifit_amt as benefitAmount, ed.bank_name as bankName, " +
                     "ed.account_number as accountNumber " +
                     "FROM eligibility_determination ed " +
                     "WHERE ed.plan_status = 'AP'");
        reader.setRowMapper(new BeanPropertyRowMapper<>(Beneficiary.class));
        return reader;
    }
    
    // Processor bean
    @Bean
    public BeneficiaryItemProcessor processor() {
        return new BeneficiaryItemProcessor();
    }
    
    // Writer bean
    @Bean
    public FlatFileItemWriter<Beneficiary> writer() {
        // Create field extractor
        BeanWrapperFieldExtractor<Beneficiary> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"caseNo", "holderName", "holderSsn", "planName", 
                                             "benefitAmount", "bankName", "accountNumber"});
        
        // Create line aggregator
        DelimitedLineAggregator<Beneficiary> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);
        
        // Create and configure writer
        FlatFileItemWriter<Beneficiary> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(outputFile));
        writer.setLineAggregator(lineAggregator);
        writer.setHeaderCallback(writer1 -> writer1.write("Case Number,Holder Name,SSN,Plan Name,Benefit Amount,Bank Name,Account Number"));
        
        return writer;
    }
    
    // Step bean
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<Beneficiary, Beneficiary>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    // Job bean
    @Bean
    public Job job() {
        return new JobBuilder("benefitIssuanceJob", jobRepository)
                .start(step1())
                .build();
    }
}
