package courses.basics_strong.funcprogramming.section5;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public class Fp08BinaryOperator {
    public static void main(String[] args) {
        // BinaryOperator interface abstract method signature is: "T apply(T t, T u)"
        // BinaryOperator use "apply" to apply some logic to T and T and return a value of same T type.

        // As you can see, the BinaryOperator is a specialization of BiFunction
        // where all the 3 BiFunction's types are all the same.
        // BinaryOperator restrict a BiFunction to manage only one type input data and same type for the return
        BinaryOperator<Integer> binaryOperator = (x,y) -> x * y;
        BiFunction<Integer, Integer, Integer> biFunction = (x, y) -> x * y;


        System.out.println( "BinaryOperator result: " + apply(2, 10, binaryOperator) );
        System.out.println( "BiFunction result: " + apply(2, 10, biFunction) );
    }

    static <T> T apply(T a, T b, BiFunction<T,T,T> biFunction) {
        return biFunction.apply(a, b);
    }
}
