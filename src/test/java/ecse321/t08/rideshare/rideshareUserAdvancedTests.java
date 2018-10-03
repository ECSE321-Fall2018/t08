package ecse321.t08.rideshare;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ecse321.t08.rideshare.Controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;
import ecse321.t08.rideshare.Repository.UserRepository;
import ecse321.t08.rideshare.Entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class rideshareUserAdvancedTests  {
    private static final String findall = "User.findAll";
    private static final String findUser = "User.findUsername";

    private static final String USER_KEY = "username";
    private static final String USER_EMAIL = "testemail@testemail.com";
    private static final String USER_FULLNAME = "testuserfullname";
    private static final String USER_FULLNAME_UPDATED = "testuserfullnameupdated";
    private static final String NONEXISTING_USER_KEY = "nonusername";
    private static final String USER_PASSWORD = "password";
    private static final boolean USER_STATUS = false;

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    @Mock
    UserRepository userDao;

    @InjectMocks
    UserController userController;

    @Before
    public void setMockOutput() {
        System.out.println("Setting Up Test For User Query Found");
        when(userDao.updateUser(anyString(), anyBoolean(), anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(3).equals(USER_FULLNAME_UPDATED)) {
                User user = new User();
                user.setUserName(USER_KEY);
                user.setFullName(USER_FULLNAME_UPDATED);
                user.setEmailAddress(USER_EMAIL);
                user.setStatus(USER_STATUS);
                user.setPassword(USER_PASSWORD);
                return user;
            } else {
                return null;
            }
        });
        when(userDao.findUser(anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_KEY)) {
                User user = new User();
                List<User> userList = new ArrayList<User>();
                user.setUserName(USER_KEY);
                user.setFullName(USER_FULLNAME);
                user.setEmailAddress(USER_EMAIL);
                user.setStatus(USER_STATUS);
                user.setPassword(USER_PASSWORD);
                userList.add(user);
                return userList;
            } else {
                return null;
            }
        });
    }


    @Test
    public void testAdvancedUserQuery() throws IllegalAccessException {
        System.out.println("Testing Advanced User Query Found");
        List<User> userList = userDao.findUser(USER_KEY, USER_EMAIL, USER_FULLNAME);
        if(userList == null || userList.size() < 1 || userList.size() > 1 ) {
            throw new IllegalAccessException("Size must be 1 of test DAO.");
        }
        User user = userList.get(0);

        assertEquals(user.getFullName(), USER_FULLNAME);
    }

    @Test
    public void testAdvancedUserQueryNotFound() throws IllegalAccessException {
        System.out.println("Testing Advanced User  Query Not Found");
        List<User> userList = userDao.findUser(NONEXISTING_USER_KEY, USER_EMAIL, USER_FULLNAME);
        if(userList == null || userList.size() < 1 || userList.size() > 1 ) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void testAdvancedUserUpdateQuery() {
        System.out.println("Testing Advanced User Update Query Found");
        User user = userController.updateUser(USER_KEY, USER_STATUS, USER_EMAIL, USER_FULLNAME_UPDATED, USER_PASSWORD);

        assertEquals(user.getFullName(), USER_FULLNAME_UPDATED);
    }

    @Test
    public void testAdvancedUserUpdateQueryNotFound() {
        System.out.println("Testing Advanced User Update Query Not Found");
        User user = userController.updateUser(NONEXISTING_USER_KEY, USER_STATUS, USER_EMAIL, USER_FULLNAME, USER_PASSWORD);
        assertEquals(user, null);
    }

    @Test
    public void testUserCreatePasswordIncorrectLength() {
        System.out.println("Testing Create User With Incorrect Password Length");
        String result = userController.createUser(USER_KEY, USER_STATUS, USER_EMAIL, USER_FULLNAME, "test");
        assertEquals("User " + USER_KEY + " could not be created, select a new username and make sure your email has not been used before.", result);
    }
}