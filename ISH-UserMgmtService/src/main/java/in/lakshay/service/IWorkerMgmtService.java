package in.lakshay.service;

import in.lakshay.bindigs.ActivateWorker;
import in.lakshay.bindigs.WorkerAccount;
import in.lakshay.bindigs.WorkerCredentials;

import java.util.List;

public interface IWorkerMgmtService {
    public String registerWorker(WorkerAccount worker) throws Exception;
    public String activateWorkerAccount(ActivateWorker worker);
    public String login(WorkerCredentials credentials);
    public List<WorkerAccount> listWorkers();
    public WorkerAccount showWorkerByWorkerId(Integer id);
    public WorkerAccount showWorkerByEmailAndName(String email, String name);
    public String updateWorker(WorkerAccount worker);
    public String deleteWorkerById(Integer id);
    public String changeWorkerStatus(Integer id, String status);
    public String recoverPassword(String name, String email) throws Exception;
}
