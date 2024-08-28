## Collectors for Data Processing in Depth 
Collectors make our life easiest if we have to process a lot of data in memory.  
In Java they are very useful to perform reducing operation like:
- accumulating elements into a collection
- summarizing elements according to various criteria
- ...and much more! accumulating and summarizing are the most used.

![image info](./imgs/Schermata_20240808_110830.png "Mind Map")

There are some collectors which are exactly analogous to terminal stream operations.  
We can call them terminal analogous collectors. We have:
- **list collector** to collect into a List
- **set collector** to collect into a Set
- **any collector** we have collection collector
- **map collector** to map

We also have the downstream collectors used for nest a collector inside one other. For example, starting from a list to produce a map with aggregated data.

### What are Collectors
When we trigger a stream pipeline using a terminal operation, all the  intermediate operation will get executed, and their result can be:
- consumed by the terminal operation "forEach"
- reduced to a single value by the terminal operation "reduce"
- collected to a new collection by the terminal operation "collect"

Java provide the utility class "java.util.stream.Collectors"  that provide ready-made reduction algorithms that can accumulate, summarize and aggregate elements into collection.  
We have different ready-made collector or methods to collect the data into various structures or into various containers.  
"Collectors" utility class is a final class that has all static method which provide the algorithm for the desired action and returns it as implementation of "Collector" interface.

### Important Collecting Operations - Collectors API
**Collectors API** offer a bunch of useful ready-made functionalities thought to accomplish typical collecting operations. It is thought for and used with stream "collect" method.  
Every each of them returns a "Collector" of something depending on the "Collectors" utility class method we are going to use.  
Please note: "Collectors" is a class that exposes utility methods. Instead, "Collector" is an interface used by the "collect" stream method.  
The "Collector<T, A, R>" interface define 3 generics:
- **T** Is the type of input elements to the reduction operation
- **A** Is the mutable accumulation type of the reduction operation (often hidden as an implementation detail)
- **R** Is the result type of the reduction operation

Now take a look to most used utility methods.

#### Terminal Analogue Operations
The Collectors utility class offer a set of methods that could be invoked directly with the stream terminal operation "collect(Collector<T, A, R> collector)".  
This is why are called *Terminal Analogue Operations*. Let's look these!

- **Collectors.toList() - Collectors.toUnmodifiableList()**
  - Both are used to collect the stream results to a single List.
  - Both returns: **Collector<T, ?, List<T>>**
- **Collectors.toSet() - Collectors.toUnmodifiableSet()**
  - Both are used to collect the stream results to a single Set.
  - Both returns: **Collector<T, ?, Set<T>>**
- **Collectors.toMap(keyMapper, valueMapper) - Collectors.toUnmodifiableMap(keyMapper, valueMapper)**
  - Both are used to collect the stream results to a Map
  - Both returns: **Collector<T, ?, Map<K,U>>**
- **Collectors.toCollection(Supplier<C> collectionFactory)**
  - Used to collect a stream went from a collection to another kind.  
    For example given a list we would like create a set or a tree-set or a liked-list in order to use the capabilities of this kind of collection.
  - Returns: **Collector<T, ?, C>**
- **Collectors.partitioningBy(Predicate<T> predicate)**
  - This method is useful when we want partition our collection in TWO groups.
  - Returns **Collector<T, ?, Map<Boolean, List<T>>>**
- **Collectors.groupingBy(......)**  
    This method is similar to partitionBy but, it is more versatile!
    It can take several arguments. 
  - The easiest is: **groupingBy(Function<T, K> classifier)**
    - It takes a function used as classifier and returns Collector of a map where the key is the classifier and value is the stream value.
    - Returns **Collector<T, ?, Map<K, List<T>>>**
  - Another useful is: **groupingBy(Function<T, K> classifier, Collector<T, A, D> downstream)**
    - It takes a function used as classifier like the previous one but also accept another Collector used as downstream.
    - Returns **Collector<T, ?, Map<K, D>>**
    - A typical is when we want group a data for a type and create a nested group by another classifier.  
      For example:

          // given a strem, we are going to collect grouping
          Map<Integer, Map<String, List<MyBean>>> map = stream.collect( 
                  // grouping by
                  Collectors.groupingBy(
                          // by age
                          i -> i.age(),
                          // an then grouping by name
                          Collectors.groupingBy(i -> i.name())
                  )
          );

          // another example is collector mapping
          // given a strem, it collect
          Map<Integer, Map<String, List<Double>>> map = stream.collect( 
                  // grouping by
                  Collectors.groupingBy(
                          // by age
                          i -> i.age(),
                          // an then grouping
                          Collectors.groupingBy(
                                  // by name
                                  i -> i.name(),
                                  // an then map the result
                                  Collectors.mapping(
                                          // this is the mapper
                                          i -> i.salary(),
                                          // this is the collector of mapped results
                                          Collectors.toList()
                                  )
                          )
                  )
          );

- **Collectors.joining(CharSequence delimiter)**
  - This method is used to reduce a collection to a single string where the elements are separated by a delimiter.
  - Returns **Collector<CharSequence, ?, String>**

#### Downstream Collectors
We saw some "Terminal Analogue Operations" like "groupingBy" that accept extra parameter named "downstream".  
A "Downstream" permit to cascade or nest Collectors further to provide the post-processing.  
For example, in the snippet shown above, we used to create a sub-aggregation.
When we provide to one these methods a Collectors methods this one is known as "downstream".

    Collectors.groupingBy(Function classifier, Collector downstream)

This operation is known as "Cascading Collectors" 

#### Cascading Collectors
Now we know the capability to create nested operations. Take a look to some of them

- **Collectors.counting()**
  - Used to simply count numbers of elements in a list
  - Returns **Collector<T, ?, Long>**


      // we want to aggregate employees by designation and count how many for each designation
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "Counting" downstream collector.
              Collectors.counting()
          )
      );

- **Collectors Summing**
  - Collectors provide methods to perform a downstream summing operation. Exists a dedicated method for each supported primitive type:
    - **Collectors.summingDouble(ToDoubleFunction<T> mapper)**
    - **Collectors.summingInt(ToIntFunction<T> mapper)**
    - **Collectors.summingLong(ToLongFunction<T> mapper)**
  - These methods are used to sum values provided to the mapper function 
  - Each one has its own return by primitive type:
    - summingDouble returns **Collector<T, ?, Double>**
    - summingInt returns **Collector<T, ?, Integer>**
    - summingLong returns **Collector<T, ?, Long>**


      // Now we want to find the total fund that is being distributed among each designation
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "summing as double" downstream collector.
              Collectors.summingDouble(e -> e.getSalary())
          )
      );

- **Collectors Averaging**
  - Collectors provide methods to perform a downstream average operation. Exists a dedicated method for each supported primitive type:
    - **Collectors.averagingDouble(ToDoubleFunction<T> mapper)**
    - **Collectors.averagingInt(ToIntFunction<T> mapper)**
    - **Collectors.averagingLong(ToLongFunction<T> mapper)**
  - These methods are used to sum values provided to the mapper function
  - Average methods always return the same Collector because an average algorithm always return a double:
    - averagingDouble returns **Collector<T, ?, Double>**
    - averagingInt returns **Collector<T, ?, Double>**
    - averagingLong returns **Collector<T, ?, Double>**


      // Now we want to find the total fund that is being distributed among each designation
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "summing as double" downstream collector.
              Collectors.averagingDouble(e -> e.getSalary())
          )
      );

- **Collectors.maxBy(Comparator<T> comparator)**
  - Used to find the max value in a list using the comparator.
  - Returns **Collector<T, ?, Optional<T>>**


      // now we want max salary for each designation
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "max salary" downstream collector.
              Collectors.maxBy(Comparator.comparingDouble(e -> e.getSalary()))
          )
      );

- **Collectors.minBy(Comparator<T> comparator)**
  - Used to find the min value in a list using the comparator.
  - Returns **Collector<T, ?, Optional<T>>**


      // now we want min salary for each designation
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "min salary" downstream collector.
              Collectors.minBy(Comparator.comparingDouble(e -> e.getSalary()))
          )
      );

- **Collectors.mapping(Function<T,U> mapper, Collector<U, A, R> downstream)**
  - Used to map a value to another. It wants another downstream. This is one example on how to create a cascading of downstream operations
  - Returns **Collector<T, ?, R>**


      // now we want max salary for each designation, but we want only the salary value and not all the employy bean
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "map" downstream collector.
              Collectors.mapping(
                  // the mapper used by "map" downstream collector. The first Level of downstream
                  e -> e.getSalary(),
                  // we provide the nested "max" downstream collector. The second Level of downstream
                  Collectors.maxBy(Comparator.comparingDouble(e -> e))
              )
          )
      );

- **Collectors Summarizing**
  - Collectors provide methods to perform a downstream summarizing operation.  
    Summarizing Operations performs in one row: min, max, average, etc. operations.
    Exists a dedicated method for each supported primitive type:
    - **Collectors.summarizingDouble(ToDoubleFunction<T> mapper)**
    - **Collectors.summarizingInt(ToIntFunction<T> mapper)**
    - **Collectors.summarizingLong(ToLongFunction<T> mapper)**
  - These methods are used to summarize values provided to the mapper function
  - Summarizing methods returns a typed object which contains the summarized data:
    - summarizingDouble returns **Collector<T, ?, DoubleSummaryStatistics>**
    - summarizingInt returns **Collector<T, ?, IntSummaryStatistics>**
    - summarizingLong returns **Collector<T, ?, LongSummaryStatistics>**
  - Each typed SummaryStatistics contains: average, min, max and so on methods to retrieve the calculated value.


      // now we want salary summary for each designation, but we want works only with the salary value and not all the employy bean
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "map" downstream collector. The first Level of downstream
              Collectors.mapping(
                  // the mapper used by "map" downstream collector
                  Employee::getSalary,
                  // we provide the nested "summarizing" downstream collector. The second Level of downstream
                  Collectors.summarizingDouble(e -> e)
              )
          )
      );

- **Collectors.collectingAndThen(Collector<T,A,R> downstream, Function<R,RR> finisher)**
  - This is another useful method, because permit us to perform a downstream and then apply a finisher to each value.
  - Returns **Collector<T,A,RR>**


      // now we want max salary for each designation, but we want only the salary value and not all the employy bean
      stream.collect(
          // This the Terminal Analogue Operations used to group
          Collectors.groupingBy(
              // Its classifier function
              e -> e.getDesignation(),
              // the "map" downstream collector. The first Level of downstream
                  Collectors.mapping(
                      // the mapper used by "map" downstream collector
                      Employee::getSalary,
                      // again another downstream. The second Level of downstream
                      Collectors.collectingAndThen(
                          // we provide the nested "summarizing" downstream collector. The third Level of downstream
                          Collectors.summarizingDouble(e -> e),
                          // this is the finisher function 
                          // which we use to transform DoubleSummaryStatistics into a String with only min and max
                          s -> "Max = " + s.getMax() + " - Min = " + s.getMin()
                      )
                  )
          )
      );

#### Custom Collectors
