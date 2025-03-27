package in.lakshay.repository;

import in.lakshay.entity.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlanRepository extends JpaRepository<PlanEntity, Integer>{

}
