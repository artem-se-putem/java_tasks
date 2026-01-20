// ### Задача 2.3: Поиск элемента
// Напишите метод `findIndex(int[] arr, int target)`, который возвращает индекс первого вхождения элемента target в массиве. Если элемент не найден, верните -1.

// **Пример:**
// findIndex(new int[]{1, 2, 3, 4, 5}, 3) // должно вернуть 2
// findIndex(new int[]{1, 2, 3, 4, 5}, 6) // должно вернуть -1


public class Main {
    public static void main(String[] args){
        System.out.println(findIndex(new int[]{1, 2, 3, 4, 5}, 3));
    }

    public static int findIndex(int[] arr, int target){
        for (int i=0; i< arr.length; i++){
            if (arr[i] == target)
                return i;
        }
        return -1;
    }
}