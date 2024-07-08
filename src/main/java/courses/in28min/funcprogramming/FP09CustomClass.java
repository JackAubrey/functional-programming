package courses.in28min.funcprogramming;

import java.util.List;

public class FP09CustomClass {
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

        // Play with "allMatch"
        // This Stream function is a final function that return a boolean result
        // An interesting point is: if a test result return FALSE, the rest of value will not be evaluated.
        // try to put "true" the third "testCourseReviewGreaterThan" method parameter
        //
        // we want test if ALL courses has review greater than 90 point
        boolean resultGT90 = courses.stream()
                .allMatch(c -> FP09CustomClass.testCourseReviewGreaterThan(c, 90, false));
        System.out.println(" Are all courses review are greater than 90 points ? "+resultGT90); // we expect true

        // we want test if ALL courses has review greater than 95 point
        boolean resultGT95 = courses.stream()
                .allMatch(c -> FP09CustomClass.testCourseReviewGreaterThan(c, 95, false));
        System.out.println(" Are all courses review are greater than 95 points ? "+resultGT95); // we expect false

        // Play with "noneMatch"
        // This Stream function is a final function that return a boolean result
        // Essentially is the opposite of "allMatch"
        // An interesting point is: if a test result return TRUE, the rest of value will not be evaluated.
        // try to put "true" the third "testCourseReviewLowerThan" method parameter.
        //
        // we want test if ALL courses has review lower than 90 point
        boolean resultLT90 = courses.stream()
                .noneMatch(c -> FP09CustomClass.testCourseReviewLowerThan(c, 90, false));
        System.out.println(" Are all courses review are lower than 90 points ? "+resultLT90); // we expect true

        // we want test if ALL courses has review lower than 95 point
        boolean resultLT95 = courses.stream()
                .noneMatch(c -> FP09CustomClass.testCourseReviewLowerThan(c, 95, false));
        System.out.println(" Are all courses review lower than 95 points ? "+resultLT95); // we expect false

        // Play with "anyMatch"
        // This Stream function is a final function that return a boolean result.
        // The difference with "allMatch" and "noneMatch" is:
        //      - "allMatch" iterate all stream VERIFYING IF ALL TEST ARE POSITIVE.
        //        This means: return true if ALL test results are true.
        //        Immediately return if it found a "false"
        //
        //      - "anyMatch" iterate all stream LOOKING FOR A FIRST POSITIVE TEST RESULT.
        //        This means: return true if AT LEAST one test results is true.
        //        Immediately return a TRUE if it found a "true"
        //
        //      - "noneMatch" iterate all stream VERIFYING IF ALL TEST ARE NEGATIVE.
        //        This means: return false if ALL test results are false.
        //        Immediately return if it found a "true"
        //
        // try to put "true" the third "testCourseReviewLowerThan" method parameter and play with thew review point value.
        //
        // we want test if there is at least one course with review points lower than X points
        int point = 95;
        boolean resultAnyLT = courses.stream()
                .anyMatch(c -> FP09CustomClass.testCourseReviewLowerThan(c, point, false));
        System.out.println(" There is a courses review at least LOWER than "+point+" points ? "+resultAnyLT); // we expect true

    }

    static boolean testCourseReviewGreaterThan(Course course, int review, boolean printValue) {
        if(printValue) {
            System.out.println("--> GT | course: " + course.getName()+" - has a review ["+course.getReviewScore()+"] Greater Than "+review+" ? "+ (course.getReviewScore() > review));
        }
        return course.getReviewScore() > review;
    }

    static boolean testCourseReviewLowerThan(Course course, int review, boolean printValue) {
        if(printValue) {
            System.out.println("--> LT | course: " + course.getName()+" - has a review ["+course.getReviewScore()+"] Lower Than "+review+" ? "+ (course.getReviewScore() < review));
        }
        return course.getReviewScore() < review;
    }
}

enum CourseCategory {
    Framework, Microservices, FullStack, Cloud
}

class Course {
    private String name;
    private CourseCategory category;
    private int reviewScore;
    private int noOfStudents;

    public Course(String name, CourseCategory category, int reviewScore, int noOfStudents) {
        this.name = name;
        this.category = category;
        this.reviewScore = reviewScore;
        this.noOfStudents = noOfStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CourseCategory getCategory() {
        return category;
    }

    public void setCategory(CourseCategory category) {
        this.category = category;
    }

    public int getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", reviewScore=" + reviewScore +
                ", noOfStudents=" + noOfStudents +
                '}';
    }
}
