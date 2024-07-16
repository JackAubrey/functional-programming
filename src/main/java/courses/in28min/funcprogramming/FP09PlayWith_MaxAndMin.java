package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.Comparator;
import java.util.List;

public class FP09PlayWith_MaxAndMin {
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

        // Play with "max" and "min"
        // These Stream functions are terminal functions. They produce a single value.
        //
        //      MAX - Returns the maximum element (NOT THE MAXIMUM VALUE) of this stream according to the provided Comparator.
        //      MIN - Returns the minimum element (NOT THE MINIMUM VALUE) of this stream according to the provided Comparator.
        //
        // Try to uncomment the ".reversed()" function to see that max and min results will be swapped
        // because this two functions don't find the max and min value but the minimum and maximum element.
        Comparator<Course> cmpByStudentsAndReviewScore = Comparator.comparingInt(Course::getNoOfStudents)
                .thenComparingInt(Course::getReviewScore)
                /*.reversed()*/;

        String max = courses.stream()
                .max(cmpByStudentsAndReviewScore)
                .map(c -> c.getName()+", students: "+c.getNoOfStudents()+", review score: "+c.getReviewScore())
                .orElse("NONE FOUND");

        System.out.println("Max "+max);

        String min = courses.stream()
                .min(cmpByStudentsAndReviewScore)
                .map(c -> c.getName()+", students: "+c.getNoOfStudents()+", review score: "+c.getReviewScore())
                .orElse("NONE FOUND");

        System.out.println("Min "+min);

        /*
         * Look the result
         *
         *      Max Microservices, students: 25000, review score: 96
         *      Min FullStack, students: 14000, review score: 91
         *
         * But if you enable the "reversed" comparator method
         *
         *      Max FullStack, students: 14000, review score: 91
         *      Min Microservices, students: 25000, review score: 96
         *
         * The results are swapped. This is exactly in accord about the "min" and "max" java-doc definition:
         *
         *      "Returns the MAXIMUM/MINIMUM ELEMENT of this stream according to the provided Comparator."
         *
         * Seems to be more similar to:
         *      - sort using comparator
         *      - take the first one of sorted list (min)
         *      - take the last one of sorted list (max)
         */
    }

}

