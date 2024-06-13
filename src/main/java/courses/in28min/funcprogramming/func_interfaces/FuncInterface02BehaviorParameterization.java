package courses.in28min.funcprogramming.func_interfaces;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FuncInterface02BehaviorParameterization {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        // Behavior Parameterization is the ability to pass the logic, the BEHAVIOUR as argument of method
        // in the following examples we are passing the logic of the method as an argument of the method
        //  * the first parameter is a stream
        //  * the second parameter is THE LOGIC of the filter
        //  * the third parameter is THE LOGIC of consuming the filtered value

        // NOTE: as you can see, now we are able to pass logic to a method
        // we can return logic o pass logic to another, this means "functions" are first-class citizen

        System.out.println("Filter Even numbers and print");
        filterAndPrint(numbers.stream(), x -> x%2==0, System.out::println);

        System.out.println("Filter Odd numbers and print");
        filterAndPrint(numbers.stream(), x -> x%2!=0, System.out::println);

        System.out.println("Filter numbers multiple of 3 and print");
        filterAndPrint(numbers.stream(), x -> x%3==0, System.out::println);

        // some extreme examples used to show how we can use functions by behaviour
        MyFilter filter = new MyFilter();

        System.out.println("Filter Even numbers and print using MyFilter");
        filterAndPrint(numbers.stream(), filter.setUseEvenFilter(true), System.out::println);

        System.out.println("Filter Odd numbers and print using MyFilter");
        filterAndPrint(numbers.stream(), filter.setUseEvenFilter(false), System.out::println);

        Predicate<Integer> evenFilter = x -> x%2 == 0;
        Predicate<Integer> oddFilter = x -> x%2 != 0;
        CompositeFilter compositeFilter = new CompositeFilter(evenFilter, oddFilter);
        System.out.println("Filter Even numbers and print using Composite Filter");
        filterAndPrint(numbers.stream(), compositeFilter.even(), System.out::println);

        System.out.println("Filter Odd numbers and print using Composite Filter");
        filterAndPrint(numbers.stream(), compositeFilter.odd(), System.out::println);

        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");
        System.out.println("Sort Courses by natural order");
        sortAndPrint(courses, StringSorter::naturalOrder, System.out::println);
        System.out.println("Sort Courses by reversed order");
        sortAndPrint(courses, StringSorter::reverseOrder, System.out::println);

        System.out.println("Sort Courses by natural order string length");
        sortAndPrint(courses, StringSorter::naturalOrderLength, System.out::println);
        System.out.println("Sort Courses by reversed order string length");
        sortAndPrint(courses, StringSorter::reverseOrderLength, System.out::println);

    }

    private static void sortAndPrint(List<String> stream, Comparator<String> comparator, Consumer<String> consumer) {
        stream
                .stream().sorted(comparator)
                .forEach(consumer);
    }

    private static void filterAndPrint(Stream<Integer> stream, Predicate<Integer> predicate, Consumer<Integer> consumer) {
        stream
                .filter(predicate)
                .forEach(consumer);
    }

    class StringSorter {
        static Comparator<String> naturalOrder = (s1, s2) -> s1.compareTo(s2);
        static Comparator<String> reverseOrder = (s1, s2) -> s2.compareTo(s1);

        static Comparator<String> naturalOrderLength = (s1, s2) -> ((Integer)s1.length()).compareTo(s2.length()) ;
        static Comparator<String> reverseOrderLength = (s1, s2) -> ((Integer)s2.length()).compareTo(s1.length()) ;


        public static int naturalOrder(String s1, String s2) {
            return naturalOrder.compare(s1, s2);
        }

        static int reverseOrder(String s1, String s2) {
            return reverseOrder.compare(s1, s2);
        }

        static int naturalOrderLength(String s1, String s2) {
            return naturalOrderLength.compare(s1, s2);
        }

        static int reverseOrderLength(String s1, String s2) {
            return reverseOrderLength.compare(s1, s2);
        }
    }

    private static class MyFilter implements Predicate<Integer> {
        boolean useEvenFilter = true;
        Predicate<Integer> evenFilter = x -> x%2==0;
        Predicate<Integer> oddFilter = x -> x%2!=0;

        @Override
        public boolean test(Integer integer) {
            return useEvenFilter ? evenFilter.test(integer) : oddFilter.test(integer);
        }

        public MyFilter setUseEvenFilter(boolean useEvenFilter) {
            this.useEvenFilter = useEvenFilter;
            return this;
        }
    }

    private static interface MyPredicate<T> extends Predicate<T> {


    }

    private record CompositeFilter(Predicate<Integer> evenFilter, Predicate<Integer> oddFilter) {
        public Predicate<Integer> even() {
            return evenFilter;
        }

        public Predicate<Integer> odd() {
            return oddFilter;
        }
    }
}
