package courses.in28min.funcprogramming;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FP11PlayWith_StringOperations {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        // collect all string elements in a single string comma separated
        String result = courses.stream().collect(Collectors.joining(", "));
        System.out.println(result);

        // split a string and collect the result by word length
        String statement = "Hello World My name is Jane and I hate Mango Fruit";

        Map<Integer, List<String>> map = Arrays.stream(statement.split(" "))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.groupingBy(i-> i.length()));
        System.out.println(map);


        // count letter occurrence
        Map<String, Long> charsOccurrence = Arrays.stream(statement.split(""))
                .filter(c -> !" ".equals(c))
                .map(c -> c.toUpperCase())
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        System.out.println(charsOccurrence);

        // group letter by count occurrence
        Map<Long, List<String>> charsGroupedByOccurrence = Arrays.stream(statement.split(""))
                .filter(c -> !" ".equals(c))
                .map(c -> c.toUpperCase())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getValue(), Collectors.mapping(e -> e.getKey(), Collectors.toList())));

        System.out.println(charsGroupedByOccurrence);
    }
}
