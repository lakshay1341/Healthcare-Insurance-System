package in.lakshay.security;

import in.lakshay.entity.AdminMaster;
import in.lakshay.entity.UserMaster;
import in.lakshay.entity.WorkerMaster;
import in.lakshay.repository.IAdminMasterRepository;
import in.lakshay.repository.IUserMasterRepository;
import in.lakshay.repository.IWorkerMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom UserDetailsService implementation
 * This service loads user details from the database
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserMasterRepository userRepository;

    @Autowired
    private IWorkerMasterRepository workerRepository;

    @Autowired
    private IAdminMasterRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find admin by email
        AdminMaster admin = adminRepository.findByEmail(email);
        if (admin != null) {
            return new User(
                    admin.getEmail(),
                    admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(admin.getRole()))
            );
        }

        // Try to find worker by email
        WorkerMaster worker = workerRepository.findByEmail(email);
        if (worker != null) {
            return new User(
                    worker.getEmail(),
                    worker.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(worker.getRole()))
            );
        }

        // Try to find user by email
        UserMaster user = userRepository.findByEmail(email);
        if (user != null) {
            return new User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
            );
        }

        // If no user is found, throw exception
        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
