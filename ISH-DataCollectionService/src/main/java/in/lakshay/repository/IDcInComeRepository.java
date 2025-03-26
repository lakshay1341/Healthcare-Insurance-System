package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.DcIncomeEntity;

public interface IDcInComeRepository extends JpaRepository<DcIncomeEntity, Integer>{

	DcIncomeEntity findByCaseNo(Integer caseNo);

}
