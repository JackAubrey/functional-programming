package courses.basics_strong.funcprogramming.section1;

import courses.basics_strong.funcprogramming.section1.data.MyFunctionalInterface;

public class FP01JavaParadigmEvolution {
    public static void main(String[] args) {
        // Previous Java 8 for example if we need to create a runnable class we need to
        // create a class that implements Runnable and pass it to a thread
        seeExampleWithRunnableClass();

        // thanks to anonymous classes we were able to reduce the code a bit
        seeExampleWithRunnableAnonymousClass();

        // thanks lambda introduced from Java 8 the code is very concise
        seeExampleWithLambda();

        // an example of usage of lambda on the fly
        seeExampleWithLambdaOnTheFly();
    }

    private static void seeExampleWithRunnableClass() {
        /**
         * A Class that implements Runnable
         */
        class MyRunnable implements Runnable {
            @Override
            public void run() {
                System.out.println("MyRunnable ThreadId = "+Thread.currentThread().threadId());
            }
        }

        // Now lets go to use this class
        Thread t = new Thread(new MyRunnable());
        t.start();
    }

    private static void seeExampleWithRunnableAnonymousClass() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Runnable ThreadId = "+Thread.currentThread().threadId());
            }
        });
        t.start();
    }

    private static void seeExampleWithLambda() {
        Thread t = new Thread( () -> System.out.println("Lambda ThreadId = "+Thread.currentThread().threadId()) );
        t.start();
    }

    private static void seeExampleWithLambdaOnTheFly() {
        onTheFly( () -> System.out.println("Hello on the fly") );
    }

    /**
     * Used by {@link #seeExampleWithLambdaOnTheFly()}
     * @param func a functional interface
     */
    private static void onTheFly(MyFunctionalInterface func) {
        func.myMethod();
    }
}

