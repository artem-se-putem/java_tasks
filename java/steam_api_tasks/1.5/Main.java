import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(){
        List<Integer> numbers = Arrays.asList(1,1,2,3,4); 
        List<Integer> result = numbers.stream()
            .distinct()
            .collect(Collectors.toList());
        System.out.println(result);
    }
}
