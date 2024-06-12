package courses.in28min.funcprogramming.exercises.ex_02_map;

import java.util.List;

public class FPEx05SquareRootOfEvenNumber {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Print the square root of even numbers using Functional Code");
        printNumberSquare(numbers);
    }

    private static void printNumberSquare(List<Integer> numbers) {
        numbers.stream()
                .filter(n -> n%2 == 0)
                .map(Math::sqrt)
                .forEach(System.out::println);
    }
}
