package in.lakshay.repository;

import in.lakshay.entity.AdminMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdminMasterRepository extends JpaRepository<AdminMaster, Integer> {
    AdminMaster findByEmail(String email);
}
