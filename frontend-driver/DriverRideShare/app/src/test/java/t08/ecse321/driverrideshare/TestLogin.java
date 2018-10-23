package t08.ecse321.driverrideshare;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestLogin {
    @Mock
    Login activity = new Login();


    final String USERNAME = "testuser";
    final String PASSWORD = "password";
    final String FAKEPASS = "fakepass";



    @Before
    public void setMockOutput() {
        when(activity.login(anyString(), anyString()))
                .thenAnswer((InvocationOnMock invocation) -> {
                    if (invocation.getArgument(0).equals(USERNAME) && invocation.getArgument(1).equals(PASSWORD)) {
                        return true;
                    } else {
                        return false;
                    }
                });
    }

    @Test
    public void testLoginSuccess(){
        boolean success = activity.login(USERNAME, PASSWORD);
        assertTrue(success);
    }

    @Test
    public void testLoginFailure(){
        boolean success = activity.login(USERNAME, FAKEPASS);
        assertFalse(success);
    }
}
