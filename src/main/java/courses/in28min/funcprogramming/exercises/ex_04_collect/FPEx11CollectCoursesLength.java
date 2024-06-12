package courses.in28min.funcprogramming.exercises.ex_04_collect;

import java.util.List;

public class FPEx11CollectCoursesLength {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        listCoursesLength(courses)
                .forEach(System.out::println);
    }

    private static List<Integer> listCoursesLength(List<String> courses) {
        return courses.stream()
                .map(c -> c.length())
                .toList();
    }
}
