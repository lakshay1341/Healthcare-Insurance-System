package in.lakshay.rest;

import in.lakshay.bindigs.WorkerAccount;
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
@RequestMapping("/admin/worker-api")
@Slf4j
public class AdminWorkerMgmtController {
    
    @Autowired
    private IWorkerMgmtService workerService;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerWorker(@RequestBody WorkerAccount account) {
        try {
            String resultMsg = workerService.registerWorker(account);
            return new ResponseEntity<>(resultMsg, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<Object> getAllWorkers() {
        try {
            List<WorkerAccount> list = workerService.listWorkers();
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/find/{id}")
    public ResponseEntity<Object> getWorkerById(@PathVariable Integer id) {
        try {
            WorkerAccount account = workerService.showWorkerByWorkerId(id);
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update")
    public ResponseEntity<String> updateWorker(@RequestBody WorkerAccount account) {
        try {
            String resultMsg = workerService.updateWorker(account);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable Integer id) {
        try {
            String resultMsg = workerService.deleteWorkerById(id);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PatchMapping("/status/{id}/{status}")
    public ResponseEntity<String> changeWorkerStatus(@PathVariable Integer id, @PathVariable String status) {
        try {
            String resultMsg = workerService.changeWorkerStatus(id, status);
            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
