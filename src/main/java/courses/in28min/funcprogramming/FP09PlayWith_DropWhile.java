package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.List;
import java.util.function.Predicate;

public class FP09PlayWith_DropWhile {
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

        // Play with "dropWhile"
        // This Stream function is an intermediate function
        //
        // this function drop values until the criteria is satisfied. The rest of stream will be taken.
        Predicate<Course> criteria = c -> c.getReviewScore() >= 95;

        List<String> criteriaList = courses.stream()
                .dropWhile(criteria)
                .map(c -> c.getName()+":"+c.getNoOfStudents()+":"+c.getReviewScore())
                .toList();
        // [Spring Boot:18000:95, API:22000:97, Microservices:25000:96, FullStack:14000:91, AWS:21000:92, Azure:21000:99, Docker:20000:92, Kubernetes:20000:91]
        // [AWS:21000:92, Spring:20000:98, Docker:20000:92, Kubernetes:20000:91, Spring Boot:18000:95, FullStack:14000:91]

        List<String> list = courses.stream()
                .map(c -> c.getName()+":"+c.getNoOfStudents()+":"+c.getReviewScore())
                .toList();

        System.out.println("Courses= "+list);
        System.out.println("Courses taken from review score is GTE 95= "+criteriaList);
    }

}

