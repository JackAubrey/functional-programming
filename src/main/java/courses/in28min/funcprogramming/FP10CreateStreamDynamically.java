package courses.in28min.funcprogramming;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FP10CreateStreamDynamically {
    public static void main(String[] args) {
        // We are going to create stream dynamically using java library or our own algorithm.

        // 1- EASY WAY TO CREATE A STREAM OF INTEGER
        System.out.println("numbers from 0 to 10 exclusive: "+IntStream.range(0, 10).boxed().toList());
        System.out.println("numbers from 0 to 10 inclusive: "+IntStream.rangeClosed(0, 10).boxed().toList());


        // 2- Generate numbers using our own alg
        //    We have many ways to do so.
        //    Can use "iterate" or "generate" where the main difference are
        //      - "generate" do not take any value in input
        //      - "iterate" wants a seed and take an input value
        // NOTE: if we exclude the "limit" function, this one is a way to generate infinite stream
        // Remember: "limit" is one of circuit-breaker operations
        IntStream.generate(intSupplier).limit(10).forEach(System.out::println);
        IntStream.iterate(1, i -> i+2).limit(10).forEach(System.out::println);
        Stream.generate(stringSupplier).limit(10).forEach(System.out::println);
        Stream.iterate("hello", s -> s+" world").limit(10).forEach(System.out::println);

        // an exercise: calculate the power of 2 and use peek to print
        IntStream.rangeClosed(0, 10)
                .map(i -> (int)Math.pow(2, i))
                .peek(System.out::println)
                .sum();
    }

    static void printPeek(int powedValue) {
        System.out.println("Peeked Value = "+powedValue);
    }

    static IntSupplier intSupplier = () -> {
        return new Random().nextInt();
    };

    static Supplier<String> stringSupplier = () -> {
        return UUID.randomUUID().toString();
    };
}
