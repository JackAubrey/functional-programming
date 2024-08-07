### Section 1 Mind Map
![image info](./imgs/Screenshot_20240717_113312.png "Mind Map")
In this section of the course, we will understand the building blocks of functional programming: lambda and functional interfaces.  
**Lambda is a cute little algorithm and functional interface is the way to execute them.**

### Functional interfaces
Functional interfaces are important because we can't write lambda without them, they are behind every lambda.  
What is a functional interface? As its definition says:
**functional interface can only have one abstract method so any interface that is having single abstract method is a functional interface**  
Annotating an interface with **@FunctionalInterface** is not necessary at all but is recommended for:
- give an evidence about the scope that interface
- prevents error like put more than one abstract method inside it.

The restriction to a Functional Interface to have a SINGLE ABSTRACT method because this is required to execute lambda without any ambiguity.

For example:

    // this a normal interface with two methods
    // is NOT a functional interface
    public interface MyInterface {
        void myMethod1();
        int myMethod2();
    }

    // if wi try to annotate it we'll obtain an error
    @FunctionalInterface
    public interface MyInterface {
        void myMethod1();
        int myMethod2();
    }

    // THIS IS A VALID Functional Interface
    public interface MyFunctionalInterface {
        void myMethod();
    }

    // THIS IS A VALID Functional Interface also annotated (that is always better)
    @FunctionalInterface
    public interface MyFunctionalInterface {
        void myMethod();
    }

After java 8 we can assume functions as they are variable assignments.  
For example:

    @FunctionalInterface
    public interface MyFunctionalInterface {
        void myMethod();
    }

    // we are asigning a function to a reference like we are used to do with normal variable
    MyFunctionalInterface myFunc = () -> System.out.println("Hello");

    // invoking the interface method we are going to execute the method implementation assigned bifore
    myFunc.myMethod();

Interesting right? More interesting is the capability to use lambda on the fly.

### Lambda on the fly
Take back the previous example:

#### Standard way
Previous seen standard way

    @FunctionalInterface
    public interface MyFunctionalInterface {
        void myMethod();
    }

    MyFunctionalInterface myFunc = () -> System.out.println("Hello");
    myFunc.myMethod();

#### On The Fly way
Now we are going to use the usage of lambda on the fly

    // we omitted the method visibility because depends on your code
    void onTheFly(MyFunctionalInterface func) {
        func.myMethod();
    }

    // we place here the code just as example, but you can put whenever you want in accordion with java rules
    // We are using on the fly a lambda
    onTheFly( () -> System.out.println("Hello On The fly") );


### Lambda are Smart
- Smart anonymous functions they use type inference
- Dynamic type language. Invoke dynamic functionality.  
  Starting from Java 7, the compiled code is able to invoke "dynamically" some pieces of code.  
  Allows dynamic language to bind symbols at runtime
- Also, reducing the length of the code, lambdas also help optimize the memory.

### Lambda Benefits
- Pass Behavior as parameter.   
  We can pass behavior as parameters just like we pass data as parameters.
- Lambda enables declarative programming.  
  The programmer says what to do rather than how to do. In this way a lot of things can be hidden.
- Using expressions instead of statement, and thanks the JVM capabilities, the code written using declarative style is thread safe and can actually work fine in multi-threaded environments.

**Note** Functional Programing is a **subset** of Declarative Programming

### Lambda have
- List of Parameters
- An arrow "->" which separates the list of parameters from the body of the lambda function
- The body of the lambda

**Remember:** on the contrary a function have
- Access Modifier (public, private, etc...)
- Return Type
- Name of Function
- Parameters List and Parameters Type
- Method Body
- Return Statement (if something returns)

### Predefined Java Functional Interfaces
Java provide us set of predefined Functional Interface each one with its own scope.
![image info](./imgs/Screenshot_20240719_113338.png "Predefined Java Functional Interfaces")
- **Predicate**: to test a condition. This is one of most used Functional Interface.
    - Accept input parameters
    - Return a boolean
    - The Predicate interface abstract method signature is: **boolean test(T var)**
- **Consumer**: to consume something
    - Accept input parameters
    - Return NOTHING
    - The Consumer interface abstract method signature is: **void accept(T var)**
- **Function**: to accept something and return a resul.  
  This is one of most used Functional Interface and typically used for transformations.
    - Accept input parameters
    - return a result
    - The Function interface abstract method signature is: **R apply(T var)**
- **Supplier**: to supply a value
    - No input parameters
    - return a value
    - The Supplier interface abstract method signature is: **T get()**

Remember if a functional interface is having reusable signature we don't need to create different functional interfaces having same signature.
We can reuse it.

    @FunctionalInterface
    interface SimpleMathOperator {
        void operate(int a, int b);
    }

    SimpleMathOperator sum = (a, b) -> a + b;
    SimpleMathOperator multiply = (a, b) -> a * b;

On that basis java has already provided most general reusable signatures.  
These are added to **java.util.fun** package.  
So this function package that contains already defined generic functional interfaces.
In this provided list, you can see from left to right:
- The functional interface
- Its description where "T" and "R" are the generics
- The primitive specializations.  
  These are used for primitive type: Int, Long and Double.  
  When you have to works with primitive data, use its specialization because are more efficient avoiding the In/Out Boxing usage.

![image info](./imgs/Screenshot_20240719_121243.png "Java Predefined Functional Interfaces list 1")

In this list we can see a first Functional Interface never met before: **UnaryOperator**  
The UnaryOperator<T> is an extension of Function<T,R> where T and R has same type.  
It represents an operation on a single operand that produces a result of the same type as its operand.

The following list show us some other very useful Java Predefined Functional Interfaces.  
![image info](./imgs/Screenshot_20240719_122534.png "Java Predefined Functional Interfaces list 2")
- BiFunction<T, U, R> accept two input parameters and return something.
- BiPredicate<T,R> accept two input parameters and return the boolean test result.
- BiConsumer<T,R> accept two input parameters and return nothing.
- BinaryOperator<T> is an extension of BiFunction<T, U, R> where all three types are all the same.

### Functional interfaces: Java Generic syntax
To understand functional interfaces from jdk library you should have a fair knowledge of java generics.

This is NOT a Functional Generics Interface. **It's a normal Functional Interface**

    @FunctionalInterface
    public interface FunctionalInterface {
        String execute(String s);
    }

This IS a Functional Generics Interface because we are providing it of Generics.

    @FunctionalInterface
    public interface FunctionalGenericInterface<T,R> {
        R execute(T t);
    }

Both T and R are simply to-uppercase letters used to indicate that we are using Java Generics.  
Which kind of letter you'll use is not a mandatory. Typically, these are what java library is used to use.
Let's take look to a couple of examples:

    FunctionalGenericInterface<String, String> fun1 = s -> s.substring(1,5);
    String substring = fun1.execute("Hello World"); // it will contains "Hello"

    FunctionalGenericInterface<String, Integer> fun2 = s -> s.length();
    Integer length = fun2.execute("Hello World"); // it will contains "11"