package ecse321.t08.rideshare.repository;

import ecse321.t08.rideshare.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
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
        String username,
        String emailAddress,
        String fullName,
        String role,
        String password
    ) {
        List<User> existingUsername = findUser(username);
        List<User> existingUserEmail = findUserByEmail(emailAddress);

        /*
         * Don't create the user under these conditions:
         * - the username is taken
         * - another user has this email
         * - the password is less than 8 characters
         */

        if (
            existingUsername.size() != 0
            || existingUserEmail.size() != 0
            || password.length() < 8
        ) {
            return null;
        }

        User user = new User(username, emailAddress, fullName, role, password, 0);
        em.persist(user);
        return user;
    }

    @Transactional
    public User getUser(int userid) {
        return em.find(User.class, userid);
    }

    @Transactional
    public User updateUser(
        String username, 
        String emailAddress, 
        String fullName, 
        String role, 
        String oldPassword,
        String newPassword
    ) {
        
        if (authenticateUser(username, oldPassword) == -1) {
            return null;
        } else {
            List<User> userList = findUser(username);
            User user = userList.get(0);
            List<User> existingUserEmail = findUserByEmail(emailAddress);
    
            if (existingUserEmail.size() == 0) {
                user.setEmailAddress(emailAddress);
            }
            user.setFullName(fullName);
            user.setRole(role);
            user.setPassword(newPassword);
            em.merge(user);
            return user;
        }
    }

    // Is the user logged in?
    @Transactional
    public int authenticateUser(String username, String password) {
        return authorizeUser(username, password, "");
    }

    // Is the user logged in and does the user have the right privileges?
    // If you pass an empty string, it's the same thing as authenticateUser
    @Transactional
    public int authorizeUser(String username, String password, String role) {
        List<User> userList = findUser(username);
        if (userList.size() != 1) {
            return -1;
        }
        User user = userList.get(0);
        if (
            user.getPassword().equals(password)
            && (role == "" || user.getRole().equalsIgnoreCase(role))
        ) {
            return user.getUserId();
        } else {
            return -1;
        }
    }

    @Transactional
    public List<User> findUser(String username, String emailAddress, String name) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(username))
            .filter(u -> u.getEmailAddress().toUpperCase().equalsIgnoreCase(emailAddress.toUpperCase()))
            .filter(u -> u.getFullName().toUpperCase().equalsIgnoreCase(name.toUpperCase()))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<User> findUser(String username, String emailAddress) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(username))
            .filter(u -> u.getEmailAddress().equalsIgnoreCase(emailAddress))
            .collect(Collectors.toList());
    }

    @Transactional
    public List<User> findUser(String username) {
        List<User> userlist = em.createNamedQuery("User.findAll").getResultList();

        return userlist.stream().filter(u -> u.getUsername().equalsIgnoreCase(username))
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
        // Check if user is admin
        if (authorizeUser(username, password, "Administrator") == -1) {
            return new ArrayList<User>();
        } else {
            return em.createNamedQuery("User.findAll").getResultList();
        }
    }

    @Transactional
    public List getFilteredUserList(String username, String password) {
        List<User> userList = getUnfilteredUserList(username, password);
        Collections.sort(userList, Comparator.comparing(User::getTripNumber));
        if (userList.size() > 99) {
            return userList.subList(0, 99);
        }
        return userList;
    }
}
