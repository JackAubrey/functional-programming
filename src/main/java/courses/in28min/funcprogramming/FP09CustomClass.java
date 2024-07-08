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
