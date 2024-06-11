package courses.in28min.funcprogramming;

import java.util.List;

public class FP03SumAllNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Sum all numbers using Structured Code: " + sumAllNumbersStructured(numbers));

        System.out.println("Sum all numbers using Functional Code: " + sumAllNumbersFunctional(numbers));
    }

    private static int sumAllNumbersStructured(List<Integer> numbers) {
        int total = 0;

        for(int number:numbers) {
            total += number;
        }

        return total;
    }

    private static int sumAllNumbersFunctional(List<Integer> numbers) {
        return numbers.stream()
                // Combine them into one result => One value
                // 0 and (a,b) -> a+b
                // 0 is the initial value
                // (a,b) is the binary function (accumulation function) used to reduce list in a single value
                .reduce(0, (a,b) -> a+b);

    }
}
