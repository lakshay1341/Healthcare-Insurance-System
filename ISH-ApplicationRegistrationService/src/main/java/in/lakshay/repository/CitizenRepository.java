package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.CitizenAppRegistrationEntity;

public interface CitizenRepository extends JpaRepository<CitizenAppRegistrationEntity, Integer>{

}
