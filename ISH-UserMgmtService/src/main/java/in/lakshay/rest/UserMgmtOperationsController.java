//Rest controller
package in.lakshay.rest;

import java.util.List;

import in.lakshay.bindigs.ActivateUser;
import in.lakshay.bindigs.LoginCredentials;
import in.lakshay.bindigs.RecoverPassword;
import in.lakshay.bindigs.UserAccount;
import in.lakshay.service.IUserMgmtService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user-api")
@Slf4j
public class UserMgmtOperationsController {
	@Autowired
	private IUserMgmtService userService;

	@PostMapping("/save")
	public  ResponseEntity<String>  saveUser(@RequestBody UserAccount account){
		//user  service
		try {
			String  resultMsg=userService.registerUser(account);
			return  new ResponseEntity<>(resultMsg,HttpStatus.CREATED);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			// Check for duplicate email constraint violation
			if (e.getMessage() != null && e.getMessage().contains("Duplicate entry") && e.getMessage().contains("email")) {
				return new ResponseEntity<>("Email address already exists. Please use a different email.", HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/activate")
	public  ResponseEntity<String>    activateUser(@RequestBody ActivateUser user){
		try {
			//use service
			String resultMsg=userService.activateUserAccount(user);
			return  new ResponseEntity<>(resultMsg,HttpStatus.CREATED);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public   ResponseEntity<String>   performLogin(@RequestBody LoginCredentials credentials){
		try {
			//use service
			String resultMsg=userService.login(credentials);
			return  new ResponseEntity<>(resultMsg,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/report")
	public   ResponseEntity<Object>   showUsers(){
		try {
			List<UserAccount>  list=userService.listUsers();
			return  new ResponseEntity<>(list,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/find/{id}")
	public  ResponseEntity<Object>  showUserById(@PathVariable Integer id){
		try {
			UserAccount  account=userService.showUserByUserId(id);
			return  new ResponseEntity<>(account,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find/{email}/{name}")
	public  ResponseEntity<Object>  showUserByEmailAndName(@PathVariable String  email,@PathVariable String  name){
		try {
			UserAccount  account=userService.showUserByEmailAndName(email, name);
			return  new ResponseEntity<>(account,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update")
	public  ResponseEntity<String>  updateUserDetails(@RequestBody UserAccount account){
		try {
			String resultMsg=userService.updateUser(account);
			return new ResponseEntity<>(resultMsg,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String>   deleteUserById(@PathVariable Integer id){
		try {
			String resultMsg=userService.deleteUserById(id);
			return new ResponseEntity<>(resultMsg,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/changeStatus/{id}/{status}")
	public  ResponseEntity<String>   changeStatus(@PathVariable Integer id , @PathVariable String status){
		try {
			String resultMsg=userService.changeUserStatus(id, status);
			return new ResponseEntity<>(resultMsg,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/recoverPassword")
	public   ResponseEntity<String>   recoverPassword(@RequestBody RecoverPassword recover){
		try {
			String  resultMsg=userService.recoverPassword(recover);
			return new ResponseEntity<>(resultMsg,HttpStatus.OK);
		}
		catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}//method




}//class
