package courses.in28min.funcprogramming;

import java.util.stream.IntStream;

public class FP14_BEWARE_TO_PARALLEL {
    public static void main(String[] args) {
        // ############# BEWARE #############
        // ## BEWARE to use the parallel.
        // ############# BEWARE #############
        //
        // Parallel processing may be beneficial to fully utilize multiple cores.
        // But we also need to consider the overhead of managing:
        //  - multiple threads
        //  - memory locality
        //  - splitting the source
        //  - merging the results.
        //
        // We touch that argument at the end of this class.
        // As we'll show below, ALSO WE PAY ATTENTION TO UNDESIRED SIDE EFFECT.
        //
        // Take a look to this example
        System.out.println("############# BEWARE #############");
        System.out.println("## This is sequential stream.");
        System.out.println("############# BEWARE #############");
        // We have a list of numbers, from 1 to 4
        int expectedResultSeq = 15;
        int resultSeq = IntStream.rangeClosed(1,4)
                // for each element we print the current thread
                // since we are using a sequential stream we'll see just one thread. The "MAIN" thread
                .peek(x -> {
                    System.out.println(x + " " + Thread.currentThread().getName());
                })
                .reduce(5, Integer::sum);
        System.out.println("Sequential sum of 1 to 4 + 5: "+resultSeq+" Is equals to "+expectedResultSeq+" ? "+(expectedResultSeq == resultSeq));
        /*
         * Taking a look to the logs:
         *
         *      ############# BEWARE #############
         *      ## This is sequential stream.
         *      ############# BEWARE #############
         *      1 main
         *      2 main
         *      3 main
         *      4 main
         *      Sequential sum of 1 to 4 + 5: 15 Is equals to 15 ? true
         *
         * We are able to see:
         *  - just one thread has been used (the "main" thread)
         *  - the result (15) is what we expected. Sum of 1 to 4 (included) + 5 .
         */

        // What happens if we use the parallel?
        // Since the JVM use its internal optimization to recycle primitive objects
        // we use another sequence data to prevent this behavior.
        // We are going to do the same process but summing numbers from 10 till 14 in parallel.
        int expectedResultPar = 65;
        System.out.println("\n\n############# BEWARE #############");
        System.out.println("## This is PARALLEL stream.");
        System.out.println("############# BEWARE #############");
        int resultParallel = IntStream.rangeClosed(10,14)
                // we want to use parallel
                .parallel()
                // for each element we print the current thread
                // since we are using a sequential stream we'll see just one thread. The "MAIN" thread
                .peek(x -> {
                    System.out.println(x + " " + Thread.currentThread().getName());
                })
                .reduce(5, Integer::sum);
        System.out.println("Parallel sum of 10 to 14 + 5: "+resultParallel+" Is equals to "+expectedResultPar+" ? "+(expectedResultPar == resultParallel));
        /*
         * Taking a look to the logs:
         *
         *      ############# BEWARE #############
         *      ## This is PARALLEL stream.
         *      ############# BEWARE #############
         *      12 main
         *      10 ForkJoinPool.commonPool-worker-2
         *      13 ForkJoinPool.commonPool-worker-2
         *      11 ForkJoinPool.commonPool-worker-1
         *      14 main
         *      Parallel sum of 10 to 14 + 5: 85 Is equals to 65 ? false
         *
         * WOW!! The result is wrong. Why?
         * Parallel operations use the java Fork-Join framework.
         * The fork-join framework is in charge of splitting the source data between worker threads and handling callback on task completion.
         *
         * The number of thread depends on the number of cores.
         * In our case, we see 2 "ForkJoinPool.commonPool-worker" and two "main" threads.
         *
         * Since the "reduce" operation is handled in parallel, the number 5 actually gets added up in every worker thread:
         *
         *      main                             = 12+5 = 17
         *      ForkJoinPool.commonPool-worker-2 = 10+5 = 15
         *      ForkJoinPool.commonPool-worker-2 = 13+5 = 18
         *      ForkJoinPool.commonPool-worker-1 = 11+5 = 16
         *      main                             = 14+5 = 19
         *
         * Result = 17+15+18+16+19 = 85.
         * It's a disaster.
         *
         * >>> We STRONGLY need to be careful about which operations can be run in parallel. <<<
         */

        // We achieve the desired result with a little fix
        int resultParallelRight = IntStream.rangeClosed(10,14)
                // we want to use parallel
                .parallel()
                // for each element we print the current thread
                // since we are using a sequential stream we'll see just one thread. The "MAIN" thread
                .peek(x -> {
                    System.out.println(x + " " + Thread.currentThread().getName());
                })
                // we reduce summing data but without adding nothing (we are using 0)
                .reduce(0, Integer::sum)
                // only at the end of stream we add 5
                + 5;
        System.out.println("Parallel sum of 10 to 14 + 5: "+resultParallel+" Is equals to "+resultParallelRight+" ? "+(expectedResultPar == resultParallelRight));

        // ## ####################
        // ## The Overhead
        // ## ####################
        //
        // Comparing sequential and parallel reduction operation
        //
        //      Benchmark                                                     Mode  Cnt        Score        Error  Units
        //      SplittingCosts.sourceSplittingIntStreamParallel               avgt   25      35476,283 ±     204,446  ns/op
        //      SplittingCosts.sourceSplittingIntStreamSequential             avgt   25         68,274 ±       0,963  ns/op
        //
        // The reason behind this is that sometimes the overhead of managing threads
        // sources and results is a more expensive operation than doing the actual work.

        // ## ####################
        // ## Splitting Costs
        // ## ####################
        //
        // Splitting the data source evenly is a necessary cost to enable parallel execution, but some data sources split better than others.
        //
        //      Benchmark                                                     Mode  Cnt        Score        Error  Units
        //      DifferentSourceSplitting.differentSourceArrayListParallel     avgt   25    2004849,711 ±    5289,437  ns/op
        //      DifferentSourceSplitting.differentSourceArrayListSequential   avgt   25    5437923,224 ±   37398,940  ns/op
        //      DifferentSourceSplitting.differentSourceLinkedListParallel    avgt   25   13561609,611 ±  275658,633  ns/op
        //      DifferentSourceSplitting.differentSourceLinkedListSequential  avgt   25   10664918,132 ±  254251,184  ns/op
        //
        // The reason behind this is that arrays can split cheaply and evenly, while LinkedList has none of these properties.
        // TreeMap and HashSet split better than LinkedList but not as well as arrays.

        // ## ####################
        // ## Merging Costs
        // ## ####################
        //
        // Every time we split the source for parallel computation, we also need to make sure to combine the results in the end.
        //
        //      Benchmark                                                     Mode  Cnt        Score        Error  Units
        //      MergingCosts.mergingCostsGroupingParallel                     avgt   25  135093312,675 ± 4195024,803  ns/op
        //      MergingCosts.mergingCostsGroupingSequential                   avgt   25   70631711,489 ± 1517217,320  ns/op
        //      MergingCosts.mergingCostsSumParallel                          avgt   25    2074483,821 ±    7520,402  ns/op
        //      MergingCosts.mergingCostsSumSequential                        avgt   25    5509573,621 ±   60249,942  ns/op
        //
        // The merge operation is really cheap for some operations, such as reduction and addition
        // but merge operations like grouping to sets or maps can be quite expensive.

        // ## ####################
        // ## Memory Locality
        // ## ####################
        //
        // Modern computers use a sophisticated multilevel cache to keep frequently used data close to the processor.
        // When a linear memory access pattern is detected, the hardware prefetches the next line of data under the assumption
        // that it will probably be needed soon.
        // Parallelism brings performance benefits when we can keep the processor cores busy doing useful work.
        // Since waiting for cache misses is not useful work, we need to consider the memory bandwidth as a limiting factor.
        //
        //      Benchmark                                                     Mode  Cnt        Score        Error  Units
        //      MemoryLocalityCosts.localityIntArrayParallel                sequential stream  avgt   25     116247,787 ±     283,150  ns/op
        //      MemoryLocalityCosts.localityIntArraySequential                avgt   25     293142,385 ±    2526,892  ns/op
        //      MemoryLocalityCosts.localityIntegerArrayParallel              avgt   25    2153732,607 ±   16956,463  ns/op
        //      MemoryLocalityCosts.localityIntegerArraySequential            avgt   25    5134866,640 ±  148283,942  ns/op
        //
        // An array of primitives brings the best locality possible in Java.
        // In general, the more pointers we have in our data structure, the more pressure we put on the memory to fetch the reference objects.
        // This can have a negative effect on parallelization, as multiple cores simultaneously fetch the data from memory.

        // SOURCE: https://www.baeldung.com/java-when-to-use-parallel-stream
    }
}
