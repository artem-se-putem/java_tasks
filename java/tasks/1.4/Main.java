// ### Задача 1.4: Факториал
// Напишите метод `factorial(int n)`, который вычисляет факториал числа n (n! = 1 * 2 * 3 * ... * n).

// **Пример:**
// factorial(5) // должно вернуть 120
// factorial(0) // должно вернуть 1



public class Main {
    public static void main(String[] args){
        System.out.println(factorial(3));
    }

    public static int factorial(int a){
        int x = 1;
        for (int i = 1; i <= a; i++){
            x *= i;
        }
        return x;
    }
}