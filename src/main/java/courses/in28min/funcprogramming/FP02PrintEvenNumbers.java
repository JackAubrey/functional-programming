package courses.in28min.funcprogramming;

import java.util.List;

public class FP02PrintEvenNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Print even numbers using Structured Code");
        printEvenListNumberStructured(numbers);

        System.out.println("Print even numbers using Functional Code");
        printEvenListNumberFunctional(numbers);
    }

    private static void printEvenListNumberStructured(List<Integer> numbers) {
        for(int number:numbers) {
            if(isEven(number)) {
                printNumber(number);
            }
        }
    }

    private static void printEvenListNumberFunctional(List<Integer> numbers) {
        numbers.stream()
                .filter(FP02PrintEvenNumbers::isEven)
                .forEach(FP02PrintEvenNumbers::printNumber);
    }

    private static void printNumber(int number) {
        System.out.println(number);
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }
}
