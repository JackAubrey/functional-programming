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
        // Try to uncomment the ".reversed()" function to see tha max and min results will be swapped because this two function didn't find the max and min value.
        Comparator<Course> cmpByStudentsAndReviewScore = Comparator.comparingInt(Course::getNoOfStudents)
                .thenComparingInt(Course::getReviewScore)
                /*.reversed() */;

        String max = courses.stream()
                .max(cmpByStudentsAndReviewScore)
                .map(c -> c.getName()+":"+c.getNoOfStudents()+":"+c.getReviewScore())
                .orElse("NONE FOUND");

        System.out.println("Max "+max);

        String min = courses.stream()
                .min(cmpByStudentsAndReviewScore)
                .map(c -> c.getName()+":"+c.getNoOfStudents()+":"+c.getReviewScore())
                .orElse("NONE FOUND");

        System.out.println("Min "+min);
    }

}

