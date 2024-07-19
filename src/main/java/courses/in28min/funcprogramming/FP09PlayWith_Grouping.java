package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FP09PlayWith_Grouping {
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

        // Play with "collect" with Collectors.grouping
        // This Stream function is terminal functions. It produces a single value.
        //

        // COLLECT with COLLECTORS::GROUPING
        // In this example we will obtain a map with grouped courses (list of courses grouped)
        Map <CourseCategory, List<Course>> coursesByCategory = courses.stream()
                .collect(Collectors.groupingBy(Course::getCategory));
        System.out.println("Courses By Category: "+coursesByCategory);

        // COLLECT with COLLECTORS::GROUPING AND COUNT
        // In this example we will obtain a map with count of group (num of grouped courses)
        Map <CourseCategory, Long> coursesCountByCategory = courses.stream()
                .collect(Collectors.groupingBy(Course::getCategory, Collectors.counting()));
        System.out.println("Number of Courses By Category: "+coursesCountByCategory);

        // COLLECT with COLLECTORS::GROUPING AND AVERAGE
        // In this example we will obtain a map with count of group (num of grouped courses)
        Map <CourseCategory, Double> coursesAvgReviewByCategory = courses.stream()
                .collect(Collectors.groupingBy(Course::getCategory, Collectors.averagingInt(Course::getReviewScore)));
        System.out.println("Average Review Score of Courses By Category: "+coursesAvgReviewByCategory);

        // COLLECT with COLLECTORS::GROUPING AND MAX
        // In this example we will obtain a map with count of group (num of grouped courses)
        //
        // PAY ATTENTION to this example.
        //      Collectors.maxBy(...) returns an Optional of Course
        // So! This means we have to use an intermediate Collectors' function to return the desired value.
        // Check
        //
        //      // We are saying: Ok start to collect using a collector and then do a certain operation
        //      Collectors.collectingAndThen(
        //
        //          // 1- "collecting" using the "maxBy" that wants a comparator.
        //          // we use and int comparator against the course review score
        //          Collectors.maxBy(Comparator.comparingInt(Course::getReviewScore)),
        //          // 2- "and then" take the optional course and
        //          //      - if NOT present we will use the "noCourseFound" supplier method
        //          //      - else we will get the course found
        //          //      - get the review score
        //          c -> c.orElseGet(FP09PlayWith_Grouping::noCourseFound).getReviewScore()
        //      )
        Map <CourseCategory, Integer> coursesMaxReviewByCategory = courses.stream()
                .collect(
                        Collectors.groupingBy(Course::getCategory,
                            Collectors.collectingAndThen(
                                    Collectors.maxBy(Comparator.comparingInt(Course::getReviewScore)),
                                    c -> c.orElseGet(FP09PlayWith_Grouping::noCourseFound).getReviewScore()
                            )
                        )
                );
        System.out.println("Max Review Score of Courses By Category: "+coursesMaxReviewByCategory);

        // COLLECT with COLLECTORS::GROUPING and MAPPING
        // In this example we will obtain a map with grouped courses names (list of grouped courses names)
        Map <CourseCategory, List<String>> coursesNameByCategory = courses.stream()
                .collect(Collectors.groupingBy(Course::getCategory,
                        Collectors.mapping(Course::getName, Collectors.toList())));
        System.out.println("Courses Names By Category: "+coursesNameByCategory);
    }

    static Course noCourseFound() {
        return  new Course("NONE", CourseCategory.Cloud, 0, 0);
    }
}

