package ecse321.t08.rideshare.utility;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class rideshareHelperTests {
    @Test
    public void tokenizer() {
        // Given
        String testString = "My very long string";
        String separator = " ";
        String[] testStringArray = {"My", "very", "long", "string"};
        ArrayList<String> testStringAnswer = new ArrayList<>(Arrays.asList(testStringArray));
        // When
        ArrayList<String> result = rideshareHelper.tokenizer(testString, separator);
        // Then
        assertEquals(result, testStringAnswer);
    }

    @Test
    public void concatenator() {
        // Given
        String[] testStringArray = {"little", "much", "testing", "yay"};
        ArrayList<String> stringArrayList = new ArrayList<>(Arrays.asList(testStringArray));
        String separator = "_+";
        String answer = "little_+much_+testing_+yay";
        // When
        String result = rideshareHelper.concatenator(stringArrayList, separator);
        // Then
        assertEquals(answer, result);
    }
}
