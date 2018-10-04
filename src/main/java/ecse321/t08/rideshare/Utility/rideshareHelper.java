package ecse321.t08.rideshare.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class rideshareHelper{
  //method returns a arraylist. To find an element use list.get(). to insert an element at the end use list.add()
  public static ArrayList Tokenizer(String str, String separator){
    StringTokenizer st = new StringTokenizer(str, separator);
    ArrayList<String> tokenlist = new ArrayList<String>();
    while(st.hasMoreTokens()){
      tokenlist.add(st.nextToken());
    }
    return tokenlist;
  }

  //method returns a string. Put the separator will separate each element of the array in the string
  public static String Concatenator(List<String> list, String separator){
    String concatestring = list.get(0);
    String token = "";
    for(int i = 1; i < list.size(); i++){
      token = list.get(i);
      concatestring = concatestring + separator + token;
    }

    return concatestring;
  }
}
