package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class for the ISH Report Service
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "in.lakshay")
@EntityScan(basePackages = "in.lakshay.entity")
public class ISHReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISHReportServiceApplication.class, args);
    }
}
