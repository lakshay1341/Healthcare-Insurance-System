//UserMgmtServiceImpl.java
package in.lakshay.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import in.lakshay.bindigs.RecoverPassword;
import in.lakshay.bindigs.UserAccount;
import in.lakshay.entity.UserMaster;
import in.lakshay.repository.IUserMasterRepository;
import in.lakshay.utils.EmailUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;


import in.lakshay.bindigs.ActivateUser;
import in.lakshay.bindigs.LoginCredentials;

@Service
@Slf4j
public class UserMgmtServiceImpl implements IUserMgmtService {
	@Autowired
	private IUserMasterRepository userMasterRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private Environment env;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String registerUser(UserAccount user) throws Exception {
		// convert UserAccount obj data to UserMaster obj(Entity obj) data
		UserMaster master = new UserMaster();
		BeanUtils.copyProperties(user, master);
		//set random string of 6 chars as password
		String tempPwd = generateRandomPassword(6);
		// Store password with BCrypt encoding
		master.setPassword(passwordEncoder.encode(tempPwd));
		master.setActiveSw("InActive");
		//save object
		UserMaster savedMaster = userMasterRepo.save(master);

		try {
			//perform send the mail Operation
			log.debug("Loading email template from: {}", env.getProperty("mailbody.registeruser.location"));
			String subject = "User Registration Success";
			String body = readEmailMEssageBody(env.getProperty("mailbody.registeruser.location"), user.getName(), tempPwd);
			log.debug("Email body loaded successfully");
			emailUtils.sendEmailMessage(user.getEmail(), subject, body);
			log.debug("Email sent successfully");
		} catch (Exception e) {
			log.error("Error sending registration email: " + e.getMessage(), e);
			// Continue with registration even if email fails
		}

		//return message
		return savedMaster.getUserId() != null ? "User is registered with Id value:: " + savedMaster.getUserId() + " - check mail for temp password" : "Problem in user registration";
	}

/*	@Override
	public String activateUserAccount(ActivateUser user) {
		// Convert   ActivateUser obj   to    Entity obj (UserMaster obj) data
		UserMaster  master=new UserMaster();
		 master.setEmail(user.getEmail());
		master.setPassword(user.getTempPassword());
		//  check the  record available by using email and  temp password
		  Example<UserMaster>  example=Example.of(master);
		  //select  * from  JRTP_USER_MASTER WHERE mail=? and passsword=?
		List<UserMaster> list=userMasterRepo.findAll(example);

		// if  valid  email and  temp is given    the set enduser supplied real password to update the record
		if(list.size()!=0) {
			//get Entity object
			UserMaster  entity=list.get(0);
			//set the password
			entity.setPassword(user.getConfirmPassword());
			// change the user account status to active
			entity.setActive_sw("Active");
			//update the obj
			UserMaster  updatedEntity=userMasterRepo.save(entity);
			return  "User  is  activated  with new  Password";
		}

		return "User is not found for activation";
	}*/

	@Override
	public String activateUserAccount(ActivateUser user) {
        // Find the user by email
		UserMaster entity = userMasterRepo.findByEmail(user.getEmail());

		if(entity == null) {
			return "User is not found for activation";
		}

		// Check if account is already active
		if ("Active".equals(entity.getActiveSw())) {
			return "User account is already active";
		}

		// Set the new password with BCrypt encoding
		entity.setPassword(passwordEncoder.encode(user.getConfirmPassword()));
		// Change the user account status to active
		entity.setActiveSw("Active");
		// Update the object
		userMasterRepo.save(entity);
		return "User is activated with new Password";
	}

	@Override
	public String login(LoginCredentials credentials) {
		// Find user by email
		UserMaster entity = userMasterRepo.findByEmail(credentials.getEmail());

		// Check if user exists
		if (entity == null) {
			return "Invalid credentials";
		}

		// Check if password matches using BCrypt
		if (!passwordEncoder.matches(credentials.getPassword(), entity.getPassword())) {
			return "Invalid credentials";
		}

		// Check if account is active
		if (entity.getActiveSw().equalsIgnoreCase("Active")) {
			return "Valid credentials and Login successful";
		} else {
			return "User Account is not active";
		}
	}

	@Override
	public List<UserAccount> listUsers() {
		// Load  all entities  and convert to UserAccount obj
 return  userMasterRepo.findAll().stream().map(entity->{
			  UserAccount user=new UserAccount();
			  BeanUtils.copyProperties(entity, user);
			  return user;
		}).toList();



//Load  all entities  and convert to UserAccount obj
		// convert all entities to   UserAccount objs
  /*List<UserMaster>  listEntities=userMasterRepo.findAll();
		List<UserAccount>  listUsers=new ArrayList();
		listEntities.forEach(entity->{
			UserAccount  user=new UserAccount();
			BeanUtils.copyProperties(entity, user);
			listUsers.add(user);
		});
		return listUsers;*/
	}

	@Override
	public UserAccount showUserByUserId(Integer id) {
		// Load the user  by user id
		Optional<UserMaster> opt=userMasterRepo.findById(id);
		UserAccount  account=null;
		if(opt.isPresent()) {
			 account=new UserAccount();
			BeanUtils.copyProperties(opt.get(), account);
		}
		return account;
	}

	@Override
	public UserAccount showUserByEmailAndName(String email, String name) {
		//use the custom  findBy(-) method
		UserMaster  master=userMasterRepo.findByNameAndEmail(name, email);
		UserAccount  account=null;
		if(master!=null) {
			account=new UserAccount();
			BeanUtils.copyProperties(master, account);
		}
		return account;
	}

	@Override
	public String updateUser(UserAccount user) {
		//use the custom  findBy(-) method
		Optional<UserMaster>  opt=userMasterRepo.findById(user.getUserId());
			if(opt.isPresent()) {
				//get Entity objet
				UserMaster  master=opt.get();
				BeanUtils.copyProperties(user,master);
				userMasterRepo.save(master);
				return  "User Details are updated";
			}
			else {
				return "User not found for updation";
			}
	}//method

	@Override
	public String deleteUserById(Integer id) {
		//  Load the obj
		Optional<UserMaster> opt=userMasterRepo.findById(id);
		if(opt.isPresent()) {
			userMasterRepo.deleteById(id);
			return "User  is  deleted";
		}
		return "user is  not found for deletion";
	}

	@Override
	public String changeUserStatus(Integer id, String status) {
		//  Load the obj
		Optional<UserMaster> opt=userMasterRepo.findById(id);
		if(opt.isPresent()) {
			//get Entity obj
			UserMaster  master=opt.get();
			//change the status
			master.setActiveSw(status);
			//update the obj
			userMasterRepo.save(master);
			return "User  status changed";
		}
		return "user  not found for changing the status";
	}

	@Override
	public String recoverPassword(RecoverPassword recover)throws Exception {
		//get   UserMaster (Entity obj)  by name, email
		UserMaster   master=userMasterRepo.findByNameAndEmail(recover.getName(), recover.getEmail());
		if(master!=null) {
			String  pwd=master.getPassword();
			 //send the recovered password  to the email account
			String  subject="  mail for  password recovery";
			String  mailBody=readEmailMEssageBody(env.getProperty("mailbody.recoverpwd.location"), recover.getName(), pwd);  //private method
			emailUtils.sendEmailMessage(recover.getEmail(), subject, mailBody);
			return  pwd +" mail is  sent having the recoved password";
		}
		return "User and  email  is not found";
	}


	//helper methods  for same class
	private  String generateRandomPassword(int length)
	 {
	 // a list of characters to choose from in form of a string
	 String alphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
	 // creating a StringBuffer size of AlphaNumericStr
	 StringBuilder randomWord = new StringBuilder(length);
	 int i;
	 for ( i=0; i<length; i++) {
	   //generating a random number using math.random()  (gives psuedo random number 0.0 to 1.0)
		  SecureRandom random=new SecureRandom();
		  float  randVal=random.nextFloat();
	   int ch = (int)(alphaNumericStr.length() * randVal);
	   //adding Random character one by one at the end of randonword
	   randomWord.append(alphaNumericStr.charAt(ch));
	  }
	    return randomWord.toString();
	 }//methid

	private String readEmailMEssageBody(String resourcePath, String fullName, String pwd) throws Exception {
		String mailBody = null;
		String url = "http://localhost:4041/activate";

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
