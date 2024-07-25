### Functional Programming Techniques
These techniques are very useful to write better functional code

#### Design APIs In Functional Way
From java 9 onwards we have default methods in interfaces and APIs take advantage of default methods to make both **"Function Chaining"** and **"Function Composition"** techniques possible in java.

#### Function Chaining
Function Chaining (aka Method Chaining) is a technique where each method returns an object allowing the calls to be chained together in a single statement without requiring variables to store the intermediate results.
Basically this technique is used to simplify the code when multiple functions are applied in a row.  
When we chain function, the first one get executed and then will get executed the second one and so on.  
The chain will be triggered when we'll call the function abstract method.

        obj.func1().func2().func3()....funAbstractMethod()
        obj.performThis().andThenThis().andThenThis()....funAbstractMethod()

Will use the "default" interface method in order to do something like this:

        @FunctionalInterface
        interface MyInterface<T> {
            void doSome(T t);

            default MyInterface<T> theChainMethodName(MyInterface<T> next) {
                return (T t) -> {
                    // we refere here to the first called 
                    this.doSome(t);

                    // now we invoke the next one
                    next..doSome(t);
                }
            }
        }

**Important!!** That snippet is missing of one important thing: *"Fail Fast"*.  
Fail Fast is the capability to immediately fails if something is not configured correctly.
Look the example code "Technique01_Chaining" under "section8.techniques" package for more details.

#### Function Composition

#### Closures
#### Currying
#### Lazy Evaluation
#### Tail Call Optimization