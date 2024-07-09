package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class FP09PlayWith_TakeWhile {
    public static void main(String[] args) {
        List<Course> courses = List.of(
                new Course("Spring", CourseCategory.Framework, 98, 20000),
                new Course("Spring Boot", CourseCategory.Framework, 95, 18000),
                new Course("API", CourseCategory.Microservices, 97, 22000),
                new Course("Microservices", CourseCategory.Microservices, 96, 25000),
                new Course("FullStack", CourseCategory.FullStack, 91, 14000),
                new Course("AWS", CourseCategory.Cloud, 92, 21000),
                new Course("Azure", CourseCategory.Cloud, 99, 21000),
                new Course("Docker", CourseCategory.Cloud, 92, 20000),
                new Course("Kubernetes", CourseCategory.Cloud, 91, 20000));

        // Play with "takeWhile"
        // This Stream function is a short-circuit intermediate function
        //
        // this function take values until the criteria is satisfied. Otherwise, the stream processing will be interrupted and a result produced.
        Predicate<Course> criteria = c -> c.getReviewScore() >= 95;

        List<String> criteriaList = courses.stream()
                .takeWhile(criteria)
                .map(c -> c.getName()+":"+c.getNoOfStudents()+":"+c.getReviewScore())
                .toList();

        List<String> list = courses.stream()
                .map(c -> c.getName()+":"+c.getNoOfStudents()+":"+c.getReviewScore())
                .toList();

        System.out.println("Courses= "+list);
        System.out.println("Courses taken until review score is GTE 95= "+criteriaList);
    }

}

