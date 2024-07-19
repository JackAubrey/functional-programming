package courses.basics_strong.funcprogramming.section5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FP05Function {
    public static void main(String[] args) {
        // Function interface abstract method signature is: "R apply(T t)"
        // Function use "apply" to apply some logic to T and return a value of type R.
        List<String> list = List.of("Basics", "Strong", "java", "functional", "programming", "BasicsStrong");
        Function<String, String> toUppercaseFunc = s -> s.toUpperCase();
        Function<String, Integer> lengthFunc = s -> s.length();

        System.out.println( apply(list, toUppercaseFunc) );
        System.out.println( apply(list, lengthFunc) );
    }

    private static <T, R> List<R> apply(List<T> list, Function<T, R> predicate) {
        List<R> newList = new ArrayList<>();

        for (T s:list) {
            newList.add( predicate.apply(s) );
        }

        return newList;
    }
}
