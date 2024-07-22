package courses.basics_strong.funcprogramming.section6;

import java.util.function.Function;

public class FP03ConstructorReference {
    public static void main(String[] args) {
        // Suppose we need to execute some tasks parallel.
        // So we will be needing threads to execute those tasks parallel.
        // Let's create a function that can generate threads for us.

        // So we pass runnable to this function and here we create the object by passing this runnable object
        // to the thread constructor and this function returns a thread
        //          r -> new Thread(r);
        //
        // We use the functional interface Function<T,R> to accomplish this work.
        // In this first version we use a lambda
        Function<Runnable, Thread> threadGeneratorUsingLambda = (r) -> new Thread(r);

        // Now let's show the same example but using Constructor Reference
        //  - As you can see the "::new" doesn't need any input parameter.
        //  - "::new" is a keyword that means "new OfSomeObject()"
        //  - since the constructor of class has the following signature
        //          public NameOfClass() // empty parameter constructor (equals for default constructor)
        //          public NameOfClass(<One Or More parameters>) // a parametrized constructor
        //    and since the Thread constructor is:
        //          public Thread(Runnable task)
        //    and since Function wants an Input and an Output
        //
        //          "(r)" our input will be implicit
        //          "->" lambda arrow in no more needed
        //          also the return is implicit
        //
        //    so
        //          from this "(r) -> new Thread(r)"
        //          now we are able to simply write: "Thread::new"
        //
        Function<Runnable, Thread> threadGeneratorUsingConstRef = Thread::new;

        // Now let's see in action some example

        // Very old style and ugly
        Runnable task1 = () -> System.out.println(Thread.currentThread().threadId()+" - Task 1 executed");
        Runnable task2 = () -> System.out.println(Thread.currentThread().threadId()+" - Task 2 executed");

        Thread thread1 = threadGeneratorUsingConstRef.apply(task1);
        Thread thread2 = threadGeneratorUsingConstRef.apply(task2);

        thread1.start();
        thread2.start();

        // A bit more in functional way
        threadGeneratorUsingConstRef
                .apply( () -> System.out.println(Thread.currentThread().threadId() + " - Task 3 executed"))
                .start();

        // using the static method reference. We create some using a loop
        for(int i=0; i<10; i++) {
            threadGeneratorUsingConstRef
                    .apply(FP03ConstructorReference::print)
                    .start();
        }

        // using the method reference. We create some using a loop
        MyPrinter printer = new MyPrinter();
        for(int i=0; i<10; i++) {
            threadGeneratorUsingConstRef
                    .apply(printer::print)
                    .start();
        }
    }

    static void print( ) {
        System.out.println(Thread.currentThread().threadId() + " - Static Method Reference - Task 4 executed");
    }
}

class MyPrinter {
    public void print() {
        System.out.println(Thread.currentThread().threadId() + " - Method Reference- Task 5 executed");
    }
}
