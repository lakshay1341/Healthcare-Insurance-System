package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.EligibilityDetailsEntity;

public interface IEligibilityDeterminationRepository extends JpaRepository<EligibilityDetailsEntity, Integer> {
    EligibilityDetailsEntity findByCaseNo(Long caseNo);
}
