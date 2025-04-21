package in.lakshay.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import in.lakshay.entity.AdminMaster;
import in.lakshay.repository.IAdminMasterRepository;

/**
 * Database initializer to create default admin user on startup
 */
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private IAdminMasterRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if not exists
        if (adminRepository.findByEmail("admin@ish.com") == null) {
            logger.info("Creating default admin user");
            
            AdminMaster admin = new AdminMaster();
            admin.setName("System Admin");
            admin.setEmail("admin@ish.com");
            admin.setPassword(passwordEncoder.encode("root"));
            admin.setRole("ROLE_ADMIN");
            admin.setActiveSw("Active");
            
            adminRepository.save(admin);
            
            logger.info("Default admin user created successfully");
        } else {
            logger.info("Default admin user already exists");
        }
    }
}
