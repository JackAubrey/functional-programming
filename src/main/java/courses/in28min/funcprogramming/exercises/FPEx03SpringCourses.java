package courses.in28min.funcprogramming.exercises;

import java.util.List;

public class FPEx03SpringCourses {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        printSpringCourses(courses);
    }

    private static void printSpringCourses(List<String> courses) {
        courses.stream()
                .filter(s -> s.toLowerCase().contains("spring"))
                .forEach(System.out::println);
    }
}
