package courses.in28min.funcprogramming;

import java.util.stream.IntStream;

public class FP00Stream_IntermediateAndTerminalOperations {
    public static void main(String[] args) {
        // Intermediate and Terminal operations.
        //
        // Streams provide us a lot of operation, some of the "Intermediate" type, others of the "Terminal" type.
        // Both have an equivalent "short-circuiting" version.
        //
        // Without a "terminal" or "short-circuiting" nothing will be performed.
        //
        // Look these examples

        // Here is missing a terminal operation, so nothing will be printed
        System.out.println("## Without any kind of terminal operation");
        IntStream.rangeClosed(0, 10).peek(System.out::println);

        // Here we are going to perform the same operation using the "terminal" operation "sum();
        // Since "sum" need to sum all the values, we will be able to see "peek" executed
        System.out.println("\n## Using sum");
        IntStream.rangeClosed(0, 10).peek(System.out::println).sum();

        // Here we are going to perform the same operation using the "terminal" operation "count();
        // "count" just count the stream items. No other operation will be performed and peek will not execute
        System.out.println("\n## Using count without any kind of intermediate operations");
        IntStream.rangeClosed(0, 10).peek(System.out::println).count();

        // BUT if we add an Intermediate operation....
        // we are still using the "count" operation, but we have added an "intermediate" operation as the "filter"
        // and then the pipeline will execute all the various methods.
        System.out.println("\n## Using count with intermediate operations");
        IntStream.rangeClosed(0, 10)
                .filter(i -> i%2 == 0) // an intermediate operation
                .peek(System.out::println).count();

        // Now we are going to see an intermediate short-circuiting.
        // we will not see anything because just limited the stream
        System.out.println("\n## Using intermediate short-circuiting operations");
        IntStream.rangeClosed(0, 10)
                .limit(5) // an intermediate short-circuiting operation
                .peek(System.out::println).count();

        // Now we are going to see an intermediate short-circuiting.
        // since we have also another intermediate operation like filter we will be able to see the peek output.
        // this doesn't work with "map" and I don't know why
        System.out.println("\n## Using intermediate short-circuiting operations and filter");
        IntStream.rangeClosed(0, 10)
                .limit(5) // an intermediate short-circuiting operation
                .filter(i -> i%2 == 0) // an intermediate operation
                .peek(System.out::println).count();

        System.out.println("\n## Peek + filter + count: print values");
        long count01 = IntStream.rangeClosed(0, 10)
                .limit(3)
                .filter(i -> i%2 == 0)
                .peek(System.out::println)
                .count();
        System.out.println("Count 01 = "+count01);

        System.out.println("\n## Peek + map + count: doesn't print values");
        long count02 = IntStream.rangeClosed(0, 10)
                .limit(3)
                .map(i -> i*2)
                .peek(System.out::println)
                .count();
        System.out.println("Count 02 = "+count02);

        System.out.println("\n## Peek + map + sum: print values");
        long sum = IntStream.rangeClosed(0, 10)
                .limit(3)
                .map(i -> i*2)
                .peek(System.out::println)
                .sum();
        System.out.println("Sum = "+sum);

        System.out.println("BEFORE");
        IntStream.rangeClosed(0, 10).filter(i -> {
            System.out.println("--> "+i);
            return i%2 == 0;
        });
        System.out.println("AFTER");
    }
}
