package courses.in28min.funcprogramming;

import java.util.List;

public class FP01PrintAllNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Print numbers using Structured Code");
        printAllListNumberStructured(numbers);

        System.out.println("Print numbers using Functional Code");
        printAllListNumberFunctional(numbers);
    }

    private static void printAllListNumberStructured(List<Integer> numbers) {
        for(int number:numbers) {
            printNumber(number);
        }
    }

    private static void printAllListNumberFunctional(List<Integer> numbers) {
        numbers.stream()
                .forEach(FP01PrintAllNumbers::printNumber);
    }

    private static void printNumber(int number) {
        System.out.println(number);
    }
}
