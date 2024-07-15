package courses.in28min.funcprogramming;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;

public class FP11PlayWith_FlatMapExamples {
    record CourseNameAndPrice(String name, Integer price) {}
    record CourseTuple(String name1, String name2, Integer length) {}

    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes");
        List<String> coursesClone = List.of("Spring", "Spring Boot", "API" , "Microservices", "AWS", "PCF","Azure", "Docker", "Kubernetes")
                .stream().sorted(Comparator.reverseOrder())
                .map(String::toUpperCase)
                .toList();
        List<Integer> coursePrices = List.of(100, 150, 25);

        // we want combine two list
        // for every course item we'll combine with coursePrices item
        List<List<CourseNameAndPrice>> namePriceTuples = courses.stream()
                        .flatMap(c -> coursePrices.stream().map(cp -> List.of(new CourseNameAndPrice(c, cp))))
                        .toList();
        System.out.println(namePriceTuples);

        // we want to create tuples made from elements with the same length
        // NOTE: In order of simplicity, the cloned courses has been sorted in desc mode and items has been put in upper case
        List<CourseTuple> namesTuples = courses.stream()
                .flatMap(n1 ->
                        // "n1" is the current "courses" stream item
                        // for every "n1" we stream "coursesClone"
                        coursesClone.stream()
                                // use a filter to detect if the current "coursesClone" (aka "n2") element has the same length
                                // of the current "courses" element (aka "n1")
                                .filter(n2 -> n1.length() == n2.length())
                                // and map them into an object
                                .map(n2 -> new CourseTuple(n1,n2,n1.length()))
                )
                .toList();

        System.out.println(namesTuples);
        /**
         * Look the result, you'll notice some problems:
         *
         * First: there are a bunch of duplicates.
         * For example:
         *      CourseTuple[name1=API, name2=PCF, length=3]
         *      CourseTuple[name1=PCF, name2=API, length=3]
         *
         * Second: there are some tuples with same name values
         * For example:
         *      CourseTuple[name1=Microservices, name2=MICROSERVICES, length=13]
         *      CourseTuple[name1=Spring, name2=SPRING, length=6]
         *
         * Third: List is unordered
         *
         * CourseTuple[name1=Spring, name2=SPRING, length=6]
         * CourseTuple[name1=Spring, name2=DOCKER, length=6]
         * CourseTuple[name1=Spring Boot, name2=SPRING BOOT, length=11]
         * CourseTuple[name1=API, name2=PCF, length=3]
         * CourseTuple[name1=API, name2=AWS, length=3]
         * CourseTuple[name1=API, name2=API, length=3]
         * CourseTuple[name1=Microservices, name2=MICROSERVICES, length=13]
         * CourseTuple[name1=AWS, name2=PCF, length=3]
         * CourseTuple[name1=AWS, name2=AWS, length=3]
         * CourseTuple[name1=AWS, name2=API, length=3]
         * CourseTuple[name1=PCF, name2=PCF, length=3]
         * CourseTuple[name1=PCF, name2=AWS, length=3]
         * CourseTuple[name1=PCF, name2=API, length=3]
         * CourseTuple[name1=Azure, name2=AZURE, length=5]
         * CourseTuple[name1=Docker, name2=SPRING, length=6]
         * CourseTuple[name1=Docker, name2=DOCKER, length=6]
         * CourseTuple[name1=Kubernetes, name2=KUBERNETES, length=10]
         */

        // fix the duplicates values
        List<CourseTuple> namesTuplesDistinct = courses.stream()
                .flatMap(n1 ->
                        // "n1" is the current "courses" stream item
                        // for every "n1" we stream "coursesClone"
                        coursesClone.stream()
                                // use a filter to detect if the current "coursesClone" (aka "n2") element has the same length
                                // of the current "courses" element (aka "n1")
                                .filter(n2 -> n1.length() == n2.length())
                                // and map them into an object
                                .map(n2 -> new CourseTuple(n1,n2,n1.length()))
                )
                // WE ARE GOING TO USE FILTER IN CONJUNCTION WITH A STATEFUL PREDICATE TO MAKE A DISTINCT.
                //  - we need to use this strategy because the stream "distinct()" operator doesn't work with objects
                //  - inside the "distinctByKey" there is a boolean used to show or not the logs.
                //  - when the "keyExtractor.apply" method inside our stateful predicate will be invoked, this will invoke our lambda.
                //  - since we want to consider [AWS, PCF] equals to [PCF, AWS] for example
                //    we make an XOR operation on both name1 and name2 hashCode lower cased.
                .filter(distinctByKey(cp -> cp.name1.toLowerCase().hashCode() ^ cp.name2.toLowerCase().hashCode()))
                .toList();

        System.out.println(namesTuplesDistinct);
        /**
         * Look the resul.
         * For convenience, the result is shown ordered by name length
         * Now there are no more duplicates like these:
         *      CourseTuple[name1=API, name2=PCF, length=3]
         *      CourseTuple[name1=PCF, name2=API, length=3]
         *
         * But we're still having a result where name1 and name are equals like
         *      CourseTuple[name1=Spring, name2=SPRING, length=6]
         *
         * CourseTuple[name1=Spring, name2=SPRING, length=6]
         * CourseTuple[name1=Spring, name2=DOCKER, length=6]
         * CourseTuple[name1=API, name2=PCF, length=3]
         * CourseTuple[name1=API, name2=AWS, length=3]
         * CourseTuple[name1=AWS, name2=PCF, length=3]
         */

        // fix: remove all that tuples that having name1 equals to name2
        List<CourseTuple> namesTuplesDistinctAndFixSameNames = courses.stream()
                .flatMap(n1 ->
                        // "n1" is the current "courses" stream item
                        // for every "n1" we stream "coursesClone"
                        coursesClone.stream()
                                // use a filter to detect if the current "coursesClone" (aka "n2") element has the same length
                                // of the current "courses" element (aka "n1")
                                .filter(n2 -> n1.length() == n2.length())
                                // and map them into an object
                                .map(n2 -> new CourseTuple(n1,n2,n1.length()))
                )
                // WE ARE GOING TO USE FILTER IN CONJUNCTION WITH A STATEFUL PREDICATE TO MAKE A DISTINCT.
                //  - we need to use this strategy because the stream "distinct()" operator doesn't work with objects
                //  - inside the "distinctByKey" there is a boolean used to show or not the logs.
                //  - when the "keyExtractor.apply" method inside our stateful predicate will be invoked, this will invoke our lambda.
                //  - since we want to consider [AWS, PCF] equals to [PCF, AWS] for example
                //    we make an XOR operation on both name1 and name2 hashCode lower cased.
                .filter(distinctByKey(cp -> cp.name1.toLowerCase().hashCode() ^ cp.name2.toLowerCase().hashCode()))
                // we filter all that tuples where "name1" and "name2" are equals ignoring case
                .filter(courseTuple -> !courseTuple.name1.equalsIgnoreCase(courseTuple.name2))
                .toList();

        System.out.println(namesTuplesDistinctAndFixSameNames);
        /**
         * Look the resul.
         * For convenience, the result is shown ordered by name length.
         * This exactly what we want
         *
         * CourseTuple[name1=Spring, name2=DOCKER, length=6]
         * CourseTuple[name1=API, name2=PCF, length=3]
         * CourseTuple[name1=API, name2=AWS, length=3]
         * CourseTuple[name1=AWS, name2=PCF, length=3]
         */

        // fix the order
        List<CourseTuple> namesTuplesDistinctAndFixSameNamesSorted = courses.stream()
                .flatMap(n1 ->
                        // "n1" is the current "courses" stream item
                        // for every "n1" we stream "coursesClone"
                        coursesClone.stream()
                                // use a filter to detect if the current "coursesClone" (aka "n2") element has the same length
                                // of the current "courses" element (aka "n1")
                                .filter(n2 -> n1.length() == n2.length())
                                // and map them into an object
                                .map(n2 -> new CourseTuple(n1,n2,n1.length()))
                )
                // WE ARE GOING TO USE FILTER IN CONJUNCTION WITH A STATEFUL PREDICATE TO MAKE A DISTINCT.
                //  - we need to use this strategy because the stream "distinct()" operator doesn't work with objects
                //  - inside the "distinctByKey" there is a boolean used to show or not the logs.
                //  - when the "keyExtractor.apply" method inside our stateful predicate will be invoked, this will invoke our lambda.
                //  - since we want to consider [AWS, PCF] equals to [PCF, AWS] for example
                //    we make an XOR operation on both name1 and name2 hashCode lower cased.
                .filter(distinctByKey(cp -> cp.name1.toLowerCase().hashCode() ^ cp.name2.toLowerCase().hashCode()))
                // we filter all that tuples where "name1" and "name2" are equals ignoring case
                .filter(courseTuple -> !courseTuple.name1.equalsIgnoreCase(courseTuple.name2))
                .sorted((CourseTuple t1, CourseTuple t2) -> Comparator.<Integer>reverseOrder().compare(t1.length(), t2.length()))
                .toList();

        System.out.println(namesTuplesDistinctAndFixSameNamesSorted);
    }

    /**
     * This is a stateful predicate.
     * Why stateful?
     *
     * Look the code:
     *
     *  1 - declare a predicate with inside a ConcurrentHashMap.
     *      this is why is stateful. This ConcurrentHashMap leaves all the time.
     *
     *  2 - return a predicate that use that ConcurrentHashMap to check if current value is already mapped
     * @param keyExtractor
     * @return
     * @param <CourseTuple>
     */
    private static <CourseTuple> Predicate<CourseTuple> distinctByKey(
            Function<CourseTuple, Integer> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        boolean showLogs = false;

        if(showLogs) System.out.println("Declared Map to discover seen items");

        return t -> {
            Integer applyResult = keyExtractor.apply(t);
            if(showLogs) {
                System.out.println("apply result = "+applyResult);
            }
            boolean result = seen.putIfAbsent(applyResult, Boolean.TRUE) == null;

            if(showLogs) {
                if (result) {
                    System.out.println("[" + applyResult + "] is NOT present");
                } else {
                    System.out.println("[" + applyResult + "] is ALREADY present");
                }
            }

            return result;
        };
    }
}
