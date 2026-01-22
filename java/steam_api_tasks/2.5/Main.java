import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(){
        // List<String> numbers = Arrays.asList("abcd", "qweqwewq", "ewq"); 
        List<Integer> stream = Stream.iterate(0, n -> n + 2).limit(5)
        .collect(Collectors.toList());
        System.out.println(stream);
    }
}
