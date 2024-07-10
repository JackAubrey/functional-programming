package courses.in28min.funcprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FP10CreateStream {
    public static void main(String[] args) {
        // Generic way
        //  NOTE:   Stream created from Stream.of(...) and List.of(...).stream() hold the wrapper class for primitive types.
        //          This means when we want to do some operation on stream operation like "reduce"
        //          the primitive data needs to unboxed, and it is inefficient.
        //          There are specific implementation of Stream to prevent this unwanted side effect like:
        //              - Arrays.stream(int [] {......})
        //              - IntStream.of(1,2,3,5,8,13,21)
        //          This consideration is still true for the rest of primitive types: long and double
        Stream<String> streamOfString = Stream.of("Foo", "Bar", "Tizio", "Caio");
        Stream<Integer> streamOfInteger = Stream.of(1,2,3,5,8,13,21);
        int map = Stream.of(1,2,3,5,8,13,21).reduce(0, Integer::max);

        // Create stream from array
        String [] arrayString = {"Foo", "Bar", "Tizio", "Caio"};
        Integer [] arrayInteger = {1,2,3,5,8,13,21};
        int [] arrayInt = {1,2,3,5,8,13,21};

        Stream<String> streamStringFromArray = Arrays.stream(arrayString);
        Stream<Integer> streamIntegerFromArray = Arrays.stream(arrayInteger);
        IntStream intStream = IntStream.of(1,2,3,5,8,13,21);

        // ##############################
        // >>>> AN INTERESTING THING <<<<
        // ##############################
        // Print the different types of streams
        // Look the last two types, are an "IntPipeline"
        // Other cases are "ReferencePipeline" (means the object are wrapped)
        // Since the last two are using an "IntPipeline" we can directly use int function like "min", "max", "sum", etc
        System.out.println("Stream of Integer from List.of: "+ List.of(1,2,3,5,8,13,21).stream());
        System.out.println("Stream of Integer from Stream.of: "+ streamOfInteger);
        System.out.println("Stream of Integer from Arrays.stream: "+ streamIntegerFromArray);
        System.out.println("Stream of >> int << from Arrays.stream: "+ Arrays.stream(arrayInt));
        System.out.println("Stream of >> int << from IntStream.of: "+ intStream);

        // we are obtaining an "IntPipeline" and we can directly use its integer functions
        Arrays.stream(arrayInt).sum();
        Arrays.stream(arrayInt).max();
        Arrays.stream(arrayInt).average();
        Arrays.stream(arrayInt).count();
        intStream.sum();
        intStream.count();

        // since here we are obtaining a "ReferencePipeline" we need to unbox before
        streamOfInteger.mapToInt(Integer::intValue).sum();
        streamIntegerFromArray.mapToInt(Integer::intValue).max();
        List.of(1,2,3,5,8,13,21).stream().mapToInt(Integer::intValue).average();
    }
}
