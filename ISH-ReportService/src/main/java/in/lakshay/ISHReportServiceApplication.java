package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Report Service
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ISHReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISHReportServiceApplication.class, args);
    }
}
