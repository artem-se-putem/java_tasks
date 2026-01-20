// ### Задача 1.1: Сумма двух чисел
// Напишите метод `sum(int a, int b)`, который возвращает сумму двух целых чисел.

// **Пример:**
// sum(5, 3) // должно вернуть 8
// sum(-1, 1) // должно вернуть 0

public class Main {
    public static void main(String[] args){
        System.out.println(sum(5,3));
    }

    public static int sum(int a, int b){
        return a+b;
    }
}