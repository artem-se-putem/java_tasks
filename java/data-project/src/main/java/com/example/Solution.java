import java.util.HashMap;
import java.util.Map;

public class Solution {
  public boolean check(String sentence){
    Map<Character,Integer> map = new HashMap<>();
    for (int i=0; i<sentence.length(); i++){
      if (Character.isLetter(sentence.charAt(i))){
        map.put(Character.toLowerCase(sentence.charAt(i)), 1);
      }
    }

    if (map.size() == 26){
        return true;
    }
    return false;
  }
}