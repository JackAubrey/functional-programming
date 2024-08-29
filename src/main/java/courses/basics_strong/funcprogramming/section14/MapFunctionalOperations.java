package courses.basics_strong.funcprogramming.section14;

import courses.basics_strong.funcprogramming.section14.model.Movie;
import courses.basics_strong.funcprogramming.section14.model.MovieComparator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapFunctionalOperations {
    public static void main(String[] args) {
        Map<String,String> contacts = new HashMap<>();
        contacts.put("1237589020", "John");
        contacts.put("1237009020", "John");
        contacts.put("7890291111", "Neal");
        contacts.put("2647210290", "Raju");
        contacts.put("9999999999", "Peter");
        contacts.put("9081234567", "Neha");

        // Traversal Operation
        // This operation is equivalent to the classic "for" loop we are used to use before Java 1.8
        log("## Traversal Operation: iterate a list like a classic for-loop");
        contacts.forEach( (k,v) -> System.out.println(k+" - "+v));

        // Sorting Operation
        log("## Filtering Operation");
        contacts.entrySet()
                        .stream().filter( e -> e.getValue().equalsIgnoreCase("john"))
                        .forEach( e -> System.out.println(e.getKey()+" - "+e.getValue()));

        log("## Filtering Operation - We filtered and collected to a map");
        Map<String, String> filteredContacts = contacts.entrySet()
                .stream().filter(e -> e.getValue().equalsIgnoreCase("john"))
                .collect(Collectors.toMap(c -> c.getKey(), c -> c.getValue()));
        filteredContacts.forEach( (k,v) -> System.out.println(k+" - "+v));

        log("## Sorting Operation: by value");
        contacts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);

        log("## Sorting Operation: by value and collected to another map");
        LinkedHashMap<String, String> collectContacts = contacts.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        // keyMapper
                        Map.Entry::getKey,
                        // valueMapper
                        Map.Entry::getValue,
                        // mergeFunction:   to resolve collisions if the duplicate key gets generated
                        //                  it merge the two values to get a single value and puts that value corresponding to a single key.
                        //                  however the keys are not going to repeat
                        (v1, v2) -> v1, // if two keys was in conflict we receive the two values and in this case we return the first one
                        // mapFactory: basically this is a Supplier
                        LinkedHashMap::new
                ));
        collectContacts.forEach( (k,v) -> System.out.println(k+" - "+v));


        // Mapping Operation
        log("## Mapping Operation");
        contacts.entrySet()
                .stream()
                .map( e -> e.getValue())
                .forEach(System.out::println);


        // Mapping Operation
        log("## Reducing Operation");
        Map<String,Double> marks = new HashMap<>();
        marks.put("Science", 66.00);
        marks.put("Maths", 78.00);
        marks.put("English", 90.00);

        double average = marks.values()
                .stream().mapToDouble(d -> d)
                .average()
                .orElse(-1.0);
        System.out.println("Average: "+average);

    }

    private static void printMovies(List<Movie> list) {
        list.forEach(System.out::println);
    }

    private static void log(String text) {
        System.out.println("\n"+text);
    }
}
