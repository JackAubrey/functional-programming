package courses.in28min.funcprogramming.exercises.ex_03_reduce;

import java.util.List;

public class FPEx09SumOfOddNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        int res = sumOddNumbers(numbers);
        System.out.println(res);
    }

    private static Integer sumOddNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n%2 != 0)
                .reduce(0, Integer::sum);
    }
}
