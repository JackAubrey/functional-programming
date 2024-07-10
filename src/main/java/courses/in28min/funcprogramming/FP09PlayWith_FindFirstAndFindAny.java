package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class FP09PlayWith_FindFirstAndFindAny {
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

        // Play with "findFirst" and "findAny"
        // These Stream functions are intermediate functions. They produce a single value.
        // Are typically combined with a filter.
        //
        // There is a very big difference from these two:
        //      - The behavior of the "findAny" operation is explicitly nondeterministic; it is free to select any element in the stream.
        //        This is to allow for maximal performance in parallel operations; the cost is that multiple invocations on the same source may not return the same result.
        //        (If a stable result is desired, use findFirst() instead.)
        //
        Predicate<Course> coursesReviewGTE95 = (c) -> c.getReviewScore() >= 95;
        Predicate<Course> coursesStudentsGTE2000 = (c) -> c.getNoOfStudents() >= 2000;
        Predicate<Course> coursesStudentsLTE2000 = (c) -> c.getNoOfStudents() <= 2000;

        Course courseFound1 = courses.stream()
                .filter(coursesReviewGTE95)
                .filter(coursesStudentsLTE2000)
                .findAny()
                .orElseGet(FP09PlayWith_FindFirstAndFindAny::noCourseFound);

        System.out.println("A Course with Students LTE 2000 and Review Score GTE 95"+courseFound1);

        Course courseFound2 = courses.stream()
                .filter(coursesReviewGTE95)
                .filter(coursesStudentsGTE2000)
                .findFirst()
                .orElseGet(FP09PlayWith_FindFirstAndFindAny::noCourseFound);

        System.out.println("A Course with Students GTE 2000 and Review Score GTE 95"+courseFound2);
    }

    static Course noCourseFound() {
        return  new Course("NONE", CourseCategory.Cloud, 0, 0);
    }
}

