package courses.basics_strong.funcprogramming.section13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class CustomCollector {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(2,6,8,9,0,1,52,5,61,8,9,96,0,18,23);

        Collector<Integer, List<Integer>, List<Integer>> toList = Collector.of(
                ArrayList::new, //this is the Supplier
                (list, e) -> list.add(e), // this is the Accumulator, it is a BiConsumer
                (list1, list2) -> {
                    // this is the Combiner, and it uses a BiFunction
                    // the combiner is used in parallel operations
                    list1.addAll(list2);
                    return list1;
                },
                // This is the Characteristic.
                // "Identity Finish" characteristic is the most general one.
                // It says that there is no need to have or to execute the finisher function.
                // This is the reason why in this example we didn't used the "of" method that wants the "finisher".
                // This characteristic assume that the accumulator or the combiner do not need futher processing
                Collector.Characteristics.IDENTITY_FINISH
        );

        List<Integer> evens = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(toList);

        System.out.println("unordered events");
        evens.forEach(System.out::println);

        // now we want to create a collector that returns a sorted collection.
        // in this case we are going to use the "finisher" method.
        Collector<Integer, List<Integer>, List<Integer>> toSortedList = Collector.of(
                ArrayList::new, //this is the Supplier
                (list, e) -> list.add(e), // this is the Accumulator, it is a BiConsumer
                (list1, list2) -> {
                    // this is the Combiner, and it uses a BiFunction
                    // the combiner is used in parallel operations
                    list1.addAll(list2);
                    return list1;
                },
                (list) -> {
                    Collections.sort(list);
                    return list;
                },
                // we are using the finisher, it is not an identity function.
                // see the "Collector.Characteristics.UNORDERED" documentation in order to understand why we are using it.
                Collector.Characteristics.UNORDERED
        );

        List<Integer> evensSorted = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(toSortedList);

        System.out.println("\nordered events");
        evensSorted.forEach(System.out::println);
    }
}
