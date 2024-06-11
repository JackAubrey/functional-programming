package courses.in28min.funcprogramming.exercises;

import java.util.List;

public class FPEx01OddNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Print Odd numbers using Functional Code");
        printOddNumbers(numbers);
    }

    private static void printOddNumbers(List<Integer> numbers) {
        numbers.stream()
                .filter(n -> n%2 != 0)
                .forEach(System.out::println);
    }
}
