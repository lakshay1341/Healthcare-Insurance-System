package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.PlanCategory;

public interface IPlanCategoryRepository extends JpaRepository<PlanCategory, Integer> {

}
