package t08.ecse321.driverrideshare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
@return a arraylist
list.get() --> to find an element
list.add() --> to insert an element at the end
 */
public class ConcatToken {
    public static ArrayList<String> tokenizer(String str, String separator) {
        return new ArrayList<>(Arrays.asList(str.split(separator)));
    }

    /*
    @param separate each element of the string list
    @return a string
     */
    public static String concatenator(List<String> list, String separator) {
        return String.join(separator, list);
    }
}
