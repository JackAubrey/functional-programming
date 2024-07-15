package courses.in28min.funcprogramming;

import java.util.List;
import java.util.function.Predicate;

public class FP13PerformanceLazyOperations {
    public static void main(String[] args) {
        // Why are we excited about functional programming?
        // Why is it that recently there is a lot more interest in functional programming?
        //
        //      >> One of the important reasons is PERFORMANCE. <<
        //
        // It is very easy to write performant code with functional programming using the multicore processors.
        // You can actually parallelize the functional code much more easily than structured code.

        // Given a list of string
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        // Let's take a look an example using sequential operations.
        // we want:
        //  - I would want to get all the courses whose length is greater than a specific number
        //  - Then I would want to uppercase them
        //  - I would want to find the first among them.
        int courseLength = 11;
        String aCourse = courses.stream()
                .peek(s -> System.out.println("Before Filter: "+s))
                .filter(filterByLengthFunction(courseLength))
                .map(String::toUpperCase)
                .peek(s -> System.out.println("After Filter and Map: "+s))
                .findFirst()
                .orElse("No course found with length greater than "+courseLength);
        System.out.println("Result >> " + aCourse);

        /*
         * Look an interesting thing.
         * This is our code output
         *
         *      Before Filter: Spring
         *      Before Filter: Spring Boot
         *      Before Filter: API
         *      Before Filter: Microservices
         *      After Filter and Map: MICROSERVICES
         *      Result >> MICROSERVICES
         *
         * Since we used the "findFirst" operation you'll notice there will be only one log output about the mapping
         * and the rest of elements will not be processed.
         * This occurs because "findFirst" is a "short-circuiting terminal operation."
         *
         * So what it does is, it starts looking at the element one at a time.
         *
         *      courses.stream()
         *          // It tries to filter. If it doesn't go through the filter, then it takes the next element. next element, next..
         *          // It checks only until it finds one element, because I would want to do a findFirst( )
         *          .filter(filterByLengthFunction(courseLength))
         *
         *          // and once it finds that element, it does an uppercase, and it returns that specific value back.
         *          .map(String::toUpperCase)
         *
         *          // "findFirst" is a "short-circuiting terminal operation."
         *          .findFirst()
         *
         * So, you can see that with functional programming, writing highly performant code is much easier.
         * The interesting thing about streams is the fact that all the intermediate operations (in our case: peek, filter, map...)
         * are returning a stream back. They are executed on a stream, and they return a stream back.
         *
         * The thing with all the INTERMEDIATE OPERATIONS in Java is that they ARE ALL LAZY.
         * So, THEY ARE NOT REALLY EXECUTED when I am executing this piece of code.
         * THEY ARE ONLY EXECUTED WHEN I EXECUTE THE TERMINAL OPERATION
         */
    }

    static Predicate<String> filterByLengthFunction(int length) {
        return (s) -> s.length() > length;
    }
}
