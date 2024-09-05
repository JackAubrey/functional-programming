## RxJava | Operators

### What are Operators?
Basically, operators are something which allow us to transform modify merge reduce data emitted by the source and return a new observable with transformed data.  
We can say operator is a function that takes upstream Observable<T> and returns a downstream Observable<R>.
![image info](./imgs/Schermata_20240905_153516.png "Operators")
For example the "filter" operator will receive every element from the upstream observable, and then it will return an observable by forwarding only those elements that matches the given predicate.  
So this filter works as observer for the upstream observable, and it will work as a producer or an observable for the upcoming operation or the upcoming observer.  
![image info](./imgs/Schermata_20240905_154234.png "Filter Operator Example")

Take a look to "OperatorsDemo" on "courses.basics_strong.reactive.section18" package.

### Types of Operator


### Operators in Action