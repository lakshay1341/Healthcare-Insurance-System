package in.lakshay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ISHConfigServerGitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ISHConfigServerGitApplication.class, args);
	}

}
