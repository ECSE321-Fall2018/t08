package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class rideshareUserAdvancedTests  {
    private static final String findall = "User.findAll";
    private static final String findUser = "User.findUsername";

    private static final String USER_KEY = "username";
    private static final int USER_ID = -11;
    private static final String USER_EMAIL = "testemail@testemail.com";
    private static final String USER_FULLNAME = "testuserfullname";
    private static final String USER_FULLNAME_UPDATED = "testuserfullnameupdated";
    private static final String NONEXISTING_USER_KEY = "nonusername";
    private static final String USER_ROLE = "Passenger";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NON_PASSWORD = "passcode";
    private static final String ADMIN_USERNAME = "admintest";
    private static final String ADMIN_PASSWORD = "adminpass";
    private static final boolean USER_STATUS = false;

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    @Mock
    UserRepository userDao;

    @InjectMocks
    UserController userController;

    // it looks like you are testing user controller so move the userDao part under ecse321.t08.rideshare.repository

    @Before
    public void setMockOutput() {
        when(userDao.updateUser(anyString(), anyString(), anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(2).equals(USER_FULLNAME_UPDATED)) {
                User user = new User();
                user.setUsername(USER_KEY);
                user.setFullName(USER_FULLNAME_UPDATED);
                user.setEmailAddress(USER_EMAIL);
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
                user.setUsername(USER_KEY);
                user.setFullName(USER_FULLNAME);
                user.setEmailAddress(USER_EMAIL);
                user.setStatus(USER_STATUS);
                user.setPassword(USER_PASSWORD);
                userList.add(user);
                return userList;
            } else {
                return new ArrayList<User>();
            }
        });
        when(userDao.authenticateUser(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(USER_KEY) && invocation.getArgument(1).equals(USER_PASSWORD)) {
                return USER_ID;
            } else {
                return -1;
            }
        });
        when(userDao.getUnfilteredUserList(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ADMIN_USERNAME) && invocation.getArgument(1).equals(ADMIN_PASSWORD)) {
                User user = new User();
                List<User> userList = new ArrayList<User>();
                user.setUsername(USER_KEY);
                userList.add(user);
                return userList;
            } else {
                return new ArrayList<User>();
            }
        });
    }


    @Test
    public void testAdvancedUserQuery() {
        List<User> userList = userController.findUser(USER_KEY, USER_EMAIL, USER_FULLNAME);

        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals(USER_FULLNAME, userList.get(0).getFullName());
    }

    @Test
    public void testAdvancedUserQueryNotFound() {
        List<User> userList = userController.findUser(NONEXISTING_USER_KEY, USER_EMAIL, USER_FULLNAME);

        assertTrue(userList.isEmpty());
    }

    @Test
    public void testAdvancedUserUpdateQuery() {
        User user = userController.updateUser(USER_KEY, USER_EMAIL, USER_FULLNAME_UPDATED, USER_ROLE, USER_PASSWORD);

        assertEquals(USER_FULLNAME_UPDATED, user.getFullName());
    }

    @Test
    public void testAdvancedUserUpdateQueryNotFound() {
        User user = userController.updateUser(NONEXISTING_USER_KEY, USER_EMAIL, USER_FULLNAME, USER_ROLE, USER_PASSWORD);

        assertNull(user);
    }

    @Test
    public void testAuthenticateUser() {
        int result = userController.authenticateUser(USER_KEY, USER_PASSWORD);

        assertEquals(USER_ID, result);
    }

    @Test
    public void testAuthenticateUserFails() {
        int result = userController.authenticateUser(USER_KEY, USER_NON_PASSWORD);

        assertEquals(-1, result);
    }

    @Test
    public void testUserCreatePasswordIncorrectLength() {
        String result = userController.createUser(USER_KEY, USER_STATUS, USER_EMAIL, USER_FULLNAME, USER_ROLE, "test");
        String expectedResult = USER_ROLE+ " " + USER_KEY + " could not be created, select a new username and make sure your email has not been used before.";

        assertEquals(expectedResult, result);
    }

    @Test
    public void getUnfilteredUserList() {
        List<User> result = userController.getUnfilteredUserList(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals(result.get(0).getUsername(), USER_KEY);
    }

    @Test
    public void getUnfilteredUserListUnsuccessful() {
        List<User> result = userController.getUnfilteredUserList(ADMIN_USERNAME, USER_PASSWORD);
        assertTrue(result.isEmpty());
    }
}