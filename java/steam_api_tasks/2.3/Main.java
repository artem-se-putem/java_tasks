import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5); 
        int result = numbers.stream()
            .map(x -> x*x)
            .reduce(0,(a,b) -> a+b);
            // .collect(Collectors.toList());
        System.out.println(result);
    }
}
