package in.lakshay.repository;

import in.lakshay.entity.DcChildrenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDcChildrenRepository extends JpaRepository<DcChildrenEntity, Integer>{

	List<DcChildrenEntity> findByCaseNo(Integer caseNo);

}
