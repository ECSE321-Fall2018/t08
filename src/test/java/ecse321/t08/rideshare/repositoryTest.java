package ecse321.t08.rideshare;

import static org.junit.Assert.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.annotation.Transactional;
import ecse321.t08.rideshare.Repository.UserRepository;
import ecse321.t08.rideshare.Entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RunWith(MockitoJUnitRunner.class)
@Transactional
public class repositoryTest  {
    private static final String findall = "User.findAll";

    private static final String USER_KEY = "username";
    private static final String USER_EMAIL = "testemail@testemail.com";
    private static final String USER_FULLNAME = "testuserfullname";
    private static final String NONEXISTING_USER_KEY = "nonusername";

    @Mock
    EntityManager entityManager;

    @Mock
    Query query;

    @InjectMocks
    UserRepository userDao = new UserRepository();

    @Before
    public void setup() {
       // when(entityManager.find((Class<?>) any(), any())).thenReturn(null);
        userDao.setEntityManager(entityManager);
    }

    @Test
    public void testAdvancedQuery() {
        Query mquery = Mockito.mock(Query.class);
        User testUser = userDao.createUser(USER_KEY,false, USER_EMAIL, USER_FULLNAME, "test");
        System.out.println("Testing User Advanced Query Found");

        List<User> userli= new ArrayList<User>();

        when(entityManager.createNamedQuery(eq(findall))).thenReturn(mquery);
        when(mquery.getResultList()).thenReturn(userli);

        List<User> userList = userDao.findUser(USER_KEY, USER_EMAIL, USER_FULLNAME);
        assertSame(userli, userList);
    }


}
