package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.List;
import java.util.function.Predicate;

public class FP09PlayWith_CountAverageSum {
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

        // Play with "sum", "average", "count"
        // These Stream functions are terminal functions. They produce a single value.
        //
        Predicate<Course> coursesReviewGT95 = (c) -> c.getReviewScore() > 95;

        // COUNT
        long numOfCourses = courses.stream()
                .filter(coursesReviewGT95)
                // The "mapToInt" is an optimization and also return an IntStream.
                // The IntStream give us the access to special function like sum, average, min, max
                .mapToInt(Course::getNoOfStudents)
                .count();

        System.out.println("Number of Courses available with Review Score GT 95: "+numOfCourses);

        // SUM
        long sumOfNumberOfStudents = courses.stream()
                .filter(coursesReviewGT95)
                // The "mapToInt" is an optimization and also return an IntStream.
                // The IntStream give us the access to special function like sum, average, min, max
                .mapToInt(Course::getNoOfStudents)
                .sum();

        System.out.println("Sum of Number of Students of courses with Review Score GT 95: "+sumOfNumberOfStudents);

        // AVERAGE
        double avgOfNumberOfStudents = courses.stream()
                .filter(coursesReviewGT95)
                // The "mapToInt" is an optimization and also return an IntStream.
                // The IntStream give us the access to special function like sum, average, min, max
                .mapToInt(Course::getNoOfStudents)
                .average()
                .orElse(0.0);

        System.out.println("Average of Number of Students of courses with Review Score GT 95: "+avgOfNumberOfStudents);

        // Other IntStream Function
        int maxNumOfStudents = courses.stream()
                .filter(coursesReviewGT95)
                // The "mapToInt" is an optimization and also return an IntStream.
                // The IntStream give us the access to special function like sum, average, min, max
                .mapToInt(Course::getNoOfStudents)
                .max()
                .orElse(0);

        System.out.println("Maximum of Number of Students of courses with Review Score GT 95: "+maxNumOfStudents);
    }

    static Course noCourseFound() {
        return  new Course("NONE", CourseCategory.Cloud, 0, 0);
    }
}

