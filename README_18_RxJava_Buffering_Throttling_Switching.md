## RxJava | Buffering Throttling and Switching
Are used to batch up emissions into data chunks or emit in particular time slice.  
![image info](./imgs/Schermata_20240917_103258.png "Mind Map")

### Buffer and Window
We saw how RxJava simplified concurrent and async tasks.  
Now while introducing concurrency and using Schedulers with Observables sometimes there might be a situation where Observables emit faster than an Observer can consume.  
Such type of situation can be handled by using "flowable" instead of "observables".  
**Flowables** are actually observables with the property called **back pressure**.  
![image info](./imgs/Schermata_20240917_104124.png "Flowables")
But some events cannot be handled with it, for example user input events we can control the user itself with program to slow down his inputs.  
In these cases RxJava provides some operators to batch up emissions into data chunks or image in particular time slice so the consumer can consume more easily.  
1. **"Buffer":** is used to:
   1. First gather emissions in specified scope.
   2. Emit each batch or group as a collection type instead of emitting one item at a time  
   
   There are many ways to define scope though for example specifying fixed size to batch emissions.  
   The "buffer(...)" operator has some overloads:
   1. "buffer(int count)" simply specify the buffer size. It returns an "Observable<List<T>>".
   2. "buffer(int count, int skip)" like the previous one but permit to skip data. Skip means  how many items emitted by the current Observable should be skipped before starting a new buffer. It returns an "Observable<List< T >>".
   3. "buffer(Supplier< U > sup)" permit to specify the accumulator type used to buffer data. For example, we would use an HashSet instead of the default List. It returns an "Observable< U >".
   4. "buffer(long timeSpan, TimeUnit unit)" span data grouped by time. It returns an "Observable<List<T>>".
   5. "buffer(ObservableSource boundaryIndicator)" the emitting of the boundary observable causes the returned ObservableSource to emit the latest buffer and complete. 

   There are a lot of method overloads...  
   See "Buffering" example on "courses.basics_strong.reactive.section23" package.

2. **"Window"** this operator is somehow similar to buffer.  
   Difference is:
   1. It buffers into other observables rather than collections.
   2. It yields an observable of observables in this manner it is much like grouped observables.
   3. Every observable will cache their emissions for specified scope and then flush once subscribed.
   4. Hence instead of waiting for each list or collection to be emitted with this approach we get emissions immediately as soon they become available.  

   There are many ways to define scope though for example specifying fixed size to batch emissions.
   The "buffer(...)" operator has some overloads:
   1. "window(int count)" simply specify the window buffer size. It returns an "Observable<Observable<T>>".
   2. "window(int count, int skip)" like the previous one but permit to skip data. Skip means  how many items emitted by the current Observable should be skipped before starting a new buffer. It returns an "Observable<Observable< T >>".
   3. "window(long timeSpan, TimeUnit unit)" span data grouped by time. It returns an "Observable<Observable<T>>".
   4. "window(ObservableSource boundaryIndicator)" the emitting of the boundary observable causes the returned ObservableSource to emit the latest buffer and complete.

   There are a lot of method overloads...  
   See "Window" example on "courses.basics_strong.reactive.section23" package.

3. **"Throttle First" and "Throttle Last"**

4. **"Switch Map"**

### Throttle Operator

### Switch Map