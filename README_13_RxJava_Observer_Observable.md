## RxJava | Observer - Observable
![image info](./imgs/Schermata_20240904_095408.png "Mind Map")

### Observable-Observer
If you think a bit to how the Observable works you'll notice that it is quite similar to Iterator with a main different: whereas the Iterator pulls the data from the source, the Observable push them towards the observers.  
RxJava works very similar to Stream with intermediate and terminal operations, so this means also Observable is lazy: it does nothing until it is subscribed.  
To once the observer gets subscribed it start executing the pipeline on the element emitted by the observable.  

    // Java Stream
    stream
      // intermediate operation
      .map(v -> "Mapping value "+v+" in order to transform it to string")
      // terminal operation
      .forEach(System.out::println)

    // RxJava
    observable
      // intermediate operation
      .map(v -> "Mapping value "+v+" in order to transform it to string")
      // terminal operation
      .subscribe(System.out::println)

Take a look to how Observable works. This is the Observable interface:

    interface ObservableSource<T> {
      void subscribe(Observer<T> observer);
    }

1. The Observer invokes the subscribe method to subscribe to an observable.
2. After subscribing to an observable the observer basically passes three types of events:
   1. **onNext(T value)** the source observable passes each item one at a time to the observer using this method
   2. **onComplete()** this tells that no more or next calls will occur.  
      The emission of elements or the events is now complete.
   3. **onError(Throwable error)** in reactive programming errors are just treated like data.  
      The data flows from observable to observer or through the complete pipeline errors also gets propagated.  
      *They are also first class citizens.* If we get any error or any event that's get here in "onError" to be handled.  
      The observable pipeline terminates here on "onError" and no more emissions will occur, just like "onComplete" but if we get encountered by an error and "onError" method is invoked and "onComplete" will never execute.

We can figure this three method like three channels.   
![image info](./imgs/Schermata_20240904_110514.png "Observer method channels")

Now take a look to how Observer interface:

    interface Observer<T> {
       void onSubscribe(Disposable d);
   
       void onNext(T t);
   
       void onError(Throwable e);
   
       void onComplete();
   }

We had already discussed that method except the "onSubscribe" method.  
**onSubscribe** this method is called once when an observer subscribes to an observable.  

See th ObservableAndObserver java class in "courses.basics_strong.reactive.section17" package.

### Crating Observable

### Crating Observer

### Hot and Cold Observables

### Connectable Observable

### Observable Variants

### Dispose