package momrest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import momrest.dao.IUserDao;
import momrest.model.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	
	@Override
	public List<User> getAllUsers() {
		
		return userDao.getAllUsers();
	}


	@Override
	public User getUserByID(int id) {
		return userDao.getUserByID(id);
	}


	@Override
	public synchronized boolean addUser(User user) {
		if(userDao.userExists(user.getEmail())) {
			return false;
		}else {
			userDao.addUser(user);
			return true;
		}
	}


	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
		
	}


	@Override
	public void deleteUser(int id) {
		userDao.deleteUser(id);
		
	}


	@Override
	public List<User> checkUserLogin(String email, String password) {
	List<User> user=userDao.userLogin(email, password);
		return user;
	}

}
