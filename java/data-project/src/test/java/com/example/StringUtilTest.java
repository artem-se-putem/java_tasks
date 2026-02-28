package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {
    
    // === Тесты для reverse() ===
    
    @Test
    void testReverse_normalString() {
        assertEquals("cba", StringUtil.reverse("abc"));
    }
    
    @Test
    void testReverse_singleChar() {
        assertEquals("a", StringUtil.reverse("a"));
    }
    
    @Test
    void testReverse_emptyString() {
        assertEquals("", StringUtil.reverse(""));
    }
    
    @Test
    void testReverse_nullInput() {
        assertNull(StringUtil.reverse(null));
    }
    
    // === Тесты для isPalindrome() ===
    
    @Test
    void testIsPalindrome_simpleWord() {
        assertTrue(StringUtil.isPalindrome("racecar"));
    }
    
    @Test
    void testIsPalindrome_notPalindrome() {
        assertFalse(StringUtil.isPalindrome("hello"));
    }
    
    @Test
    void testIsPalindrome_withSpaces() {
        assertTrue(StringUtil.isPalindrome("A man a plan a canal Panama"));
    }
    
    @Test
    void testIsPalindrome_emptyString() {
        assertFalse(StringUtil.isPalindrome(""));
    }
    
    @Test
    void testIsPalindrome_nullInput() {
        assertFalse(StringUtil.isPalindrome(null));
    }
    
    // === Тесты для wordCount() ===
    
    @Test
    void testWordCount_normalSentence() {
        assertEquals(4, StringUtil.wordCount("Hello world from Java"));
    }
    
    @Test
    void testWordCount_singleWord() {
        assertEquals(1, StringUtil.wordCount("Hello"));
    }
    
    @Test
    void testWordCount_multipleSpaces() {
        assertEquals(3, StringUtil.wordCount("Hello   world    Java"));
    }
    
    @Test
    void testWordCount_emptyString() {
        assertEquals(0, StringUtil.wordCount(""));
    }
    
    @Test
    void testWordCount_onlySpaces() {
        assertEquals(0, StringUtil.wordCount("   "));
    }
    
    @Test
    void testWordCount_nullInput() {
        assertEquals(0, StringUtil.wordCount(null));
    }
}
