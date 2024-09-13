## RxJava | Subjects Replaying and Caching  
![image info](./imgs/Schermata_20240912_153920.png "Mind Map")
Multi-Casting is a way to reduce duplicated when you multicast an event you send the same event to all the downstream observers or subscribers.  
You don't retrieve the event again from the source. This is useful when you're doing an expensive operation like a network request.  
You don't want to repeatedly execute identical network requests for each observer or subscriber you just want to execute one and then multicast the results.  
So Subjects Replaying and Caching are the tools provided by RxJava to achieve Multi-Casting.

### Replaying and Caching
Both Replaying and Caching are a weird combination of hot and cold observable they does not re-emit but the emissions will be cached to be replayed.  

1. **"Replay"** is used to replay the emissions from the start for an observer coming late.  
   Basically we have replay method that returns a ConnectableObservable which shares a single subscription to the current observable that will replay all of its item and notification to any future observer.  
   ![image info](./imgs/Schermata_20240913_103840.png "Replay")
   So replay operator is a powerful way to hold on to previous emissions within a certain scope and re-emit them when a new observer comes in.  
   Using "replay" method to an Observable we will obtain a ConnectableObservable so this means the firing event emission stars from the "connect" method invocation. We can also use "autoConnect" method to automatically starts the connection and emitting.  

    **Replay Caching** is the capability of the "reply" method to limit the replaying of emission only to a certain number of latest emissions.
   The "reply" method offer many signatures in order to play with caching behaviour.
   - Simplest one is specifying the buffer size.
   - Or we could prefer specify the period before emissions must live before their eviction.
   - Or we can use both period and buffer size
   - Or specify a function as selector (this is quite different as behaviour).
   - A combination of selector, buffer and period and so on... 

   See both "Replaying" and "ReplayingAndXXX" examples on "courses.basics_strong.reactive.section22" package.

2. **Caching** is very similar to "replay" but "cache" operator return an Observable and we don't need to use "autoConnect" or "connect" methods.  
   The caching stars automatically when we subscribe to an Observable.  
   Another different is unlike "reply" method, "cache" doesn't offer overloads.

### Subjects

### Adding Emissions using Subjects

### Subject Implementations