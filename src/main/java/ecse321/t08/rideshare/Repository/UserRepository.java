package ecse321.t08.rideshare.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ecse321.t08.rideshare.Entity.User;

@Repository
public class UserRepository {

	@PersistenceContext
	EntityManager entityManager;

	@Transactional
	public User createUser(String userName, boolean isuseractive, String emailaddress, String fullname, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setStatus(isuseractive);
        user.setEmailAddress(emailaddress);
        user.setFullName(fullname);
        user.setPassword(password);
        entityManager.persist(user);
        return user;
    }
}
