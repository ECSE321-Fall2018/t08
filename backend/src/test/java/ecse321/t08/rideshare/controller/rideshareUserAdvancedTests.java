package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.User;
import ecse321.t08.rideshare.repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class rideshareUserAdvancedTests {
    private static final String findall = "User.findAll";
    private static final String findUser = "User.findUsername";

    private static final String USER_KEY = "username";
    private static final String USER_KEY2 = "username2";
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
    UserRepository userDao;

    @InjectMocks
    UserController userController;

    // it looks like you are testing user controller so move the userDao part under ecse321.t08.rideshare.repository

    @Before
    public void setMockOutput() {
        when(userDao.updateUser(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
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
        when(userDao.findUser(anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(2).equals(USER_KEY)) {
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
                return new ArrayList();

            }
        });
        when(userDao.authenticateUser(anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(USER_KEY)
                && invocation.getArgument(1).equals(USER_PASSWORD)
            ) {
                return USER_ID;
            } else {
                return -1;

            }
        });
        when(userDao.login(anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (
                            invocation.getArgument(0).equals(USER_KEY)
                                    && invocation.getArgument(1).equals(USER_PASSWORD)
                    ) {
                        return USER_ROLE;
                    } else {
                        return "";
                    }
                });
        when(userDao.authorizeUser(anyString(), anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (
                            invocation.getArgument(0).equals(USER_KEY)
                                    && invocation.getArgument(1).equals(USER_PASSWORD)
                                    && invocation.getArgument(2).equals(USER_ROLE)
                    ) {
                        return USER_ID;
                    } else {
                        return -1;
                    }
                });
        when(userDao.getUnfilteredUserList(anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(ADMIN_USERNAME)
                && invocation.getArgument(1).equals(ADMIN_PASSWORD)
            ) {
                User user = new User();
                List<User> userList = new ArrayList<User>();
                user.setUsername(USER_KEY);
                userList.add(user);
                return userList;
            } else {
                return new ArrayList();

            }
        });
        when(userDao.getFilteredUserList(anyString(), anyString()))
        .thenAnswer((InvocationOnMock invocation) -> {
            if (
                invocation.getArgument(0).equals(ADMIN_USERNAME)
                && invocation.getArgument(1).equals(ADMIN_PASSWORD)
            ) {
                User user = new User();
                List<User> userList = new ArrayList<User>();
                user.setUsername(USER_KEY);
                user.setTripnumber(2);
                User user2 = new User();
                user2.setUsername(USER_KEY2);
                user2.setTripnumber(1);
                userList.add(user);
                userList.add(user2);
                Collections.sort(userList, Comparator.comparing(User::getTripnumber));
                return userList;
            } else {
                return new ArrayList();
            }
        });
    }

    @Test
    public void testAdvancedUserQuery() {
        ResponseEntity<?> response = userController.findUser(ADMIN_USERNAME, ADMIN_PASSWORD, USER_KEY, USER_EMAIL, USER_FULLNAME);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAdvancedUserQueryNotFound() {
        ResponseEntity<?> response = userController.findUser(ADMIN_USERNAME, ADMIN_PASSWORD, USER_KEY2, USER_EMAIL, USER_FULLNAME);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void testAdvancedUserUpdateQuery() {
        ResponseEntity<?> response = userController.updateUser(
            USER_KEY, 
            USER_EMAIL, 
            USER_FULLNAME_UPDATED, 
            USER_ROLE, 
            USER_PASSWORD,
            USER_PASSWORD
        );

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testAdvancedUserUpdateQueryNotFound() {
        ResponseEntity<?> response = userController.updateUser(
            NONEXISTING_USER_KEY, 
            USER_EMAIL, 
            USER_FULLNAME, 
            USER_ROLE, 
            USER_PASSWORD,
            USER_PASSWORD
        );

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void testAuthenticateUser() {
        ResponseEntity<?> response = userController.authenticateUser(USER_KEY, USER_PASSWORD);
        JSONObject json = new JSONObject();
        try {
            json.put("data", USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(json.toString(), response.getBody().toString());
    }

    @Test
    public void testAuthenticateUserFails() {
        ResponseEntity<?> response= userController.authenticateUser(USER_KEY, USER_NON_PASSWORD);
        JSONObject json = new JSONObject();
        try {
            json.put("data", -1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(json.toString(),  response.getBody().toString());
    }

    @Test
    public void testAuthorizeUser() {
        ResponseEntity<?> response = userController.authorize(USER_KEY, USER_PASSWORD, USER_ROLE);

        JSONObject json = new JSONObject();
        try {
            json.put("data", USER_ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(json.toString(), response.getBody().toString());
    }

    @Test
    public void testAuthorizeUserFails() {
        ResponseEntity<?> response= userController.authorize(USER_KEY, USER_NON_PASSWORD, USER_ROLE);

        JSONObject json = new JSONObject();
        try {
            json.put("data", -1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(json.toString(),  response.getBody().toString());
    }

    @Test
    public void testLoginUser() {
        ResponseEntity<?> response = userController.login(USER_KEY, USER_PASSWORD);

        JSONObject json = new JSONObject();
        try {
            json.put("data", USER_ROLE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(json.toString(), response.getBody().toString());
    }

    @Test
    public void testLoginUserFails() {
        ResponseEntity<?> response = userController.login(USER_KEY, USER_NON_PASSWORD);

        JSONObject json = new JSONObject();
        try {
            json.put("data", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertEquals(json.toString(), response.getBody().toString());
    }

    @Test
    public void testUserCreatePasswordIncorrectLength() {
        ResponseEntity<?> result = userController.createUser(
            USER_KEY,
            USER_EMAIL, 
            USER_FULLNAME, 
            USER_ROLE, "test"
        );

        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    public void getUnfilteredUserList() {
        ResponseEntity<?> response = userController.getUnfilteredUserList(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getUnfilteredUserListUnsuccessful() {
        ResponseEntity<?> response = userController.getUnfilteredUserList(ADMIN_USERNAME, USER_PASSWORD);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getFilteredUserList() {
        ResponseEntity<?> response = userController.getFilteredUserList(ADMIN_USERNAME, ADMIN_PASSWORD);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getUnFilteredUserListUnsuccessful() {
        ResponseEntity<?> response = userController.getFilteredUserList(ADMIN_USERNAME, USER_PASSWORD);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}