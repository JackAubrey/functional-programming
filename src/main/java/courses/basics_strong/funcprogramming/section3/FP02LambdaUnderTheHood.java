package courses.basics_strong.funcprogramming.section3;

import courses.basics_strong.funcprogramming.section3.fi.MyFunctionalInterface;

public class FP02LambdaUnderTheHood {
    public static void main(String[] args) {
        // In order to understand what lambda do under the hood let's take a look to this example.
        //
        // we are implementing twice the same interface in order to have two implementations.
        // but if we look the compiled code will see two anonymous class generated.
        // There are a lot of thing that are wrong.
        MyFunctionalInterface anonymousInnerClassFunc1 = new MyFunctionalInterface() {
            @Override
            public void myMethod() {
                System.out.println("Anonymous Inner Class Implementation 1");
            }
        };

        MyFunctionalInterface anonymousInnerClassFunc2 = new MyFunctionalInterface() {
            @Override
            public void myMethod() {
                System.out.println("Anonymous Inner Class Implementation 2");
            }
        };

        // Now how lambda can help us?
        //
        // Look! Our code is much more concise, no inner class created.
        // This is possible because starting from Java 7, the compiled code is able to invoke "dynamically" some pieces of code.
        MyFunctionalInterface lambdaFunc1 = () -> System.out.println("Lambda Implementation 1");
        MyFunctionalInterface lambdaFunc2 = () -> System.out.println("Lambda Implementation 2");
    }
}
