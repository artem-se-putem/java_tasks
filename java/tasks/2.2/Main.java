// ### Задача 2.2: Реверс массива
// Напишите метод `reverseArray(int[] arr)`, который возвращает новый массив с элементами в обратном порядке.

// **Пример:**
// reverseArray(new int[]{1, 2, 3, 4, 5}) // должно вернуть new int[]{5, 4, 3, 2, 1}

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        System.out.println(Arrays.toString(reverseArray(new int[]{1, 2, 3, 4, 5})));
    }

    public static int[] reverseArray(int[] arr){
        int[] reversed = new int[arr.length];
        for (int i=0; i< arr.length; i++){
            reversed[i] = arr[arr.length - 1 - i];
        }

        return reversed;
    }
}