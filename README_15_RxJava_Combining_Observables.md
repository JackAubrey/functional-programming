## RxJava | Combining Observables
Imagine an environment where you are having a lot of microservices running on different machines, and you want to club responses from those services and show result on UI.  
In these kind of scenarios these operators are very helpful.  
There are various operators and factories for this like "concat", "merge", "zip", "amb" and many more.  
![image info](./imgs/Schermata_20240906_121701.png "Combining Observables")

### Merging and Concatenating
We have "merge" method for merging and "concat" for concatenating multiple observables and obviously that's not the only difference.  
All combining operators are about merging the emissions of multiple observables to have a single observable source, but what kind of emissions this resulting observable will emit, it depends on what operator we are using to merge them.  
**NOTE:** Using source that emit data from the same thread for example using "just(...)" factory method, both method produce the same result. Merge will produce a result equals to what we expected from concat.  
Unlike if we use sources that works on separated thread for example using "interval(...)" factory method, the "merge" method works as we expect, but the "contact" method since it need to work sequentially, will never emit the second source until the first one ends. So in case like "interval" we need to limit (using the "take()" method) the first source.

The "Observable" interface offer some factory method for "merge" and "concat", but we can also do the same using the "mergeWith" and "concatWith" methods provided by the Observable instance created via one of Observable creation factory method.

    // Via Factory Method
    Observable.merge(s1, s2)

    // Via Observable Reference
    Observable<String> s1 = Observable.just(...);
    Observable<String> s2 = Observable.just(...);
    s1.mergeWith(s2);

- **merge(Observable o1, Observable o2)** merge takes two observables but that's not the only signature we have.  
  ![image info](./imgs/Schermata_20240906_122808.png "Merge")
  If an error occur (the X in the marble diagram) no further emission will be emitted.

- **concat(Observable o1, Observable o2)** unlike the merge, concat maintains the sequential order so you will get the emissions in order you have passed the observables to the concat method.
  ![image info](./imgs/Schermata_20240906_123216.png "Concat")
  Also for concat we have multiple overloads method and in case of error the emission ends.

### FlatMap vs ConcatMap

### Disposing of duplicated emitting source: the "amb()" method

### Zip vs CombineLatest

### Grouping and Grouped Observables