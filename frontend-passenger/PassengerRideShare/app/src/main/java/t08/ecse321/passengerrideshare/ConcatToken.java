package t08.ecse321.passengerrideshare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConcatToken {

    /*
    list.get() --> to find an element
    list.add() --> to insert an element at the end
    @return A arraylist
     */
    public static ArrayList<String> tokenizer(String str, String separator) {
        return new ArrayList<>(Arrays.asList(str.split(separator)));
    }

    /*
    Separator will separate each element of the array in the string
    @return A String
     */
    public static String concatenator(List<String> list, String separator) {
        return String.join(separator, list);
    }

}
