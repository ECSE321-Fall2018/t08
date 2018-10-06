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
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class rideshareUserBasicTests {

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
		when(userDao.getUser(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if(invocation.getArgument(0).equals(USER_ID)) {
				User user = new User();
				user.setUserID(USER_ID);
				user.setUsername(USER_KEY);
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
		assertEquals(userController.getUser(USER_ID).getUsername(), USER_KEY);
	}


	@Test
	public void testUserQueryNotFound() {
		assertNull(userController.getUser(NONEXISTING_USER_ID));
	}
}
