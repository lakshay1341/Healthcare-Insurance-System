package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IshEligibilityDeterminationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IshEligibilityDeterminationServiceApplication.class, args);
	}

}
