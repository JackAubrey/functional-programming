package courses.in28min.funcprogramming;

import java.util.ArrayList;
import java.util.List;

public class FP15PlayWith_replaceAllAndRemoveIf {
    public static void main(String[] args) {
        // "List.of" create an unmodifiable list
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");
        // we need to create a new one modifiable
        //      getModifiableCourseList(courses)
        // this method return a List of strings cloned from the imput
        List<String> coursesModifiable = getModifiableCourseList(courses);
        System.out.println("List of Courses: "+coursesModifiable);

        // now use "replaceAll" to convert our items to uppercase.
        coursesModifiable.replaceAll(s -> s.toUpperCase());
        System.out.println("Modified List of Courses: "+coursesModifiable);

        // now use "removeIf" to remove all thar courses with length less than 6
        coursesModifiable.removeIf(s -> s.length() < 6);
        System.out.println("Courses with Length GTE 6: "+coursesModifiable);

        // now use "removeAll" to remove all data that match another list
        coursesModifiable = getModifiableCourseList(courses); // we regenerate the list
        coursesModifiable.removeAll(List.of("AWS", "PCF","Azure"));
        System.out.println("Courses Filtered: "+coursesModifiable);

        // now use "retainAll" to retail only data that match another list
        coursesModifiable = getModifiableCourseList(courses); // we regenerate the list
        coursesModifiable.retainAll(List.of("AWS", "PCF","Azure"));
        System.out.println("Courses Retained: "+coursesModifiable);
    }

    static List<String> getModifiableCourseList(List<String> courses) {
        return new ArrayList<>(courses);
    }
}
