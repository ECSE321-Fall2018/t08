package ecse321.t08.rideshare.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ecse321.t08.rideshare.Entity.User;

import java.util.List;

@Repository
public class UserRepository {

	@PersistenceContext
    EntityManager em;


	@Transactional
	public User createUser(String userName, boolean isuseractive, String emailaddress, String fullname, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setStatus(isuseractive);
        user.setEmailAddress(emailaddress);
        user.setFullName(fullname);
        user.setPassword(password);
        em.persist(user);
        return user;
    }

    @Transactional
    public User getUser(int userId) {
	    User user = em.find(User.class, userId);
	    return user;
    }

    @Transactional
    public User updateUser(String userName, boolean isuseractive, String emailaddress, String fullname, String password) {
        List<User> userList = em.createNamedQuery("User.findUserName")
                .setParameter("username", userName)
                .getResultList();


        if(userList.isEmpty() || userList.size() > 1) {
            return null;
        }
        User user = userList.get(0);

        em.getTransaction().begin(); //Indicates to database that changes might begin to entity
        if(user.getStatus() != isuseractive) {
            user.setStatus(isuseractive);
        }

        if(!(user.getEmailAddress().equalsIgnoreCase(emailaddress))) {
            user.setEmailAddress(emailaddress);
        }

        if(user.getFullName() != fullname) {
            user.setFullName(fullname);
        }
        em.getTransaction().commit(); //Indicates to database that changes finished


        return user;
    }

    @Transactional
    public List<User> findUser(String userName, String emailAddress, String name) {

        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();


        if(userName != null && userName != "") {
            for (int i = 0; i < userlist.size(); i++) {
                if(!(userlist.get(i).getUserName().toUpperCase().contains(userName.toUpperCase()))) {
                    userlist.remove(i);
                }
            }
        }

        if(emailAddress != null && emailAddress != "") {
            for (int i = 0; i < userlist.size(); i++) {
                if(!(userlist.get(i).getEmailAddress().toUpperCase().contains(emailAddress.toUpperCase()))) {
                    userlist.remove(i);
                }
            }
        }

        if(name != null && name != "") {
            for (int i = 0; i < userlist.size(); i++) {
                if(!(userlist.get(i).getFullName().toUpperCase().contains(name.toUpperCase()))) {
                    userlist.remove(i);
                }
            }
        }


        return userlist;
    }

}
