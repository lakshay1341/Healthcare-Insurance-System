package in.lakshay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.ElgibilityDetailsEntity;

public interface IElgibiltyDeterminationRepository extends JpaRepository<ElgibilityDetailsEntity, Integer> {
    public ElgibilityDetailsEntity findByCaseNo(int caseno);
}
