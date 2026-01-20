// ### Задача 4.1: Проверка на простое число
// Напишите метод `isPrime(int n)`, который проверяет, является ли число простым.

// **Пример:**
// isPrime(7) // должно вернуть true
// isPrime(10) // должно вернуть false
// isPrime(1) // должно вернуть false


public class Main {
    public static void main(String[] args){
        System.out.print(isPrime(7)); // должно вернуть true
        System.out.print(isPrime(10)); // должно вернуть false
        System.out.print(isPrime(1)); // должно вернуть false
    }

    public static boolean isPrime(int n){
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
