package ecse321.t08.rideshare;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import ecse321.t08.rideshare.Utility.rideshareHelper;



@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class rideshareHelperTests {


    private static final String StringToToken = "This;is;a;test";
    private static final String[] stringTokened = {"This", "is", "a", "test"};
    private static final String StringToTokenFail = "This/is/a/test";
    private static final String StringConcatFail = "Thisisatest";


    @Test
    public void testToken() {
        System.out.println("Testing Tokenizer");
        List<String> list = rideshareHelper.Tokenizer(StringToToken, ";");
        int index = 0;
        for(String l: list) {
            assertEquals(stringTokened[index], l);
            index++;
        }
    }

    @Test
    public void testConcat() {
        System.out.println("Testing Concatenator");
        assertEquals(StringToToken, rideshareHelper.Concatenator(Arrays.asList(stringTokened), ";"));
    }

    @Test
    public void testTokenFail() {
        System.out.println("Testing Tokenizer Failure");
        List<String> list = rideshareHelper.Tokenizer(StringToTokenFail, ";");
        int index = 0;
        for(String l: list) {
            assertNotEquals(stringTokened[index], l);
            index++;
        }
    }

    @Test
    public void testConcatFail() {
        System.out.println("Testing Concatenator Failure");
        assertNotEquals(StringConcatFail, rideshareHelper.Concatenator(Arrays.asList(stringTokened), ";"));
    }

}

