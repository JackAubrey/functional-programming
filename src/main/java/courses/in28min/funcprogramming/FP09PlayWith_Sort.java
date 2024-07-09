package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FP09PlayWith_Sort {
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


        // Play with "sorted"
        // "sorted" is a stream  Intermediate function.
        // we enrich the courses list with same of initial list but written in lower case
        List<String> sortedByNumStudentsAsc = courses.stream()
                .sorted(sortByNumStudentsAsc) // follow this stream function in order to read the notes
                .map(c -> c.getName() + " n° students "+c.getNoOfStudents())
                .toList();
        System.out.println("Sort Courses by num Students ASC: "+sortedByNumStudentsAsc);

        List<String> sortedByNumStudentsDesc = courses.stream()
                .sorted(sortByNumStudentsDesc)
                .map(c -> c.getName() + " n° students "+c.getNoOfStudents())
                .toList();
        System.out.println("Sort Courses by num Students DESC: "+sortedByNumStudentsDesc);

        List<String> sortedByNumStudentsAndReviewsDesc = courses.stream()
                .sorted(sortByNumStudentsAndReviewsDesc)
                .map(c -> c.getName() + " n° students "+c.getNoOfStudents()+" n° reviews "+c.getReviewScore())
                .toList();
        System.out.println("Sort Courses by num Students and Reviews DESC: "+sortedByNumStudentsAndReviewsDesc);

        courses = new ArrayList<>(courses);

        courses.add(new Course("spring", CourseCategory.Framework, 98, 20000));
        courses.add(new Course("spring boot", CourseCategory.Framework, 95, 18000));
        courses.add(new Course("api", CourseCategory.Microservices, 97, 22000));
        courses.add(new Course("microservices", CourseCategory.Microservices, 96, 25000));
        courses.add(new Course("fullstack", CourseCategory.FullStack, 91, 14000));
        courses.add(new Course("aws", CourseCategory.Cloud, 92, 21000));
        courses.add(new Course("azure", CourseCategory.Cloud, 99, 21000));
        courses.add(new Course("docker", CourseCategory.Cloud, 92, 20000));
        courses.add(new Course("kubernetes", CourseCategory.Cloud, 91, 20000));

        List<String> sortedCourses1 = courses.stream()
                .sorted(sortByNameAsc)
                .map(Course::getName)
                .toList();
        System.out.println("Sort Courses by name ASC: "+sortedCourses1);

        List<String> sortedCourses2 = courses.stream()
                // the third parameter is used to perform an ASC or DESC sort
                // using the fourth parameter we are able to perform a sort ignoring case or not
                .sorted( (c1, c2) -> sortByName(c1, c2, true, false))
                .map(Course::getName)
                .toList();
        System.out.println("Sort Courses by name ASC: "+sortedCourses2);

        List<String> sortedCourses3 = courses.stream()
                // the third parameter is used to perform an ASC or DESC sort
                // using the fourth parameter we are able to perform a sort ignoring case or not
                .sorted( (c1, c2) -> sortByName(c1, c2, false, false))
                .map(Course::getName)
                .toList();
        System.out.println("Sort Courses by name DESC: "+sortedCourses3);
    }

    static final Comparator<Course> sortByNameAsc = (c1, c2) -> c1.getName().trim().compareTo(c2.getName().trim());

    // Remember: Comparator.comparing wants a Function<T,U>
    //  - T is the Course received by the stream
    //  - U is the num of students of course received by the stream.
    //
    // We can write it in this manner
    //
    //  static final Function<Course, Integer> funcCompareNumStudents = (c) -> c.getNoOfStudents();
    //  static final Comparator<Course> sortByNumStudentsAsc = Comparator.comparingInt(funcCompareNumStudents);
    //
    // or in this manner:
    //
    //  static final Function<Course, Integer> funcCompareNumStudents = Course::getNoOfStudents;
    //  static final Comparator<Course> sortByNumStudentsAsc = Comparator.comparingInt(funcCompareNumStudents);
    //
    // Remember: since we are handling a primitive integer value, it's always better use the appropriate function when available.
    // If the date we are handling is not a primitive use ".comparing(...)
    //
    //  PRIMITIVE:
    //      Comparator.comparingInt(Course::getNoOfStudents);
    //
    //  OBJECT
    //      Comparator.comparing(Course::getName);
    static final Comparator<Course> sortByNumStudentsAsc = Comparator.comparingInt(Course::getNoOfStudents);
    static final Comparator<Course> sortByNumStudentsDesc = Comparator.comparingInt(Course::getNoOfStudents).reversed();
    static final Comparator<Course> sortByNumStudentsAndReviewsDesc = Comparator.comparingInt(Course::getNoOfStudents)
            .thenComparing(Course::getReviewScore)
            .reversed();

    static int sortByName(Course c1, Course c2, boolean asc, boolean caseSensitive) {
        String name1 = caseSensitive ? c1.getName().trim() : c1.getName().toLowerCase().trim();
        String name2 = caseSensitive ? c2.getName().trim() : c2.getName().toLowerCase().trim();

        if(asc) {
            return name1.compareTo(name2);
        } else {
            return name2.compareTo(name1);
        }
    }
}

