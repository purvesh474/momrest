package momrest.service;

import java.util.List;

import momrest.model.User;

public interface IUserService {

	List<User> getAllUsers();
	User getUserByID(int id);
	boolean addUser(User user);
	void updateUser(User user);
	void deleteUser(int id);
	List<User> checkUserLogin(String email,String password);
	List<User> searchUserEmail(String email);
	boolean isGuestuser(String email);
}
