package courses.basics_strong.funcprogramming.section5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FP07BiFunction {
    public static void main(String[] args) {
        // BiFunction interface abstract method signature is: "R apply(T t, U u)"
        // BiFunction use "apply" to apply some logic to T and U and return a value of type R.
        BiFunction<String, String, String> append = (a,b) -> a+b;
        BiFunction<Integer, Integer, Integer> sum = (a,b) -> a+b;
        BiFunction<String, String, Integer> appendLength = (a,b) -> (a+b).length();
        BiFunction<String, Integer, String> appendNum = (a,b) -> a+b;

        System.out.println( apply("Basic", "Strong", append) );
        System.out.println( apply( 10, 20, sum) );
        System.out.println( apply("Basic", "Strong", appendLength) );
        System.out.println( apply("Basic", 10, appendNum) );
    }

    private static <T,U,R> R apply(T a, U b, BiFunction<T, U, R> biFunction) {
        return biFunction.apply(a, b);
    }
}
