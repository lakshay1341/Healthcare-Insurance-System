package in.lakshay.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.bindigs.LoginCredentials;
import in.lakshay.entity.UserMaster;
import in.lakshay.entity.WorkerMaster;
import in.lakshay.repository.IUserMasterRepository;
import in.lakshay.repository.IWorkerMasterRepository;
import in.lakshay.service.IUserMgmtService;
import in.lakshay.service.IWorkerMgmtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IUserMasterRepository userRepository;

    @Autowired
    private IWorkerMasterRepository workerRepository;

    @Autowired
    private IUserMgmtService userService;

    @Autowired
    private IWorkerMgmtService workerService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        logger.info("Login attempt for email: {}", credentials.getEmail());

        // Try to authenticate as a user
        logger.debug("Attempting user authentication for: {}", credentials.getEmail());
        String userResult = userService.login(credentials);

        if (userResult.contains("Valid credentials")) {
            logger.info("User authentication successful for: {}", credentials.getEmail());
            UserMaster user = userRepository.findByEmail(credentials.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("type", "USER");
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        } else {
            logger.debug("User authentication failed for: {}, result: {}", credentials.getEmail(), userResult);
        }

        // Try to authenticate as a worker
        logger.debug("Attempting worker authentication for: {}", credentials.getEmail());
        in.lakshay.bindigs.WorkerCredentials workerCredentials = new in.lakshay.bindigs.WorkerCredentials();
        workerCredentials.setEmail(credentials.getEmail());
        workerCredentials.setPassword(credentials.getPassword());

        String workerResult = workerService.login(workerCredentials);

        if (workerResult.contains("Valid credentials")) {
            logger.info("Worker authentication successful for: {}", credentials.getEmail());
            WorkerMaster worker = workerRepository.findByEmail(credentials.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("email", worker.getEmail());
            response.put("type", "WORKER");
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        } else {
            logger.debug("Worker authentication failed for: {}, result: {}", credentials.getEmail(), workerResult);
        }

        // Authentication failed
        logger.warn("Authentication failed for: {}", credentials.getEmail());
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    // JWT token generation methods removed
}
