// ### Задача 2.4: Подсчет символов в строке
// Напишите метод `countChars(String s)`, который возвращает `Map<Character, Integer>` - карту, где ключ - символ, значение - количество его вхождений в строку.

// **Пример:**
// countChars("hello") // должно вернуть Map с {'h':1, 'e':1, 'l':2, 'o':1}

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        System.out.println(countChars("hello"));
    }

    public static Map<Character, Integer> countChars(String str){
        Map<Character, Integer> map = new HashMap<>();
        for (int i=0; i< str.length(); i++){
            char ch = str.charAt(i);
            map.put(ch, map.getOrDefault(ch,0) + 1);
        }
        return map;
    }
}