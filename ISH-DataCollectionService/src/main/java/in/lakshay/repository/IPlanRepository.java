package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.PlanEntity;

public interface IPlanRepository extends JpaRepository<PlanEntity, Integer>{

}
