package ecse321.t08.rideshare.utility;

import java.util.ArrayList;
import java.util.Arrays;

public class rideshareHelper {
    // method returns a arraylist
    // to find an element use list.get()
    // to insert an element at the end use list.add()
    public static ArrayList<String> tokenizer(String str, String separator) {
        return new ArrayList<>(Arrays.asList(str.split(separator)));
    }

    // method returns a string
    // put the separator will separate each element of the array in the string
    public static String concatenator(ArrayList<String> list, String separator) {
        return String.join(separator, list);
    }
}
