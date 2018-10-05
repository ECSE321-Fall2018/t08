package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
	@PersistenceContext
    EntityManager em;

	@Transactional
	public User createUser(
        String userName, 
        boolean isuseractive, 
        String emailaddress, 
        String fullname, 
        String role, 
        String password
    ) {
        User existingUser = em.find(User.class, userName);

        /*
         * Don't create the user under these conditions:
         * - the username is taken
         * - another user has this email
         * - the password is less than 8 characters
         */

        if (existingUser != null) {
            return null;
        }

        User existingUserEmail = em.find(User.class, emailaddress);
        if (existingUserEmail != null) {
            return null;
        }

        if (password.length() < 8) {
            return null;
        }

        User user = new User();
        user.setUsername(userName);
        user.setStatus(isuseractive);
        user.setEmailAddress(emailaddress);
        user.setFullName(fullname);
        user.setRole(role);
        user.setPassword(password);

        em.persist(user);
        return user;
    }

    @Transactional
    public User getUser(int userId) {
        return em.find(User.class, userId);
    }

    @Transactional
    public User updateUser(String userName, boolean isuseractive, String emailaddress, String fullname, String role, String password) {
        List<User> userList = em.createNamedQuery("User.findUserName")
                .setParameter("usernameparam", "'%" +userName + "%'")
                .getResultList();

        if (userList.isEmpty() || userList.size() > 1) {
            return null;
        }

        User user = userList.get(0);
        if (!(user.getPassword().equals(password))) {
            return null;
        }

        em.getTransaction().begin(); // Indicates to database that changes might begin to entity
        if (user.getStatus() != isuseractive) {
            user.setStatus(isuseractive);
        }

        if (!(user.getEmailAddress().equalsIgnoreCase(emailaddress))) {
            user.setEmailAddress(emailaddress);
        }

        if (!(user.getFullName().equalsIgnoreCase(fullname))) {
            user.setFullName(fullname);
        }

        if (!(user.getRole().equalsIgnoreCase(role))) {
            user.setRole(role);
        }
        em.getTransaction().commit(); //Indicates to database that changes finished

        return user;
    }

    @Transactional
    public List<User> findUser(String userName, String emailAddress, String name) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(userName))
            .filter(u -> u.getEmailAddress().equalsIgnoreCase(emailAddress))
            .filter(u -> u.getFullName().equalsIgnoreCase(name))
            .collect(Collectors.toList());
    }

}
