package courses.in28min.funcprogramming.exercises;

import java.util.List;

public class FPEx05PowOfOddNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Print cube of odd numbers using Functional Code");
        printCubeOfOddNumbers(numbers, 3);
    }

    private static void printCubeOfOddNumbers(List<Integer> numbers, int pow) {
        numbers.stream()
                .filter(n -> n%2 != 0)
                .map(n -> Math.pow(n, pow))
                .forEach(System.out::println);
    }
}
