package courses.in28min.funcprogramming.exercises.ex_03_reduce;

import java.util.List;

public class FPEx08CubeOfNumbersAndSum {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        int res = cubeAndSum(numbers);
        System.out.println(res);
    }

    private static Integer cubeAndSum(List<Integer> numbers) {
        return numbers.stream()
                .map(n -> n * n * n)
                .reduce(0, Integer::sum);
    }
}
