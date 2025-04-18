package in.lakshay.rest;

import in.lakshay.bindigs.ActivateWorker;
import in.lakshay.bindigs.WorkerAccount;
import in.lakshay.bindigs.WorkerCredentials;
import in.lakshay.service.IWorkerMgmtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/worker-api")
@Slf4j
public class WorkerMgmtOperationsController {
    
    @Autowired
    private IWorkerMgmtService workerService;
    
    @PostMapping("/save")
    public ResponseEntity<String> saveWorker(@RequestBody WorkerAccount account) {
        try {
            String resultMsg = workerService.registerWorker(account);
            return new ResponseEntity<>(resultMsg, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/activate")
    public ResponseEntity<String> activateWorker(@RequestBody ActivateWorker worker) {
        try {
            String resultMsg = workerService.activateWorkerAccount(worker);
            return new ResponseEntity<>(resultMsg, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> performLogin(@RequestBody WorkerCredentials credentials) {
        try {
            String resultMsg = workerService.login(credentials);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/report")
    public ResponseEntity<Object> showWorkers() {
        try {
            List<WorkerAccount> list = workerService.listWorkers();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<Object> showWorkerById(@PathVariable Integer id) {
        try {
            WorkerAccount account = workerService.showWorkerByWorkerId(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/find/{email}/{name}")
    public ResponseEntity<Object> showWorkerByEmailAndName(@PathVariable String email, @PathVariable String name) {
        try {
            WorkerAccount account = workerService.showWorkerByEmailAndName(email, name);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateWorkerDetails(@RequestBody WorkerAccount account) {
        try {
            String resultMsg = workerService.updateWorker(account);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorkerById(@PathVariable Integer id) {
        try {
            String resultMsg = workerService.deleteWorkerById(id);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PatchMapping("/changeStatus/{id}/{status}")
    public ResponseEntity<String> changeStatus(@PathVariable Integer id, @PathVariable String status) {
        try {
            String resultMsg = workerService.changeWorkerStatus(id, status);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/recover-password/{name}/{email}")
    public ResponseEntity<String> recoverPassword(@PathVariable String name, @PathVariable String email) {
        try {
            String resultMsg = workerService.recoverPassword(name, email);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
