//Service Interface
package in.lakshay.service;

import in.lakshay.bindigs.ActivateUser;
import in.lakshay.bindigs.LoginCredentials;
import in.lakshay.bindigs.RecoverPassword;
import in.lakshay.bindigs.UserAccount;

import java.util.List;






public interface IUserMgmtService {
	public  String  registerUser(UserAccount user)throws Exception;
	public   String   activateUserAccount(ActivateUser user);
	public  String   login(LoginCredentials credentials);
	public   List<UserAccount>  listUsers();
	public   UserAccount   showUserByUserId(Integer id);
	public    UserAccount   showUserByEmailAndName(String  email,String name);
	public   String  updateUser(UserAccount  user);
	public    String   deleteUserById(Integer  id);
	public    String   changeUserStatus(Integer id,String  status);
	public   String   recoverPassword(RecoverPassword recover)throws Exception;
	}
