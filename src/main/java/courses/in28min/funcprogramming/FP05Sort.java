package courses.in28min.funcprogramming;

import java.util.List;

public class FP05Sort {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        sort(numbers);
        sort(courses);
        distinctAndSort(numbers);
        distinctAndSort(courses);
    }

    private static void sort(List<?> list) {
        list.stream()
                .sorted()
                .forEach(System.out::println);

    }

    private static void distinctAndSort(List<?> list) {
        list.stream()
                .distinct()
                .sorted()
                .forEach(System.out::println);

    }

}
