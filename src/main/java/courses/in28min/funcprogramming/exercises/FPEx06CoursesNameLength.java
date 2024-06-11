package courses.in28min.funcprogramming.exercises;

import java.util.List;

public class FPEx06CoursesNameLength {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");

        printCoursesLength(courses);
    }

    private static void printCoursesLength(List<String> courses) {
        courses.stream()
                .map(c -> "Course \""+c+"\" is " + c.length()+" chars length")
                .forEach(System.out::println);
    }
}
