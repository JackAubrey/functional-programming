package courses.basics_strong.funcprogramming.section5;

import java.util.List;
import java.util.function.Consumer;

public class FP03Consumer {
    public static void main(String[] args) {
        // Consumer interface abstract method signature is: "void accept(T t)"
        // Consumer use "accept" to do something with received value and do not return anything.
        List<String> list = List.of("Basics", "Strong", "java", "functional", "programming", "BasicsStrong");
        List<Integer> listInt = List.of(1,5,8,90,32,45);
        Consumer<String> printUppercaseString = s -> System.out.println(s.toUpperCase());
        Consumer<Integer> printIsOddOrEven = i -> System.out.println( i%2==0 ? i + " is even" : i + " is odd" );

        consume(list, printUppercaseString);
        consume(listInt, printIsOddOrEven);
    }

    private static <T> void consume(List<T> list, Consumer<T> predicate) {
        for (T s:list) {
            predicate.accept(s);
        }
    }
}
