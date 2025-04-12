package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.DcEducationEntity;


public interface IDcEducationRepository extends JpaRepository<DcEducationEntity, Integer>{

	DcEducationEntity findByCaseNo(Long caseNo);

}
