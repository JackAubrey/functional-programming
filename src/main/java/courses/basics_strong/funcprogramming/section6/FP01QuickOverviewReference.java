package courses.basics_strong.funcprogramming.section6;

import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class FP01QuickOverviewReference {
    public static void main(String[] args) {

        new FP01QuickOverviewReference().test();

    }

    void test() {
        IntPredicate myLambda = (b) -> b < 50;

        List<Integer> result = IntStream.rangeClosed(0, 100)
                .filter(myLambda)
                .filter(i -> i > 10)
                .filter(this::isEven)
                .map(this::square)
                .mapToObj(Integer::valueOf)
                .toList();

        System.out.println(result);
    }

    boolean isEven(int i) {
        return i % 2 == 0;
    }

    int square(int x) {
        return x * 2;
    }
}
