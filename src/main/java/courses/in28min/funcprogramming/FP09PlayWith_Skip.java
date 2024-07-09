package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.Comparator;
import java.util.List;

public class FP09PlayWith_Skip {
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

        // Play with "skip"
        // This Stream function is intermediate function
        //
        // Ignore a certain number of elements.
        // If we use it AFTER some operation like "sorted", before we sort and after we skip
        Comparator<Course> sortByNumStudentsAndReviews = Comparator
                .comparing(Course::getNoOfStudents)
                .thenComparing(Course::getReviewScore)
                .reversed();

        int skip = 3;
        List<String> skippedList = courses.stream()
                .sorted(sortByNumStudentsAndReviews)
                .map(c -> c.getName()+" - Students="+c.getNoOfStudents()+" - Reviews="+c.getReviewScore())
                .skip(skip)
                .toList();

        System.out.println("Courses sorted by Students and Reviews ignoring the first ["+skip+"] elements: "+skippedList);

        List<String> skippedListBefore = courses.stream()
                .skip(skip)
                .sorted(sortByNumStudentsAndReviews)
                .map(c -> c.getName()+" - Students="+c.getNoOfStudents()+" - Reviews="+c.getReviewScore())
                .toList();

        System.out.println("Courses sorted by Students and Reviews ignoring the first ["+skip+"] elements: "+skippedListBefore);
    }

}

