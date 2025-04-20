package in.lakshay.repository;

import in.lakshay.entity.WorkerMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWorkerMasterRepository extends JpaRepository<WorkerMaster, Integer> {
    public WorkerMaster findByEmailAndPassword(String email, String password);
    public WorkerMaster findByNameAndEmail(String name, String email);
    public WorkerMaster findByEmail(String email);
}
