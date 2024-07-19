package courses.basics_strong.funcprogramming.section5;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FP02Predicates {
    public static void main(String[] args) {
        // Predicate interface abstract method signature is: "boolean test(T t)"
        // Predicate use "test" to test a condition and return true or false

        // We are NOT using here the stream in order to show the Predicate in action
        List<String> list = List.of("Basics", "", "Strong", " ", "java", "functional", "programming", "", "BasicsStrong");
        Predicate<String> filterEmpty = (s) -> !s.trim().isEmpty();
        Predicate<String> filterNonBasics = (s) -> s.toLowerCase().contains("basics");

        // We want remove from a list of string all the empty strings
        List<String> filteredList = filterList(list, filterEmpty);
        System.out.println(filteredList);
        // Result = [Basics, Strong, java, functional, programming, BasicsStrong]

        // We want remove from a list of string all string that not contains the "basics" string
        filteredList = filterList(list, filterNonBasics);
        System.out.println(filteredList);
        // Result = [Basics, BasicsStrong]

        // as you can see we are to pass different behaviors with same Predicate's signature

        // now we are going to improve using generics
        // we can use one of other already defined predicate
        filteredList = filterListGenerics(list, filterEmpty);
        System.out.println(filteredList);
        // Result = [Basics, Strong, java, functional, programming, BasicsStrong]

        // or create a new one
        List<Integer> listInt = List.of(1,7,12,6,4,9,23,3,7);
        Predicate<Integer> filterIntGT10 = i -> i > 10;
        List<Integer> filteredIntList = filterListGenerics(listInt, filterIntGT10);
        System.out.println(filteredIntList);
        // Result = [12, 23]
    }

    private static List<String> filterList(List<String> list, Predicate<String> predicate) {
        List<String> newList = new ArrayList<>();

        for(String s:list) {
            if( predicate.test(s) ) {
                newList.add(s);
            }
        }

        return newList;
    }

    private static <T> List<T> filterListGenerics(List<T> list, Predicate<T> predicate) {
        List<T> newList = new ArrayList<>();

        for(T s:list) {
            if( predicate.test(s) ) {
                newList.add(s);
            }
        }

        return newList;
    }
}
