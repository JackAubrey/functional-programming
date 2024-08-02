## Streams
We can use Streams in both functional and declarative style.
![image info](./imgs/Screenshot_20240731_155206.png "Design patterns Mind Map")
*Streams are not containers. Don't confuse it with List or more generally with a Collection.*  
It is a very advanced and fancy Iterator which does not contain any data.  
Streams expose a lot of methods (API), someone called Intermediate Operations, others called Terminal Operations.
Moth of Stream's methods (API) are Higher Order Functions, like:
- Filter
- Map
- Reduce

**Streams are lazy** and they are also:
- Declarative
- Concise and Readable
- Flexible
- Parallelize

![image info](./imgs/Screenshot_20240731_172641.png "Streams")

### Streams Pipeline
A stream pipeline consists of:
- **Source** from which we generate the stream such as collection an array a generator function or an io channel.
- **Zero or more intermediate operations** these are operations that are applied on a stream, and they return another stream.
- **Terminal operation** produce a result or side effect from the stream.
  As we said a side effect is anything other than returning the result such as a print statement.

![image info](./imgs/Schermata_20240801_122547.png "Stream Pipeline")

### Streams are not data Containers
Stream are immutables, once consumed you can not reuse it. We neither can add anything to an existing stream nor we can remove anything from it.   
If you try to reuse you'll obtain the error "stream has already been operated upon or closed".  
Streams are not collections, neither array nor other.  
Stream are more similar to an Iterator, very advanced and fancy, but like this one.  
Streams didn't contain data, Stream permit to iterate and applying operations over it during this data iteration and this is the stream pipeline.  
What we are able to perform on Stream is: read data, make an **operation that returns a new stream.**

![image info](./imgs/Schermata_20240801_141903.png "Stream are not data Containers")

### Streams are Lazy
As we already said, stream are lazy. We saw what terminal operations are we so what intermediate operations. We have already just encountered some of these.  
All the intermediate operations are lazy!  
Like a simple declaration, they will be executed only when the stream will be triggered against a terminal operation.  
Only the terminal operations are eager.

    // look this example
    Stream.of(1,2,3,4,5)
          // "peek" is used only to debug, peek the current value from the stream without consuming it
          .peek( n -> System.out.println("N before filter = "+n) )
          .filter( n -> n%2 == 0)
          // "peek" is used only to debug, peek the current value from the stream without consuming it
          .peek( n -> System.out.println("N before map= "+n) )
          .map(n -> ""+n);

    // if you try to execute this code, nothing happens because its only a declaration od the stream pipeline.
    // neither the filter, neither the map will be executed and also the "peek" function.
    //
    // if we would assign that code to a reference we should:
    Stream<String> stream = Stream.of(1,2,3,4,5)
          .peek( n -> System.out.println("N before filter = "+n) )
          .filter( n -> n%2 == 0)
          .peek( n -> System.out.println("N before map= "+n) )
          // map take an Integer a produce a String
          // this is why the final stream has "String" as type
          .map(n -> ""+n);

    // Now triggering the Stream using one of terminal operations like "reduce", "collect" and so on
    List<String> list = stream.toList();
    System.out.println(list);

    // in this case and only in this case the pipeline will be execute producing this output
    // N before filter = 1
    // N before filter = 2
    // N before map= 2
    // N before filter = 3
    // N before filter = 4
    // N before map= 4
    // N before filter = 5
    // [2, 4]
    

### Streams API | Intermediate Operations | FILTER
A typical Intermediate Operations provided by the Stream API is the Filter.
Filter receive an item from the stream, apply the predicate logic we provided and if the predicate is satisfied it returns in a new Stream.
Filter is the functional equivalent of the "if" statement

    Stream<T> filter(Predicate<T> predicate);

Let's see an example!

    Predicate<Integer> evenPredicate = i -> i%2 == 0;

    List<Integer> listByLambdaPredicate = Stream.of(1,2,3,4,5,6,7,8,9,10)
          .filter( i -> i%2 == 0) // our lambda that implements the Predicate
          .toList();

    List<Integer> listByPredicateRef = Stream.of(1,2,3,4,5,6,7,8,9,10)
          .filter( evenPredicate) // our Predicate directly applyed
          .toList();

    // you'll see the same result.
    // 
    // Filter wants a Predicate<T>
    // A predicate accept a data, apply a logic and returns a boolean that indicate if test is passed or not
    //
    System.out.println(listByLambdaPredicate);
    // [2, 4, 6, 8, 10]
    System.out.println(listByPredicateRef);
    // [2, 4, 6, 8, 10]

### Streams API | Intermediate Operations | MAP
Another typical Intermediate Operations provided by the Stream is the map.  
Map use the functional interface Function<T,U> that which one take a value and return a new one.
It is another major used Stream method API. Very used to transform data from a type to another, to map a data against another and so on.
Again, every data returned by the "map" function will return as a new Stream.

#### **FUNDAMENTAL:** never change the state of the object received in input. Everytime returns a new one! Otherwise, we might get **concurrent modification exception** at runtime.
#### Moreover, we should try to avoid changing the state of an object when using functional programming

    <R> Stream<R> map(Function<T, R> mapper);

Let's see an example!

    Function<Integer, String> intToString = i -> "A String of "+i;

    List<String> listByLambdaPredicate = Stream.of(1,2,3,4,5,6,7,8,9,10)
          .map( i -> "A String of "+i) // our lambda that implements the Predicate
          .toList();

    List<String> listByPredicateRef = Stream.of(1,2,3,4,5,6,7,8,9,10)
          .map( intToString) // our Predicate directly applyed
          .toList();

    // you'll see the same result.
    // 
    // Map wants a Function<T,R>
    // A function accept a data, apply a logic and returns a result
    //
    System.out.println(listByLambdaPredicate);
    // [A String of 1, A String of 2, A String of 3, A String of 4, A String of 5, A String of 6, A String of 7, A String of 8, A String of 9, A String of 10]
    System.out.println(listByPredicateRef);
    // [A String of 1, A String of 2, A String of 3, A String of 4, A String of 5, A String of 6, A String of 7, A String of 8, A String of 9, A String of 10]

### Streams API | Intermediate Operations | REDUCE
Many times we need to perform operations where a stream reduces to single resultant value.  
*Reducing is the repeated process of combining all the elements.*  
![image info](./imgs/Schermata_20240802_111157.png "Reduce API")
Let's see the "reduce" API.  
- The first parameter "identity" is used to set the initial value and also identify the final result type. It has no effect during the accumulation operation.
- The second parameter "accumulator" is used to accumulate values during the final process. Remember the **Tail Call Optimization (aka TCO)** concept treated in "Functional Programming Techniques".


    T reduce(<T> identity, BinaryOperator accumulator)

Let's see an example!

    // Remember
    //
    // 1 - The BinaryOperator<T> is a BinaryFunction<T,U,R> where all the input and output have all the same type
    // 2 - An accumulator is based on TCO technique where every single iteration produce a result without waiting the previous. In this case.
    //      - first iteration  a = Identity Value,  b = first stream value       and return a + b
    //      - second iteration a = previous result, b = the second stream value  and return a + b
    //      - last condition return a
    BinaryOperator<Integer> accumulator = (a,b) -> a+b;

    Integer sum1 = Stream.of(1,2,3,4,5,6,7,8,9,10)
          .reduce(0, accumulator);
    System.out.println(sum1); // will print 55

    // now we are going to demostrate the usage of the "Identity" and how the accumulator works.
    Integer sum2 = Stream.of(1,2,3,4,5,6,7,8,9,10)
          // this is the equivalent code of the "accumulator" but for each iteration print the input parameters "a" and "b"
          .reduce(10, (a,b) -> {
              System.out.println("A = ["+a+"], B = ["+b+"]");
              return a+b;
          });
    System.out.println(sum2);

    // this is what will be produced into the console
    // A = [10], B = [1]
    // A = [11], B = [2]
    // A = [13], B = [3]
    // A = [16], B = [4]
    // A = [20], B = [5]
    // A = [25], B = [6]
    // A = [31], B = [7]
    // A = [38], B = [8]
    // A = [46], B = [9]
    // A = [55], B = [10]
    // 65

