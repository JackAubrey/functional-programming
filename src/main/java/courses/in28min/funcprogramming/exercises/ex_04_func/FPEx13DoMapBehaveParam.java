package courses.in28min.funcprogramming.exercises.ex_04_func;

import java.util.List;
import java.util.function.Function;

public class FPEx13DoMapBehaveParam {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        List<Integer> squaredNumbers = mapAsNewList(numbers, x -> x * x);
        List<Integer> squaredProgNumbers = mapAsNewList(numbers, new PowFunction(2));
        List<Integer> cubedNumbers = mapAsNewList(numbers, x -> x * x * x);
        List<Integer> cubedProgNumbers = mapAsNewList(numbers, new PowFunction(3));
        List<Integer> doubledNumbers = mapAsNewList(numbers, x -> x+x);

        System.out.println("List of squared numbers using lambda: "+squaredNumbers);
        System.out.println("List of squared numbers using PwFunction class: "+squaredProgNumbers);
        System.out.println("List of cubed numbers using lambda: "+cubedNumbers);
        System.out.println("List of cubed numbers using PwFunction class: "+cubedProgNumbers);
        System.out.println("List of doubled numbers using lambda: "+doubledNumbers);
    }

    private static List<Integer> mapAsNewList(List<Integer> numbers, Function<Integer, Integer> function) {
        return numbers.stream()
                .map(function)
                .toList();
    }

    record PowFunction(int pow) implements Function<Integer, Integer> {

        @Override
            public Integer apply(Integer integer) {
                int res = 1;

                for (int i = 0; i < pow; i++) {
                    res = res * integer;
                }
                return res;
            }
        }
}
