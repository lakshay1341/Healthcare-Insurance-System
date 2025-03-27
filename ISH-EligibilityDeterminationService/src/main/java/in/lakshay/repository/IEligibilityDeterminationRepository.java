package in.lakshay.repository;

import in.lakshay.entity.EligibilityDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEligibilityDeterminationRepository extends JpaRepository<EligibilityDetailsEntity,Integer> {

}
