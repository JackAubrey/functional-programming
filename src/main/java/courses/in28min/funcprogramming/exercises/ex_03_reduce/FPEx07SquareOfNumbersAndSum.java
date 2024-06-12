package courses.in28min.funcprogramming.exercises.ex_03_reduce;

import java.util.List;

public class FPEx07SquareOfNumbersAndSum {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        int res = squareAndSum(numbers);
        System.out.println(res);
    }

    private static Integer squareAndSum(List<Integer> numbers) {
        return numbers.stream()
                .map(n -> n * n)
                .reduce(0, Integer::sum);
    }
}
