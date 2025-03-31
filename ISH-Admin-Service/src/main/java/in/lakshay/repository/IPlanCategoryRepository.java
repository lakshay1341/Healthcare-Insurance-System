package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.PlanCategory;

public interface IPlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {

}
