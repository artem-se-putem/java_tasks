// ### Задача 1.3: Четное или нечетное
// Напишите метод `isEven(int n)`, который возвращает `true`, если число четное, и `false` если нечетное.

// **Пример:**
// isEven(4) // должно вернуть true
// isEven(5) // должно вернуть false

public class Main {
    public static void main(String[] args){
        System.out.println(isEven(5,3));
    }

    public static boolean isEven(int a, int b){
        if (a%2==0)
            return true;
        else
            return false;
    }
}