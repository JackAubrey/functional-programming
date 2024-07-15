package courses.in28min.funcprogramming;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FP11PlayWith_FlatMap {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        // look this situation
        // we are splitting the single stream item into a list of string
        // as you can see we'll obtain a List of String array
        //
        // What do I do to treat these arrays as if they were a single string split by letters?
        List<String[]> resultExample = courses.stream()
                        .map(s -> s.split(""))
                        .toList();
        System.out.println(resultExample);

        // simple use the "flatMap"
        //
        List<String> result = courses.stream()
                .map(s -> s.split(""))
                .flatMap(Arrays::stream)
                .toList();

        System.out.println(result);

        // This is the "flatMap" method signature.
        //
        //      flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
        //
        // It wants a function that:
        //  - accept a value
        //  - return a Stream
        //
        // Look the "explainFlatMap", you'll see some logs
        // Running the code, that method will produce 4 log
        //  - 1: print the java.lang.String reference to the input array
        //  - 2: print its content
        //  - 3: print the java.util.stream.ReferencePipeline reference to the stream obtained from the input array
        //  - 4: print the stream content
        // In this case it is as if we were transforming a piece of data
        List<String> resultExplained = courses.stream()
                .map(s -> s.split(""))
                .flatMap(FP11PlayWith_FlatMap::explainFlatMap)
                .toList();

        System.out.println(resultExplained);

        // Here you can see an advanced method used to applay other data transformations to the input data during the flat-mapping
        List<String> resultExplainedAdvanced = courses.stream()
                .map(s -> s.split(""))
                .flatMap(i -> FP11PlayWith_FlatMap.explainFlatMapAdvanced(i, ExplainCase.UPPER))
                .toList();

        System.out.println(resultExplainedAdvanced);
    }

    private static Stream<String> explainFlatMap(String [] input) {
        System.out.println("Input = "+input);
        System.out.println("Input Clear = "+ Arrays.stream(input.clone()).toList());

        Stream<String> result = Stream.of(input);

        System.out.println("Input Result = "+result);
        // we can not do directly "result.toList()" here otherwise we consume the stream.
        // So this means we have to create a new one
        System.out.println("Input Result Clear = "+ Stream.of(input).toList());

        return result;
    }

    private static Stream<String> explainFlatMapAdvanced(String [] input, ExplainCase explainCase) {
        System.out.println("Input = "+input);
        System.out.println("Input Clear = "+ Arrays.stream(input.clone()).toList());

        Stream<String> result = null; toSimpleStream(input);
        switch (explainCase) {
            case DISTINCT:
                result = toDistinctStream(input);
                System.out.println("Input Result Distinct = "+result);
                System.out.println("Input Result Distinct Clear = "+ toDistinctStream(input).toList());
                break;
            case UPPER:
                result = toUppercaseStream(input);
                System.out.println("Input Result Upper = "+result);
                System.out.println("Input Result Upper Clear = "+ toUppercaseStream(input).toList());
                break;
            default:
                result = toSimpleStream(input);
                System.out.println("Input Result = "+result);
                System.out.println("Input Result Clear = "+ toSimpleStream(input).toList());
                break;
        }

        return result;
    }

    enum ExplainCase {
        SIMPLE, UPPER, DISTINCT
    }

    private static Stream<String> toSimpleStream(String [] input) {
        return Stream.of(input);
    }

    private static Stream<String> toUppercaseStream(String [] input) {
        return Stream.of(input).map(String::toUpperCase);
    }

    private static Stream<String> toDistinctStream(String [] input) {
        return Stream.of(input).distinct();
    }
}
