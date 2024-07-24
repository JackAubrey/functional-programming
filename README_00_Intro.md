Java is an object-oriented programming language.
We play with objects the state of objects we inherit properties we combine and compose objects.

In functional programming we play with functions and lambda 

In lambda calculus functions can be treated as a value if it's a pure function so whenever f x is pure we can simply replace it with y anywhere.  
This is a powerful concept which we'll learn in this course

### Object Oriented Programming
Objects: In Java the Objects are first-class citizen

### Functional Programming
Functions: Thanks to this new paradigm, now we can have Functions as first-class citizen
A Function is treated like a value. y = f(x)

### Lambda
A Lambda is basically a building block of functional programming
Lambda has no name: Anonymous
**Don't forget that we can only convert lambdas for the interfaces having only one abstract method.**

For using lambda we must understand one thing that a (standard) function has four properties:
    - first is the name of the function
    - second is parameters list
    - third is body of the function
    - fourth is a return type

So lets look this. We have a tipical Runnable class implementation:

        class MyRunnable implements Runnable {
            public void run() {
                System.out.println("Body of Method");
            }
        }

Let's see how we can play with these properties in java 8 and beyond
We are going to remove this outer class because this is an unnecessary noise compiler.

            public void run() {
                System.out.println("Body of Method");
            }

Remove the name of the method because as we know runnable interface is having a single abstract method.

            public void () {
                System.out.println("Body of Method");
            }

Remove access modifier because the methods should always be public.

            void () {
                System.out.println("Body of Method");
            }

Remove the return type because compiler can guess the return type beyond java 8 by using type inference.
Since function can only return one value at a time and compiler can guess easily based on the context.
As you can see, we are very close now to a lambda function.

            () {
                System.out.println("Body of Method");
            }

Now using the arrow simbol (used to indicate lambda) we convert the remaining part to a lambda.
    
            () -> {
                System.out.println("Body of Method");
            }

In our case since the body contains a single statement we don't need this pair of curly brackets
    
            () -> System.out.println("Body of Method")

### Few good reasons for learning functional programming
- What to do rather than how to do
- Code is concise and Simple
- Less error prone
- Add to your skill set

#### What to do rather than how to do
In functional programming user tells the program what to do rather than how to do like in sql or unix  
we write the program under the hood, and then we just pass the command and the command gets executed.

#### Code is concise and Simple
Functional programming is an abstraction which hides the complexities under the hood and provide us clean interface this is why functional code is concise.

#### Less error prone
Functional programming emphasizes not to share mutable state of objects. 
Keep the functions pure and side effect free. 
This makes it easier to do complex code even in multi-threading environments and less error-prone.

#### Add to your skill set
A lot of programming languages are using functional style of code.