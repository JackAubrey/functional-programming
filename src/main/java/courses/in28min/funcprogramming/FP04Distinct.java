package courses.in28min.funcprogramming;

import java.util.List;

public class FP04Distinct {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        distinct(numbers);
        distinct(courses);
    }

    private static void distinct(List<?> list) {
        list.stream()
                .distinct()
                .forEach(System.out::println);

    }

}
