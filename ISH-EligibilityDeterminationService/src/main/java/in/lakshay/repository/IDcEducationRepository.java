package in.lakshay.repository;

import in.lakshay.entity.DcEducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IDcEducationRepository extends JpaRepository<DcEducationEntity, Integer>{

	DcEducationEntity findByCaseNo(Integer caseNo);

}
