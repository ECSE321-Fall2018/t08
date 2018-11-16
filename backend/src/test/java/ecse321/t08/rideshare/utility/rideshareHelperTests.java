package ecse321.t08.rideshare.utility;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

public class rideshareHelperTests {
    @Test
    public void tokenizer() {
        String testString = "My very long string";
        String separator = " ";
        String[] testStringArray = {"My", "very", "long", "string"};
        ArrayList<String> testStringAnswer = new ArrayList<>(Arrays.asList(testStringArray));

        ArrayList<String> result = rideshareHelper.tokenizer(testString, separator);

        assertEquals(result, testStringAnswer);
    }

    @Test
    public void concatenator() {
        String[] testStringArray = {"little", "much", "testing", "yay"};
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(testStringArray));
        String separator = "_+";
        String answer = "little_+much_+testing_+yay";

        String result = rideshareHelper.concatenator(stringArrayList, separator);

        assertEquals(answer, result);
    }
}