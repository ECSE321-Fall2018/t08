package ecse321.t08.rideshare.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Helper {
    // Takes a string and splits it based on semicolons in the string
    // The split-up strings are put into an ArrayList and returned
    // To find an element use Helper.tokenizer.get()
    // To insert an element at the end use Helper.tokenizer.add()
    public static ArrayList<String> tokenizer(String str, String separator) {
        return new ArrayList<>(Arrays.asList(str.split(separator)));
    }

    // Joins the List elements into one string, separating the elements with semicolons
    public static String concatenator(List<String> list, String separator) {
        return String.join(separator, list);
    }
}
