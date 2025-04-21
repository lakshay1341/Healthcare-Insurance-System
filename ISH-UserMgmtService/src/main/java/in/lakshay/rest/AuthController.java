package in.lakshay.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.bindigs.LoginCredentials;
import in.lakshay.entity.AdminMaster;
import in.lakshay.entity.UserMaster;
import in.lakshay.entity.WorkerMaster;
import in.lakshay.repository.IAdminMasterRepository;
import in.lakshay.repository.IUserMasterRepository;
import in.lakshay.repository.IWorkerMasterRepository;
import in.lakshay.security.CustomUserDetailsService;
import in.lakshay.security.JwtUtil;
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
    private IAdminMasterRepository adminRepository;

    @Autowired
    private IUserMgmtService userService;

    @Autowired
    private IWorkerMgmtService workerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentials credentials) {
        logger.info("Login attempt for email: {}", credentials.getEmail());

        try {
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword())
            );

            // Generate JWT token
            UserDetails userDetails = userDetailsService.loadUserByUsername(credentials.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            // Determine user type
            String userType = "USER";
            String name = "";

            // Check if admin
            AdminMaster admin = adminRepository.findByEmail(credentials.getEmail());
            if (admin != null) {
                userType = "ADMIN";
                name = admin.getName();
            } else {
                // Check if worker
                WorkerMaster worker = workerRepository.findByEmail(credentials.getEmail());
                if (worker != null) {
                    userType = "WORKER";
                    name = worker.getName();
                } else {
                    // Must be a regular user
                    UserMaster user = userRepository.findByEmail(credentials.getEmail());
                    if (user != null) {
                        name = user.getName();
                    }
                }
            }

            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");
            response.put("email", credentials.getEmail());
            response.put("name", name);
            response.put("type", userType);
            response.put("message", "Login successful");

            logger.info("Authentication successful for: {} as {}", credentials.getEmail(), userType);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            logger.warn("Authentication failed for: {}", credentials.getEmail());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
            errorResponse.put("error", "Unauthorized");
            errorResponse.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            logger.error("Error during authentication: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "An error occurred during authentication");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Handle worker login and generate JWT token
     * This endpoint is specifically for worker login
     *
     * @param credentials Worker credentials
     * @return Response with JWT token and worker details
     */
    @PostMapping("/worker-login")
    public ResponseEntity<?> workerLogin(@RequestBody in.lakshay.bindigs.WorkerCredentials credentials) {
        logger.info("Worker login attempt for email: {}", credentials.getEmail());

        try {
            // Find worker by email
            WorkerMaster worker = workerRepository.findByEmail(credentials.getEmail());

            // Check if worker exists
            if (worker == null) {
                logger.warn("Worker not found: {}", credentials.getEmail());
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
                errorResponse.put("error", "Unauthorized");
                errorResponse.put("message", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            // Check if password matches using BCrypt
            if (!passwordEncoder.matches(credentials.getPassword(), worker.getPassword())) {
                logger.warn("Invalid password for worker: {}", credentials.getEmail());
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
                errorResponse.put("error", "Unauthorized");
                errorResponse.put("message", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            // Check if account is active
            if (!worker.getActiveSw().equalsIgnoreCase("Active")) {
                logger.warn("Worker account not active: {}", credentials.getEmail());
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", HttpStatus.UNAUTHORIZED.value());
                errorResponse.put("error", "Unauthorized");
                errorResponse.put("message", "Worker account is not active");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
            }

            // Generate JWT token
            String token = jwtUtil.generateToken(credentials.getEmail(), "ROLE_WORKER");

            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("tokenType", "Bearer");
            response.put("email", credentials.getEmail());
            response.put("name", worker.getName());
            response.put("type", "WORKER");
            response.put("message", "Login successful");

            logger.info("Authentication successful for worker: {}", credentials.getEmail());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error during worker authentication: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "An error occurred during authentication");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
