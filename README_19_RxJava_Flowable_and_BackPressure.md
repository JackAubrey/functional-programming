## RxJava | Flowable and BackPressure
![image info](./imgs/Schermata_20240918_144948.png "Mind Map")

### Producer/Consumer problem: Need of BackPressure
Why there is a need for having something that can provide solution to BackPressure?  
What is back pressure?  
We all know the general flow of emissions in the observable chain. The observable emits the item and then the item travels through the complete chain and after that next comes right, so this is the normal flow.  
![image info](./imgs/Schermata_20240918_145517.png "Normal chain flow")  
But when we add concurrency or parallelization in this chain then  multiple parts of this chain are operated on different threads.  
For example if we put an observe on call in between this chain it will be like the upper part of this chain is processed by some thread and the lower part is processed by some other thread.  
Now an emission is no longer strictly being handed downstream one at a time from the source all the way to the observer before starting the next one.  
This is because once an emission hits a different scheduler through "observeOn(...)".  
The source is no longer in charge of pushing that emission to the observer.  
Therefore the source starts pushing the next emission, even though the previous emission may not have reached the observer yet.  
So this way producer may produce faster than the consumer can consume.  

![image info](./imgs/Schermata_20240918_150201.png "Multi Thread chain flow")  

See "ProducerConsumerProblem" example on "courses.basics_strong.reactive.section24" package.

### BackPressure with Flowable/Subscriber
As we have Observable that emits to an Observer we also have **Flowable and Subscriber.**  
Flowable emits and the Subscriber listen to those emissions.  
We talked about the Producer/Consumer problem and need of BackPressure, but how we can apply it in the chain?  

![image info](./imgs/Schermata_20240918_154630.png "Flowable/Subscriber simple chain")

So Flowable is the replacement of Observable where we need BackPressure.  
See "BackPressure" example on "courses.basics_strong.reactive.section24" package.  
See "BackPressureWithLimits" example on "courses.basics_strong.reactive.section24" package.

### Flowable Creation and BackPressure strategies
As we already said pretty much all the Observable factories and operators apply to Flowable.  
*They are same but still there is one critical difference between "Observable.create(...)" and "Flowable.create(...)":* with Flowable you must specify a back pressure strategy as a second argument.  
There are different kind of  Buffer Strategies:  
1. **BUFFER**: buffer queues up emissions in an unbounded queue.  
   Until then the downstream is able to consume them. Once the downstream is ready it flushes the buffer, but it can cause an out of memory error if the queues gets too large.
2. **DROP**: Drop if the downstream cannot keep up this will ignore the upstream emissions, and will not queue anything while the downstream is busy.
3. **ERROR**: Error signals are missing back pressure exception the moment the downstream cannot keep up with the source.
4. **LATEST**: This will keep only the latest emission until the downstream is ready to receive it.
5. **MISSING**: It results in no back pressure implementation at all.  
   The downstream must deal with the back pressure overflow.  

See "FlowableCreation" example on "courses.basics_strong.reactive.section24" package.

### Flowable vs Observable