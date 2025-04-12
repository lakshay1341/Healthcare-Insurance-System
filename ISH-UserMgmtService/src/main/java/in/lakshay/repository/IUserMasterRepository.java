//Repository Interface
package in.lakshay.repository;

import in.lakshay.entity.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserMasterRepository extends JpaRepository<UserMaster, Integer> {
     public    UserMaster   findByEmailAndPassword(String mail,String pwd);
     public   UserMaster   findByNameAndEmail(String name, String  mail);
}
