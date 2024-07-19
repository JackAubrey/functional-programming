package courses.basics_strong.funcprogramming.section5;

import java.util.Random;
import java.util.function.Supplier;

public class FP04Supplier {
    public static void main(String[] args) {
        // Supplier interface abstract method signature is: "T get()"
        // Supplier use "get" to supply a value of type T.
        Supplier<String> supplyString = () -> "hello";
        Supplier<Integer> supplyRandInt = () -> new Random().nextInt(100);

        System.out.println(supplyString.get());
        System.out.println(supplyRandInt.get());
    }
}
