package courses.in28min.funcprogramming.exercises.ex_04_func;

import java.util.List;
import java.util.function.BinaryOperator;

public class FPEx12DoReduceFunction {
    public static void main(String[] args) {
        BinaryOperator<Integer> sum = (x,y) -> x+y;
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);
        
        int result = numbers.stream()
                .reduce(0, sum);

        System.out.println(result);
    }
}
