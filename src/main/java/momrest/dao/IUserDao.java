package momrest.dao;

import java.io.Serializable;
import java.util.List;

import momrest.model.User;

public interface IUserDao {

	List<User> getAllUsers();
	void addUser(User user);
	void updateUser(User user);
	User getUserByID(int id);
	void deleteUser(int id);
	boolean usernameExists(String username);
	boolean userExists(String email);
	int getUsedIdFromEmail(String email);
	List<User> userLogin(String email,String password);
	int getLastInsertedID();
	List<User> searchUserByEmailID(String email);
	
	
}
