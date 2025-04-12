package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ISHBenefitIssuanceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ISHBenefitIssuanceServiceApplication.class, args);
    }

}
