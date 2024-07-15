package courses.in28min.funcprogramming;

import courses.in28min.funcprogramming.data.Course;
import courses.in28min.funcprogramming.data.CourseCategory;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FP12HigherOrderFunctions {
    public static void main(String[] args) {
        // A higher order function is a function which returns a function.
        //
        // In order to better understand this concept, lets start with review some peaces of code
        // we already met during others examples
        //
        // we have:
        //
        // a list of courses
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

        // and two predicates very similar whit just one difference, the cutoff value
        Predicate<Course> coursesReviewGT95 = (c) -> c.getReviewScore() > 95;
        Predicate<Course> coursesReviewGT90 = (c) -> c.getReviewScore() > 90;

        // we can filter the list with one of them
        List<Course> list01 = courses.stream().filter(coursesReviewGT95)
                .toList();
        System.out.println("Courses greater than 95: "+list01);
        /*
         * We'll obtain this
         *
         * Course{name='Spring', category='Framework', reviewScore=98, noOfStudents=20000}
         * Course{name='API', category='Microservices', reviewScore=97, noOfStudents=22000}
         * Course{name='Microservices', category='Microservices', reviewScore=96, noOfStudents=25000}
         * Course{name='Azure', category='Cloud', reviewScore=99, noOfStudents=21000}
         */

        // but what should I do if I wanted to change the cut-off?
        // ok I could use the second predicate
        //
        //      courses.stream().filter(coursesReviewGT90)
        //
        // it's not a thing so dynamically
        // lets start with a bit refactoring
        //
        //  1 - define a cut-off variable
        int reviewScoreCutOff = 95;

        // use the variable to one of the previous predicate
        coursesReviewGT95 = (c) -> c.getReviewScore() > reviewScoreCutOff;

        // use the refactoring IDE functionality.
        // on IntelliJ:
        //  - select "(c) -> c.getReviewScore() > reviewScoreCutOff"
        //  - CTRL + ALT + M
        //  - or Left Click > Refactoring > Extract Method
        //  - give a name to the extracted method
        //
        // Below here you see the method "reviewScorePredicateByCutOffValue".
        // This method returns Predicate with inside a logic.
        // This function is a HIGHER ORDER FUNCTIONS because like we defined to the top of this example:
        //      >> A higher order function is a function which returns a function. <<
        // In this case we are returning back a Predicate.
        //
        // now lets go to use it
        int myCutOff = 92;

        List<Course> list02 = courses.stream().filter(reviewScorePredicateByCutOffValue(myCutOff))
                .toList();
        System.out.println("Courses greater than "+myCutOff+": "+list02);
        /*
         * We'll obtain this
         *
         * Course{name='Spring', category='Framework', reviewScore=98, noOfStudents=20000}
         * Course{name='Spring Boot', category='Framework', reviewScore=95, noOfStudents=18000}
         * Course{name='API', category='Microservices', reviewScore=97, noOfStudents=22000}
         * Course{name='Microservices', category='Microservices', reviewScore=96, noOfStudents=25000}
         * Course{name='Azure', category='Cloud', reviewScore=99, noOfStudents=21000}
         */


        // Another simple example about the HIGHER ORDER FUNCTIONS
        // Given a stream of integer
        // we want to calculate the pow of each-one dynamically
        short square = 2;
        IntStream.rangeClosed(1, 10).mapToObj(Integer::valueOf)
                .map(pow(square))
                .toList()
                .forEach(System.out::println);

        short cube = 3;
        IntStream.rangeClosed(1, 10).mapToObj(Integer::valueOf)
                .map(pow(cube))
                .toList()
                .forEach(System.out::println);
    }

    /**
     * Accept an Integer used to filter courses with review score greater than a certain value
     * @param reviewScoreCutOff
     * @return a Predicate with the logic! In this case the logic is to filter courses with review score greater than a certain value
     */
    private static Predicate<Course> reviewScorePredicateByCutOffValue(int reviewScoreCutOff) {
        return (c) -> c.getReviewScore() > reviewScoreCutOff;
    }

    private static Function<Integer, PowResult> pow(short exponent) {
        return (i) -> {
            long result = Double.valueOf(Math.pow(i, exponent)).longValue();

            return new PowResult(i, result, exponent);
        };
    }

    record PowResult(Integer value, Long result, Short exponent) {

    }
}
