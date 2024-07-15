package courses.in28min.funcprogramming;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class FP11PlayWith_FlatMapExamples2 {

    public static void main(String[] args) {
        // this is similar to more complex example seen the "FP11PlayWith_FlatMapExamples" class but using List.of instead of Wrapper object like "CourseTuple"
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");
        List<String> courses2 = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes")
                .stream().sorted(Comparator.reverseOrder())
                .map(String::toUpperCase)
                .toList();

        List<List<String>> distinctAndFixSameNamesAndSort = courses.stream()
                .flatMap(n1 ->
                        // "n1" is the current "courses" stream item
                        // for every "n1" we stream "courses2"
                        courses2.stream()
                                // use a filter to detect if the current "courses2" (aka "n2") element has the same length
                                // of the current "courses" element (aka "n1")
                                .filter(n2 -> n1.length() == n2.length())
                                // and map them into an object
                                .map(n2 -> List.of(n1,n2))
                )
                .map(FP11PlayWith_FlatMapExamples2::collectSorted)
                .distinct()
                // we filter all that tuples where "name1" and "name2" are equals ignoring case
                .filter(i -> !i.get(0).equalsIgnoreCase(i.get(1)))
                .sorted((l1, l2) -> Comparator.<Integer>naturalOrder().compare(l1.get(0).length(), l2.get(0).length()))
                .toList();

        System.out.println("Example 2: "+distinctAndFixSameNamesAndSort);
    }

    private static <T> List<T> mergeOne(Stream<List<T>> listStream) {
        return listStream.flatMap(List::stream).toList();
    }


    static List<String> collectSorted(List<String> input) {
        return new ArrayList<>(input.stream().map(String::toUpperCase).sorted().toList());
    }
}
