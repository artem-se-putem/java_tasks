// ### Задача 2.5: Проверка палиндрома
// Напишите метод `isPalindrome(String s)`, который проверяет, является ли строка палиндромом (читается одинаково слева направо и справа налево). Игнорируйте регистр и пробелы.

// **Пример:**
// isPalindrome("racecar") // должно вернуть true
// isPalindrome("hello") // должно вернуть false
// isPalindrome("A man a plan a canal Panama") // должно вернуть true

public class Main {
    public static void main(String[] args){
        System.out.println(isPalindrome("racecar"));
        System.out.println(isPalindrome("hello"));
        System.out.println(isPalindrome("A man a plan a canal Panama"));
    }

    public static boolean isPalindrome(String s){
        for (int i=0; i<s.length(); i++){
            if (s.toLowerCase().charAt(i) != s.toLowerCase().charAt(s.length()-1-i)){
                System.out.println(s.toLowerCase().charAt(i));
                System.out.println(s.toLowerCase().charAt(s.length()-1-i));
                return false;
            }
        }
        return true;
    }
}