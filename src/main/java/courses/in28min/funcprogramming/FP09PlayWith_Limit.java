package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.Comparator;
import java.util.List;

public class FP09PlayWith_Limit {
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

        // Play with "limit"
        // This Stream function is a short-circuit intermediate function
        //
        // Limit the potential infinite stream to a finite stream
        // If we use it BEFORE certain operation like filter or sort, means we will before limit the stream and than apply filter sort etc.
        Comparator<Course> sortByNumStudentsAndReviews = Comparator
                .comparing(Course::getNoOfStudents)
                .thenComparing(Course::getReviewScore)
                .reversed();

        int limit = 3;
        List<String> limitedList = courses.stream()
                .sorted(sortByNumStudentsAndReviews)
                .map(c -> c.getName()+" - Students="+c.getNoOfStudents()+" - Reviews="+c.getReviewScore())
                .limit(limit)
                .toList();

        System.out.println("First ["+limit+"] courses sorted by Students and Reviews: "+limitedList);

        List<String> limitedListBefore = courses.stream()
                .limit(limit)
                .sorted(sortByNumStudentsAndReviews)
                .map(c -> c.getName()+" - Students="+c.getNoOfStudents()+" - Reviews="+c.getReviewScore())
                .toList();

        System.out.println("First ["+limit+"] courses sorted by Students and Reviews: "+limitedListBefore);


        // we can also combine limit and skip
        // for example we want skip the first two and take the first 3 of remains
        int skip=2;
        limit=3;

        List<String> skipAndLimitList = courses.stream()
                .sorted(sortByNumStudentsAndReviews)
                .map(c -> c.getName()+" - Students="+c.getNoOfStudents()+" - Reviews="+c.getReviewScore())
                .skip(skip)
                .limit(limit)
                .toList();

        System.out.println("First ["+limit+"] courses sorted by Students and Reviews ignoring the first ["+skip+"] elements: "+skipAndLimitList);

        // Pay attention to the order of these two functions:
        //  - If we first put limit and then skip, we will first obtain a truncated list of which we will then ignore a certain number of the first elements.
        //  - If we use skip first and then limit,  we will have the right effect of ignoring a certain number of elements first and then  limiting the rest.
        List<String> skipAndLimitList2 = courses.stream()
                .sorted(sortByNumStudentsAndReviews)
                .map(c -> c.getName()+" - Students="+c.getNoOfStudents()+" - Reviews="+c.getReviewScore())
                .limit(limit)
                .skip(skip)
                .toList();

        System.out.println("First ["+limit+"] courses sorted by Students and Reviews ignoring the first ["+skip+"] elements: "+skipAndLimitList2);
    }

}

