package courses.basics_strong.funcprogramming.section10;

import java.util.List;
import java.util.stream.Collectors;

public class StatelessStatefulOp {
    public static void main(String[] args) {
        // Given a list
        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9);

        // DO NOT USE PARALLEL in cases like this.
        // The streams elements are processed by different threads.
        // How they would be able to know which is the second element and similarly for the limit operation?
        // How the threads will know the first five elements ?
        //
        // Do not use parallel in these cases because these operations are difficult to process
        // in parallel since they need a counter visible to all the threads!!!
        List<Integer> collect = list
                .parallelStream() // take a parallel stream
                .skip(2) // we would skip first 2
                .limit(5) // and we would limit the stream to 5
                .collect(Collectors.toList()); // collect the result to a new list

        System.out.println("Parallel Result: "+collect);
    }
}
