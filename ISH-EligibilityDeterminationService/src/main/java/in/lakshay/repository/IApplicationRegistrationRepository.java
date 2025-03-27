package in.lakshay.repository;

import in.lakshay.entity.CitizenAppRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IApplicationRegistrationRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer>{

}
