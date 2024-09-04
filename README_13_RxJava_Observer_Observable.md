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

See the ObservableAndObserver java class in "courses.basics_strong.reactive.section17" package.

### Crating Observable
On the examples "ObservableAndObserver" and "HelloRxJava" we have created on "Observable" using its factory method "Observable.create(...)", but there are various ways to create an observable.  
1. **create(...)** we already met this one, it is rarely used preferring the "just" method.  
   This method need a lambda where we have to invoke manually: onNext, onComplete and onError.
2. **just(T value)** it take one or more values to push, maximum 10 values. The onNext, onComplete and onError will be implicitly done by this.
3. **fromIterable(Iterable<T> iterable)** like "just" method did, this is used to push value directly but taking it from an Iterable. We don't care about to handle the "onXXX" methods.  
   Note: there are few methods like this one with same concept: "fromArray", "fromCollection", "fromStream", "fromSupplier", "fromFuture" and many others.
4. **range(int starValue, int totalCount)** it range based emission so it takes start value and the total count, we also have its long equivalent **rangeLong**.
5. **interval(....)** exists different variants of this method. we can pass an initial delay or not, we can pass a custom scheduler, but all these variants wants a period and a TimeUnit.  
   NOTE: all these variants returns a Long.
   These methods are designed to send an event every period of a certain time unit.  
   *They continue to emit values until the subscription won't dispose.  
   These variants are time based and need to work on separated thread.* 
6. **intervalRange(...)** is very similar to "interval" and works similar to them. Despite the "interval" these variants:
   1. always wants a "starValue" and a "totalCount"
   2. when the count ends, automatically send the "onComplete" event.
7. **fromFuture(Future<T> future)** PAY ATTENTION to use it, is not easy to use.
8. **empty()** if we want to create an empty observer we invoke the empty method.   
   It will emit nothing it will just call the on complete action.
9. **never()** it also emits nothing, and it also never calls "onComplete" action. This observable is used for testing to prove that no emissions are coming from it.
10. **error(ExSupplier exSup)** needs of an Observer able to handle the "onError" event. This is used to rise errors.
11. **defer(Supplier<ObservableSource> observableSupplier)** it creates a separate state for each observer. Useful when for example we wants observes a list that can change.
12. **fromCallable(Callable)** similar to "fromFuture" with also the same recommendations.

See the CreatingObservable java class in "courses.basics_strong.reactive.section17" package.

### Crating Observer
We have different ways to create an Observer.  
The first way that we already discussed is by creating the anonymous inner class where we implement all Observer interface methods.  
In this way we create the Observer, and then we pass it to the Observable subscribe method.  

But we don't always need to go with this approach we don't need to implement the observer interface on our own.  
We have different signatures of the Observable subscribe method to directly create observer.  
We can pass a single consuming lambda in order to take the "onNext" value, or we can pass two lambdas to take the "onNext" and "onError", or three lambdas to also take the "onComplete" event.

See the CreatingObserver java class in "courses.basics_strong.reactive.section17" package.

### Hot and Cold Observables
How the observables are classified and what are Hot and Cold categories?  
How observables behave when there are multiple Observers?  
We have two kind of observables: **Hot** and **Cold** Observables.  
An Observable is the source of emission of data, and it is possible that one observer subscribes to the source gets the data but after that the data is changed or modified.  
Now if there are more observers will the other observers get the updated data or will they get the previous data.  
*This is the criteria that decides an observable is a cold observable, or it is a hot observable.*  

- **Cold Observable** When we use a "Cold Observable" and we modify data the new Observer subscriptions did after the data update will receive all data from the scratch. Observers that was subscribed before the data has update, will not receive them.
- **Hot Observable** Is an observable that starts when we create it.  
  It doesn't even wait for any subscription and all the observer get the same data from the observable.  
  For any new subscription they **don't run from start**. All the observer will get the emitted item at once, so they are basically independent of observer subscription.  
  We can always convert a Cold to Hot Observable using the "publish" methods provided by the Observable. This method returns a ConnectableObservable where we invoke the "connect" method to start catching the fired events.

See the ColdObservable and HotObservable java class in "courses.basics_strong.reactive.section17" package.

### Connectable Observable
This kind of Observable are observables that convert Cold Observable to Hot Observable. We can obtain them using the "publish()" method after an Observable creation.  
NOTE: All Observable created provide that method, but actually, and I don't know why (I'm waiting the teacher response) only Observable instance provided by time based method (like "interval") works.

### Observable Variants

### Dispose