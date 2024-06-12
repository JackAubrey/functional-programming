package courses.in28min.funcprogramming;

import java.util.Comparator;
import java.util.List;

public class FP06SortComparator {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        System.out.println("Distinct numbers and sort in Natural Order");
        distinctAndSortNaturalOrder(numbers);
        System.out.println("Distinct numbers and sort in Reverse Order");
        distinctAndSortReverseOrder(numbers);
        System.out.println("Distinct String and sort using custom Order based on LENGTH");
        distinctAndSortOrderingByLength(courses);
    }

    private static void distinctAndSortOrderingByLength(List<String> courses) {
        courses.stream()
                .distinct()
                //.sorted(Comparator.comparing(k -> k.length())) // is in natural order
                .sorted(Comparator.comparing(k -> k.length(), Comparator.reverseOrder())) // in reverse order
                //.sorted( (s1,s2) -> ((Integer)s2.length()).compareTo(s1.length()) ) // reverse order is equal to the line before
                .forEach(System.out::println);
    }

    private static void distinctAndSortNaturalOrder(List<Integer> list) {
        list.stream()
                .distinct()
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);

    }

    private static void distinctAndSortReverseOrder(List<Integer> list) {
        list.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

    }

}
