package courses.in28min.funcprogramming.func_interfaces;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FuncInterface01Basic {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        numbers.stream()
                // This is a lambda expression
                // we are injecting code instead of value in a method
                // the code represents what we are used to do in structured manner
                //
                // boolean isEven(int number) {
                //      return number % 2;
                // }
                .filter(x -> x % 2 == 0)

                // This is a lambda expression
                // the equivalent code here is
                //
                // int square(int number) {
                //      return number * number;
                // }
                .map(x -> x * x)

                // This is a method-reference.
                // A Method-Reference is a simplification of a Lambda-Expression
                // we are referencing a method (in this case a static method)
                // we can also reference a local method or an instance
                // this one refer to local instance ==> this::methodToInvoke
                // this one refer to an instance ==> instance::methodToInvoke
                // obviously the methods-reference works in conjunction with the number of parameters and the return
                .forEach(System.out::println);

        // WE CAN SEE THE SAME CODE USING FUNCTIONAL INTERFACES:
        // * Predicate: used to make a test.
        // * Function: used to process data and return something
        // * Consumer: used to some with input data without any return
        // * Supplier: NOT SHOWN HERE is used to supply a value when there is no other value
        // Java offer many implementations of FunctionalInterface in addition to those already mentioned, such as:
        // BiFunction, BiConsumer, BiPredicate, BinaryOperator, and so on...
        Predicate<Integer> isEvenPredicate = x -> x%2 == 0; // this is an instance of Predicate interface using lambda
        Function<Integer, Integer> squareFunction = x -> x * x; // this is an instance of Function interface using lambda
        Consumer<Integer> sysoutConsumer = x -> System.out.println(x); // this is an instance of Consumer interface using lambda

        // FUNCTIONAL INTERFACES has a one single abstract method interfaces marked with "@FunctionalInterface" annotation
        // Predicate, Function, Consumer and Supplier are Java's interfaces annotated with "@FunctionalInterface"
        // We can easily see this using code instead of lambada
        // For example we can write the even-predicate in a legacy manner
        //
        // Predicate<Integer> isEvenPredicateLegacy = new Predicate<Integer>() {
        //     @Override
        //     public boolean test(Integer number) {
        //         return number % 2 == 0;
        //    }
        // };
        //
        // In background LAMBDAS are automatically converted in java class
        // A Lambda is an object because in Java Objects are first-class citizen

        numbers.stream()
                // a Predicate because we are testing something
                // this means: we accept a value and return boolean
                .filter(isEvenPredicate)

                // a Function because we are accepting something and returning back other
                // this means: we accept a value do something with it and return back the result
                .map(squareFunction)

                // a Consumer because we consume the value without returning back nothing
                .forEach(sysoutConsumer);
    }
}
