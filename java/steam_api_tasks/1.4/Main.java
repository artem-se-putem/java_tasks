import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(){
        List<String> numbers = Arrays.asList("apple", "pie", "banana", "cat"); 
        List<String> result = numbers.stream()
            .sorted(Comparator.reverseOrder())
            .collect(Collectors.toList());
        System.out.println(result);
    }
}
