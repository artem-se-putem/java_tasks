// ### Задача 2.1: Максимум в массиве
// Напишите метод `maxInArray(int[] arr)`, который возвращает максимальный элемент в массиве. Если массив пустой, верните `Integer.MIN_VALUE`.

// **Пример:**
// maxInArray(new int[]{3, 7, 2, 9, 1}) // должно вернуть 9
// maxInArray(new int[]{-5, -2, -10}) // должно вернуть -2


public class Main {
    public static void main(String[] args){
        System.out.println(maxInArray(new int[]{3, 7, 2, 9, 1}));
    }

    public static int maxInArray(int[] arr){
        int result=arr[0];
        for (int i=1; i<arr.length; i++){
            if (arr[i] > result)
                result = arr[i];
        }
        return result;
    }
}