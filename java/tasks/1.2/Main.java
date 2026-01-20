// ### Задача 1.2: Максимум из двух чисел
// Напишите метод `max(int a, int b)`, который возвращает большее из двух чисел.

// **Пример:**
// max(5, 3) // должно вернуть 5
// max(-1, 1) // должно вернуть 1


public class Main {
    public static void main(String[] args){
        System.out.println(max(5,3));
    }

    public static int max(int a, int b){
        return Math.max(a,b);
    }
}