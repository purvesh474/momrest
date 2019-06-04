package momrest.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import momrest.model.User;

@Transactional
@Repository
public class UserDao implements IUserDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<User> getAllUsers() {
		String hql = "FROM User as u ORDER BY u.userid";

		return (List<User>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public void addUser(User user) {
		entityManager.persist(user);

	}

	@Override
	public void updateUser(User user) {
		int userid=getUsedIdFromEmail(user.getEmail());
		user.setUserid(userid);
		User updateUser = getUserByID(user.getUserid());
		updateUser.setPassword(user.getPassword());
		updateUser.setFirstname(user.getFirstname());
		updateUser.setLastname(user.getLastname());
		updateUser.setAddress(user.getAddress());
		updateUser.setCompany(user.getCompany());
		updateUser.setBirthdate(user.getBirthdate());
		entityManager.flush();

	}

	@Override
	public User getUserByID(int id) {

		return entityManager.find(User.class, id);
	}

	@Override
	public void deleteUser(int id) {
		entityManager.remove(getUserByID(id));

	}

	@Override
	public boolean usernameExists(String username) {
		String hql="from User as u where u.username=?1";
		int count= entityManager.createQuery(hql).setParameter(1, username).getResultList().size();
		
		return count > 0 ? true : false ;
	}

	@Override
	public boolean userExists(String email) {
		String hql="from User as u where u.email=?1";
		int count=entityManager.createQuery(hql).setParameter(1, email).getResultList().size();
		return count > 0 ? true:false;
	}

	@Override
	public int getUsedIdFromEmail(String email) {
		String hql="from User as u where u.email=?1";
		User user = (User) entityManager.createQuery(hql).setParameter(1, email).getSingleResult();
		return user.getUserid();
	}

	@Override
	public List<User> userLogin(String email, String password) {
		String hql="from User as u where u.email=?1 and u.password=?2";
		List<User> user=entityManager.createQuery(hql).setParameter(1, email).setParameter(2, password).getResultList();
		
		return user;
	}

}
