package in.lakshay.rest;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.lakshay.entity.AdminMaster;
import in.lakshay.repository.IAdminMasterRepository;

/**
 * Controller for admin operations
 */
@RestController
@RequestMapping("/admin-api")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private IAdminMasterRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Create a new admin user
     * This endpoint should be secured in production
     *
     * @param adminRequest Admin user details
     * @return Response with admin details
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody Map<String, String> adminRequest) {
        logger.info("Creating admin user: {}", adminRequest.get("email"));

        try {
            // Check if admin already exists
            AdminMaster existingAdmin = adminRepository.findByEmail(adminRequest.get("email"));
            if (existingAdmin != null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
                errorResponse.put("error", "Bad Request");
                errorResponse.put("message", "Admin with this email already exists");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            // Create new admin
            AdminMaster admin = new AdminMaster();
            admin.setName(adminRequest.get("name"));
            admin.setEmail(adminRequest.get("email"));
            admin.setPassword(passwordEncoder.encode(adminRequest.get("password")));
            admin.setRole("ROLE_ADMIN");
            admin.setActiveSw("Active");

            // Save admin
            AdminMaster savedAdmin = adminRepository.save(admin);

            // Create response
            Map<String, Object> response = new HashMap<>();
            response.put("adminId", savedAdmin.getAdminId());
            response.put("name", savedAdmin.getName());
            response.put("email", savedAdmin.getEmail());
            response.put("role", savedAdmin.getRole());
            response.put("message", "Admin created successfully");

            logger.info("Admin created successfully: {}", savedAdmin.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logger.error("Error creating admin: {}", e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorResponse.put("error", "Internal Server Error");
            errorResponse.put("message", "An error occurred while creating admin");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
