import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(){
        List<String> numbers = Arrays.asList("hello", "world", "java", "stream"); 
        List<Integer> result = numbers.stream()
            .map(x -> x.length())
            .collect(Collectors.toList());
        System.out.println(result);
    }
}
