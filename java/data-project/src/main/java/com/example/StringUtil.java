package com.example;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {
    
    // Переворачивает строку
    public static String reverse(String s) {
        if (s == null) return null;
        return new StringBuilder(s).reverse().toString();
    }
    
    // Проверяет, является ли строка палиндромом
    public static boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) return false;
        String cleaned = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        return cleaned.equals(reverse(cleaned));
    }
    
    // Возвращает количество слов в строке
    public static int wordCount(String s) {
        if (s == null || s.isBlank()) return 0;
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return 0;
        return trimmed.split("\\s+").length;
    }
    
    // Подсчет букв в строке
    public static Map<Character, Integer> countLetters(String s) {
        Map<Character, Integer> map = new HashMap<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }
        }
        
        return map;
    }
}
