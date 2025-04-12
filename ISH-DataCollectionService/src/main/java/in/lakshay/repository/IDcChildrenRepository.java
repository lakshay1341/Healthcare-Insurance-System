package in.lakshay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.DcChildrenEntity;

public interface IDcChildrenRepository extends JpaRepository<DcChildrenEntity, Integer>{

	List<DcChildrenEntity> findByCaseNo(Long caseNo);

}
