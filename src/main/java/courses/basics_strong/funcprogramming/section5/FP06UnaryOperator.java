package courses.basics_strong.funcprogramming.section5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class FP06UnaryOperator {
    public static void main(String[] args) {
        // UnaryOperator remember is a extension of Function whereas T and R has same type "Function<T,T>"
        // We use it when we restrict a function that return a value of the same type of input
        List<String> list = List.of("Basics", "Strong", "java", "functional", "programming", "BasicsStrong");
        List<Integer> listInt = List.of(1,5,8,90,32,45);
        UnaryOperator<String> toUppercaseFunc = s -> s.toUpperCase();
        UnaryOperator<Integer> multiply100 = i -> i * 100;

        System.out.println( apply(list, toUppercaseFunc) );
        System.out.println( apply(listInt, multiply100) );

        // Remember:
        // UnaryOperator<T> extends Function<T,T>
        // so we can use UnaryOperator also whereas a method expect a function
        // like in the "applySpecial" methods example
        System.out.println( applySpecial(list, toUppercaseFunc) );
    }

    private static <T> List<T> apply(List<T> list, UnaryOperator<T> predicate) {
        List<T> newList = new ArrayList<>();

        for (T s:list) {
            newList.add( predicate.apply(s) );
        }

        return newList;
    }

    private static <T, R> List<R> applySpecial(List<T> list, Function<T, R> predicate) {
        List<R> newList = new ArrayList<>();

        for (T s:list) {
            newList.add( predicate.apply(s) );
        }

        return newList;
    }
}
