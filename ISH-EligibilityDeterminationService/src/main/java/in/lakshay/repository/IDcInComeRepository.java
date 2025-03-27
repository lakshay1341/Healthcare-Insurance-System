package in.lakshay.repository;

import in.lakshay.entity.DcIncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDcInComeRepository extends JpaRepository<DcIncomeEntity, Integer>{

	DcIncomeEntity findByCaseNo(Integer caseNo);

}
