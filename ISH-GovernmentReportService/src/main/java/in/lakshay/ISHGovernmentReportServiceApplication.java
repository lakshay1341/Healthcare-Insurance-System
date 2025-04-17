package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main application class for the ISH Government Report Service
 * This service generates reports for government officials about citizen applications,
 * eligibility determinations, and benefit issuances.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ISHGovernmentReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISHGovernmentReportServiceApplication.class, args);
    }
}
