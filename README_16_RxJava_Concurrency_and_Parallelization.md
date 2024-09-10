## RxJava | Concurrency and Parallelization  
![image info](./imgs/Schermata_20240909_162709.png "Mind Map")

### How to?
Till now, we saw observables observers subjects and almost every important component in RxJava running on a single main thread.  
But you must be feeling like where is the main essence of using RxJava it is in making the code async.  
You all know how powerful parallel processing is, you can run tasks or subtasks in parallel rather than going serially and waiting for one task to complete to run the next task.  
But RxJava observable has a contract:

    // RxJava Observable Contract
    From observable, the emissions must be passed sequentially and one at a time.
    Emissions cannot be passed by an observable concurrently or in parallel.  

**If this is a contract then how we can make the task run a sync in an RxJava program?**  
Look the "ConcurrencyInRxJava" example that contains details about this theme and placed on "courses.basics_strong.reactive.section20" package.

### Schedulers

### Subscribe On

### Observe On

### Achieving Concurrency using Flat Map