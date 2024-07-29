package courses.basics_strong.funcprogramming.section8.techniques;

import net.datafaker.Faker;

import java.util.function.Function;

public class Technique04_Currying {
    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> func1 = u -> {
            return v -> u + v;
        };

        Function<Integer, Function<Integer, Integer>> func1ShortVer = u -> v -> u + v;

        // When we invoke the "func1" function, it returns the function returned by its body.
        Function<Integer, Integer> func1Return = func1.apply(1);

        // now invoking the apply method on the returned function will get the final result.
        Integer sum = func1Return.apply(2);
        System.out.println(sum);

        // we can also do this.
        // instead of use a BiFunction and provide the values together
        // we can use this technique to provide value one by one.
        // this could be useful when we have more than two values or complex situations.
        Integer sum2 = func1.apply(2).apply(3);
        System.out.println(sum2);

        // Also is very very very useful because after the first invocation
        // we can invoke the second one with other parameters without set again and again the first one.
        // Let's take an example:
        //
        Function<Integer, Integer> func2 = func1.apply(10); // like: given 10
        System.out.println( func2.apply(10) ); // sum it with 10
        System.out.println( func2.apply(3) ); // sum it with 3
        System.out.println( func2.apply(7) ); // sum it with 7

        // another example
        Function<Double, Function<Double, Function<Double, Double>>> multiply = x -> y -> z-> x * y * z;
        Function<Double, Function<Double, Double>> funcX = multiply.apply(10.0);
        Function<Double, Double> funcXY1 = funcX.apply(3.0);
        Function<Double, Double> funcXY2 = funcX.apply(3.5);
        Function<Double, Double> funcXY3 = funcX.apply(4.2);

        System.out.println( funcXY1.apply(7.0) );
        System.out.println( funcXY1.apply(2.0) );
        System.out.println( funcXY2.apply(4.5) );
        System.out.println( funcXY3.apply(8.2) );
    }

    record MyUser(String firsName, String lastName) {}
}
