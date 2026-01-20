// ### Задача 1.5: Сумма элементов массива
// Напишите метод `sumArray(int[] arr)`, который возвращает сумму всех элементов массива.

// **Пример:**
// sumArray(new int[]{1, 2, 3, 4, 5}) // должно вернуть 15
// sumArray(new int[]{}) // должно вернуть 0


public class Main {
    public static void main(String[] args){
        System.out.println(sumArray(new int[]{1, 2, 3, 4, 5}));
    }

    public static int sumArray(int[] mass){
        int result=0;
        for (int i=0; i<mass.length; i++){
            result += mass[i];
        }
        return result;
    }
}