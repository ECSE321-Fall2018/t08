package ecse321.t08.rideshare;

import org.mockito.InjectMocks;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


import ecse321.t08.rideshare.Controller.UserController;
import ecse321.t08.rideshare.Entity.User;
import  ecse321.t08.rideshare.Repository.UserRepository;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class rideshareUserTests {

	@Mock
	private UserRepository userDao;

	@Mock
	private EntityManager entityManager;

	@InjectMocks
	private UserController userController;

	private static final String USER_KEY = "username";
	private static final int USER_ID = -1;
	private static final String USER_EMAIL = "testemail@testemail.com";
	private static final String USER_FULLNAME = "testuserfullname";
	private static final int NONEXISTING_USER_ID = -2;

	@Before
	public void setMockOutput() {
		System.out.println("Setting Up Test For User Query Found");
		when(userDao.getUser(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(USER_ID)) {
				User user = new User();
				user.setUserID(USER_ID);
				user.setUserName(USER_KEY);
				user.setFullName(USER_FULLNAME);
				user.setEmailAddress(USER_EMAIL);
				user.setStatus(false);
				user.setPassword("test");
				return user;
			} else {
				return null;
			}
		});
	}

	@Test
	public void testUserSimpleQueryFound() {
		System.out.println("Testing User Query Found");
		assertEquals(userController.getUser(USER_ID), USER_KEY);
	}


	@Test
	public void testUserQueryNotFound() {
		System.out.println("Testing User Query Not Found");
		assertEquals(userController.getUser(NONEXISTING_USER_ID), "NOT FOUND");
	}
}
