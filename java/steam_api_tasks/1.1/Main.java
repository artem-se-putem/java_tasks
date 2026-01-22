import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args){
        // Java Stream API - Задача 1.1: Фильтрация четных чисел
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); 
        List<Integer> result = numbers.stream() 
                        .filter(x -> x % 2 == 0)  // оставить только четные
                        .collect(Collectors.toList()); 
        // Ожидаемый результат: [2, 4, 6, 8, 10]
        System.out.println(result);
    }
}
