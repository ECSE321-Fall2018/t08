package ecse321.t08.rideshare.utility;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class rideshareCryptTests {
    @Test
    public void testPasswordMatch() throws Exception {
        final String PASSWORD = "password123$%&";
        String hashedPass = Crypt.getSaltedHash(PASSWORD);
        assertTrue(Crypt.check(PASSWORD, hashedPass));
    }

    @Test
    public void testPasswordNotMatch() throws Exception {
        final String PASSWORD = "password123$%&";
        final String PASSWORD_2 = "password123$ %&";
        String hashedPass = Crypt.getSaltedHash(PASSWORD);
        assertTrue(!Crypt.check(PASSWORD_2, hashedPass));

    }
}