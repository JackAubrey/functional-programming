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

### Streams API | Intermediate Operations | Filter
A typical Intermediate Operations provided by the Stream is the Filter.
Filter take the streamed item, apply the predicate logic we provided and if the predicate is satisfied it returns all that items that has satisfied our predicate in a new Stream.

![image info](./imgs/Schermata_20240801_143215.png "Stream API | Intermediate Operations | Filter")

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

