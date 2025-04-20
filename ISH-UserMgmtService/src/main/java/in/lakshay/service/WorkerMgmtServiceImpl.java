package in.lakshay.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import in.lakshay.bindigs.ActivateWorker;
import in.lakshay.bindigs.WorkerAccount;
import in.lakshay.bindigs.WorkerCredentials;
import in.lakshay.entity.WorkerMaster;
import in.lakshay.repository.IWorkerMasterRepository;
import in.lakshay.utils.EmailUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
// Security imports removed
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WorkerMgmtServiceImpl implements IWorkerMgmtService {

    @Autowired
    private IWorkerMasterRepository workerMasterRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private Environment env;

    @Override
    public String registerWorker(WorkerAccount worker) throws Exception {
        // Convert WorkerAccount obj data to WorkerMaster obj (Entity obj) data
        WorkerMaster master = new WorkerMaster();
        BeanUtils.copyProperties(worker, master);

        // Set random string of 6 chars as password
        String tempPwd = generateRandomPassword(6);
        // Store password directly without encoding
        master.setPassword(tempPwd);
        master.setActiveSw("InActive");

        // Save object
        WorkerMaster savedMaster = workerMasterRepo.save(master);

        try {
            // Send email with temporary password
            log.debug("Loading email template from: {}", env.getProperty("mailbody.registerworker.location"));
            String subject = "Worker Registration Success";
            String body = readEmailMessageBody(env.getProperty("mailbody.registerworker.location"), worker.getName(), tempPwd);
            log.debug("Email body loaded successfully");
            emailUtils.sendEmailMessage(worker.getEmail(), subject, body);
            log.debug("Email sent successfully");
        } catch (Exception e) {
            log.error("Error sending registration email: " + e.getMessage(), e);
            // Continue with registration even if email fails
        }

        // Return message
        return savedMaster.getWorkerId() != null ?
               "Worker is registered with Id value:: " + savedMaster.getWorkerId() + " - check mail for temp password" :
               "Problem in worker registration";
    }

    @Override
    public String activateWorkerAccount(ActivateWorker worker) {
        // Since passwords are now encoded, we need to find the worker by email only
        WorkerMaster entity = workerMasterRepo.findByEmail(worker.getEmail());

        if (entity == null) {
            return "Worker is not found for activation";
        }

        // Verify if the temporary password matches
        if (!worker.getTempPassword().equals(entity.getPassword())) {
            return "Invalid temporary password";
        }

        // Set the new password (without encoding)
        entity.setPassword(worker.getConfirmPassword());
        // Change the worker account status to active
        entity.setActiveSw("Active");
        // Update the obj
        workerMasterRepo.save(entity);
        return "Worker is activated with new Password";
    }

    @Override
    public String login(WorkerCredentials credentials) {
        // Find worker by email
        WorkerMaster entity = workerMasterRepo.findByEmail(credentials.getEmail());

        // Check if worker exists
        if (entity == null) {
            return "Invalid credentials";
        }

        // Check if password matches
        if (!credentials.getPassword().equals(entity.getPassword())) {
            return "Invalid credentials";
        }

        // Check if account is active
        if (entity.getActiveSw().equalsIgnoreCase("Active")) {
            return "Valid credentials and Login successful";
        } else {
            return "Worker Account is not active";
        }
    }

    @Override
    public List<WorkerAccount> listWorkers() {
        // Load all entities and convert to WorkerAccount obj
        return workerMasterRepo.findAll().stream().map(entity -> {
            WorkerAccount worker = new WorkerAccount();
            BeanUtils.copyProperties(entity, worker);
            return worker;
        }).toList();
    }

    @Override
    public WorkerAccount showWorkerByWorkerId(Integer id) {
        // Load the worker by worker id
        Optional<WorkerMaster> opt = workerMasterRepo.findById(id);
        WorkerAccount account = null;

        if (opt.isPresent()) {
            account = new WorkerAccount();
            BeanUtils.copyProperties(opt.get(), account);
        }

        return account;
    }

    @Override
    public WorkerAccount showWorkerByEmailAndName(String email, String name) {
        // Use the custom findBy(-) method
        WorkerMaster master = workerMasterRepo.findByNameAndEmail(name, email);
        WorkerAccount account = null;

        if (master != null) {
            account = new WorkerAccount();
            BeanUtils.copyProperties(master, account);
        }

        return account;
    }

    @Override
    public String updateWorker(WorkerAccount worker) {
        // Use the findById method
        Optional<WorkerMaster> opt = workerMasterRepo.findById(worker.getWorkerId());

        if (opt.isPresent()) {
            // Get Entity object
            WorkerMaster master = opt.get();
            BeanUtils.copyProperties(worker, master);
            workerMasterRepo.save(master);
            return "Worker Details are updated";
        } else {
            return "Worker not found for updation";
        }
    }

    @Override
    public String deleteWorkerById(Integer id) {
        // Load the obj
        Optional<WorkerMaster> opt = workerMasterRepo.findById(id);

        if (opt.isPresent()) {
            workerMasterRepo.deleteById(id);
            return "Worker is deleted";
        }

        return "Worker is not found for deletion";
    }

    @Override
    public String changeWorkerStatus(Integer id, String status) {
        // Load the obj
        Optional<WorkerMaster> opt = workerMasterRepo.findById(id);

        if (opt.isPresent()) {
            // Get Entity obj
            WorkerMaster master = opt.get();
            // Change the status
            master.setActiveSw(status);
            // Update the obj
            workerMasterRepo.save(master);
            return "Worker status changed";
        }

        return "Worker not found for changing the status";
    }

    @Override
    public String recoverPassword(String name, String email) throws Exception {
        // Get WorkerMaster (Entity obj) by name, email
        WorkerMaster master = workerMasterRepo.findByNameAndEmail(name, email);

        if (master != null) {
            String pwd = master.getPassword();
            // Send the recovered password to the email account
            String subject = "Mail for password recovery";
            String mailBody = readEmailMessageBody(env.getProperty("mailbody.recoverpwd.location"), name, pwd);
            emailUtils.sendEmailMessage(email, subject, mailBody);
            return pwd + " mail is sent having the recovered password";
        }

        return "Worker name and email is not found";
    }

    // Helper methods for same class
    private String generateRandomPassword(int length) {
        // A list of characters to choose from in form of a string
        String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        // Creating a StringBuffer size of AlphaNumericStr
        StringBuilder randomWord = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // Generating a random number using SecureRandom
            SecureRandom random = new SecureRandom();
            float randVal = random.nextFloat();
            int ch = (int)(alphaNumericStr.length() * randVal);
            // Adding Random character one by one at the end of randomWord
            randomWord.append(alphaNumericStr.charAt(ch));
        }

        return randomWord.toString();
    }

    private String readEmailMessageBody(String resourcePath, String fullName, String pwd) throws Exception {
        String mailBody = null;
        String url = "http://localhost:4041/worker/activate";

        log.debug("Reading email template from path: {}", resourcePath);
        try {
            // Use Spring's ResourceLoader to load the file from classpath
            String actualPath = resourcePath.replace("classpath:", "");
            log.debug("Actual resource path after removing 'classpath:': {}", actualPath);

            org.springframework.core.io.Resource resource =
                new org.springframework.core.io.ClassPathResource(actualPath);

            log.debug("Resource exists: {}", resource.exists());
            log.debug("Resource description: {}", resource.getDescription());

            // Read the resource content
            try (java.io.InputStream is = resource.getInputStream();
                 java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(is))) {

                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }

                mailBody = buffer.toString();
                log.debug("Email template content loaded, length: {}", mailBody.length());

                mailBody = mailBody.replace("{FULL-NAME}", fullName);
                mailBody = mailBody.replace("{PWD}", pwd);
                mailBody = mailBody.replace("{URL}", url);
                log.debug("Email template placeholders replaced successfully");
            }
        } catch (Exception e) {
            log.error("Error reading email template: " + e.getMessage(), e);
            throw e;
        }

        return mailBody;
    }
}
