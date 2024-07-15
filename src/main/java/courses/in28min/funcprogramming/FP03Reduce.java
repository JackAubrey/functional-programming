package courses.in28min.funcprogramming;

import java.util.List;

public class FP03Reduce {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        // "reduce" a terminal operation
        // Performs a reduction on the elements of this stream, using the provided identity value and an associative accumulation function.

        int result = numbers.stream()
                // "REDUCE" Combine them into one result => One value
                // 0 and (a,b) -> a+b
                // 0 is the initial value
                // (a,b) is the binary function (accumulation function) used to reduce list in a single value
                // "a" is the initial/final value or the partial accumulated value
                // "b" is current stream value
                // use the second "reduce" and comment this one to see how the function works
                .reduce(0, (a,b) -> a+b);
                //.reduce(0, FP03Reduce::sum);

        System.out.println("Result = "+result);
    }

    private static int sum(int aggregated, int nextNumber) {
        System.out.println("a = "+aggregated+" -  B = "+nextNumber);
        return aggregated+nextNumber;
    }
}
