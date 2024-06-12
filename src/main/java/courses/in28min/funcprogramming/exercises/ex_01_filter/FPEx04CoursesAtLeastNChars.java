package courses.in28min.funcprogramming.exercises.ex_01_filter;

import java.util.List;

public class FPEx04CoursesAtLeastNChars {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        printCoursesWithAtLeastNChars(courses, 4);
    }

    private static void printCoursesWithAtLeastNChars(List<String> courses, int numChar) {
        courses.stream()
                .filter(c -> c.trim().length() >= numChar)
                .forEach(System.out::println);
    }
}
