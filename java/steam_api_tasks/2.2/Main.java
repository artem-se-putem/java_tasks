import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(){
        List<String> numbers = Arrays.asList("abcd", "qweqwewq", "ewq"); 
        List<String> result = numbers.stream()
            .filter(x -> (x.length()>4))
            .map(x -> x.toUpperCase())
            .collect(Collectors.toList());
        System.out.println(result);
    }
}
