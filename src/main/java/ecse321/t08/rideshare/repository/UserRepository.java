package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.Comparator;
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
        List<User> existingUserName = findUser(userName);
        List<User> existingUserEmail = findUserByEmail(emailaddress);

        /*
         * Don't create the user under these conditions:
         * - the username is taken
         * - another user has this email
         * - the password is less than 8 characters
         */

        if (existingUserName.size() != 0) {
            return null;
        }

        if (existingUserEmail.size() != 0) {
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
        user.setTripnumber(0);

        em.persist(user);
        return user;
    }

    @Transactional
    public User getUser(int userId) {
        return em.find(User.class, userId);
    }

    @Transactional
    public User updateUser(String userName, String emailaddress, String fullname, String role, String password) {
        List<User> userList = findUser(userName);

        if (userList.isEmpty() || userList.size() > 1) {
            return null;
        }

        User user = userList.get(0);
        if (!(user.getPassword().equals(password))) {
            return null;
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
        em.merge(user);
        return user;
    }

    @Transactional
    public int authenticateUser(String username, String password) {
	    List<User> userlist = findUser(username);
	    if(userlist.size() < 1 || userlist.size() > 1) {
	        return -1;
        }
        User user = userlist.get(0);
	    if(user.getPassword().equals(password)) {
	        return user.getUserID();
        } else {
	        return -1;
        }
    }


    @Transactional
    public List<User> findUser(String userName, String emailAddress, String name) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(userName))
            .filter(u -> u.getEmailAddress().toUpperCase().equalsIgnoreCase(emailAddress.toUpperCase()))
            .filter(u -> u.getFullName().toUpperCase().equalsIgnoreCase(name.toUpperCase()))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<User> findUser(String userName, String emailAddress) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(userName))
                .filter(u -> u.getEmailAddress().equalsIgnoreCase(emailAddress))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<User> findUser(String userName) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(userName))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<User> findUserByEmail(String emailAddress) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getEmailAddress().equalsIgnoreCase(emailAddress))
                .collect(Collectors.toList());
    }

    @Transactional
    public List getUnfilteredUserList(String username, String password) {
        List<User> user = findUser(username);
        // Check if user is admin
        if (user.size() == 0 || user.size() > 1 ||!(user.get(0).getRole().equalsIgnoreCase("administrator")) || !(user.get(0).getPassword().equals(password))) {
            return null;
        }
        return em.createNamedQuery("User.findAll").getResultList();
    }

    @Transactional
    public List getFilteredUserList(String username, String password) {
        List<User> user = findUser(username);
        // Check if user is admin
        if (user.size() == 0 || user.size() > 1 ||!(user.get(0).getRole().equalsIgnoreCase("administrator")) || !(user.get(0).getPassword().equals(password))) {
            return null;
        }
        List<User> userli = em.createNamedQuery("User.findAll").getResultList();
        Collections.sort(userli, Comparator.comparing(User::getTripnumber));
        if(userli.size() > 99) {
            return userli.subList(0, 99);
        }
        return userli;
    }

}
