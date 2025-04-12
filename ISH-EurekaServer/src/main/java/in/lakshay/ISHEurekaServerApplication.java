package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ISHEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ISHEurekaServerApplication.class, args);
	}

}
