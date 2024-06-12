package courses.in28min.funcprogramming.exercises.ex_04_collect;

import java.util.List;

public class FPEx10CollectFilteredEvenNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        filterAndCollect(numbers)
                .forEach(System.out::println);
    }

    private static List<Integer> filterAndCollect(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n%2 == 0)
                .toList();
    }
}
