package courses.in28min.funcprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FP10CreateStream_PeekFuncConsideration {
    public static void main(String[] args) {
        // This example is thought to show the "peek" stream operation.
        // "peek" is an intermediate operation that permit to peek value from the stream pipeline.
        // It uses a Consumer functional interface, so this means it does not modify in any kind of manner the data.
        // This method EXISTS MAINLY TO SUPPORT DEBUGGING, where you want to see the elements as they flow past a certain point in a pipeline:

        // for example, we want sum the values of int stream and also print the values
        // you'll se for every stream pipeline iteration, peek will print the item value
        // only at the end of stream we will see the "sum"
        int sum = IntStream.rangeClosed(1, 10)
                .peek(v -> System.out.println("Stream Item: "+v))
                .filter(v -> v%2 == 0)
                .peek(v -> System.out.println("Stream Filtered Item: "+v))
                .map(v -> v*2)
                .peek(v -> System.out.println("Stream Filtered and remapped Item: "+v))
                .sum();
        System.out.println("The sum is " + sum);
    }
}
