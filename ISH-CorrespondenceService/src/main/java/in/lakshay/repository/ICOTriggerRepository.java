package in.lakshay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.lakshay.entity.CoTriggersEntity;

public interface ICOTriggerRepository extends JpaRepository<CoTriggersEntity, Integer> {
    public List<CoTriggersEntity> findByTriggerStatus(String status);

    public CoTriggersEntity findByCaseNo(int caseNo);
}
