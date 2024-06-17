package courses.in28min.funcprogramming.func_interfaces;

import java.util.List;
import java.util.Random;
import java.util.function.*;

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
        // * Predicate<T>:  used to make a test. Given an input return a boolean.
        //                  The interface signature is "boolean test(T var1)"
        // * Function<T,R>: used to process data and return something. Given an input, return an output
        //                  The interface signature is "R apply(T var1)"
        // * Consumer<T>:   used to some with input data without any return. Given an input do not return anything
        //                  The interface signature is "void accept(T var1)"
        // * Supplier<T>:   Typically used like a factory, is used to supply a value when there is no value. Given nothing return something
        //                  The interface signature is "T get()"
        //
        // Java offer many implementations of FunctionalInterface in addition to those already mentioned, such as:
        // BiFunction, BiConsumer, BiPredicate,
        // UnaryOperators, BinaryOperator, and so on...
        //
        // NOTE: The XxxOperator are similar to XxxFunction with main difference: ALL XxxOperator generics has the same type.
        //       Are similar to a XxxFunction short-cut. Look the BinaryOperator definition below
        //
        //          BinaryOperator<T> extends BiFunction<T, T, T>
        //
        Predicate<Integer> isEvenPredicate = x -> x%2 == 0; // this is an instance of Predicate interface using lambda
        Function<Integer, Integer> squareFunction = x -> x * x; // this is an instance of Function interface using lambda
        Consumer<Integer> sysoutConsumer = x -> System.out.println(x); // this is an instance of Consumer interface using lambda
        Supplier<Integer> rndSupplier = () -> new Random().nextInt(100); // NOT USED below. Just to show it

        // There are other useful FunctionalInterfaces
        //
        //  *   BiConsumer<T,U>:      Consume two given input value
        //  *   BiFunction<T,U,R>:    Given two input value return something
        //  *   BiPredicate<T,U>:     Make a test upon the given two values
        //
        //  *   ObjDoubleConsumer   : a consumer that consume a generic object and a double
        //  *   ObjIntConsumer      : a consumer that consume a generic object and a integer
        //  *   ObjLongConsumer     : a consumer that consume a generic object and a Long
        //
        //  *   ToDoubleBiFunction  : a bi-function that given two generics input return a double
        //  *   ToDoubleFunction    : a function that given a generic input return a double
        //  *   ToIntBiFunction     : a bi-function that given two generics input return an int
        //  *   ToIntFunction       : a function that given a generic input return an int
        //  *   ToLongBiFunction    : a bi-function that given two generics input return a long
        //  *   ToLongFunction      : a function that given a generic input return a long

        // BiConsumer example
        BiConsumer<String, Integer> biConsumer = (s, i) -> {
           for(int x=0; x<i; x++)
               System.out.println("Hello "+s);
        } ;

        // BiFunction example
        BiFunction<String, Integer, String> biFunction = (s, i) -> {
            String result = "";

            for(int x=0; x<i; x++)
                result += "\nHello "+s;

            return result;
        };

        // ObjDoubleConsumer example
        ObjDoubleConsumer<String> objDoubleConsumer = (s, d) -> System.out.println("Consume a String ["+s+"] and a double ["+d+"]" );

        // ToDoubleBiFunction example
        ToDoubleBiFunction<String, Integer> toDoubleBiFunction = (s,i) -> {
            return (double) s.length() / i;
        };

        // BiPredicate example
        BiPredicate<String, Integer> biPredicate = (s, i) -> s.length() == i;

        // Following are "PRIMITIVE FUNCTIONAL" interface.
        // NOTE: >> The typical generic type interface boxing/unboxing is inefficient.
        //          So these interfaces doesn't need of generics because are explicit.
        //          >>> These interfaces are an optimization. <<<
        //          Below there are few, but exit others. Explore the "java.util.function "package to see more
        //          For example:
        //              - DoubleXxx : Consumer, Produce and so on that works with Double
        //              - IntXxx    : Consumer, Produce and so on that works with Integer
        //              - LongXxx   : Consumer, Produce and so on that works with Long
        //
        //          A single special case also exist for boolean supplier: BooleanSupplier
        //
        //  *   IntBinaryOperator   : A binary operator that works with Integers
        //  *   IntConsumer         : A consumer that works with Integers
        //  *   IntFunction         : A function that works with Integers
        //  *   IntPredicate        : A predicate that works with Integers
        //  *   IntSupplier         : A supplier that works with Integers
        //  *   IntToDoubleFunction : A function that works with Integers but return a Double
        //  *   IntToLongFunction   : A function that works with Integers but return a Long
        //  *   IntUnaryOperator    : An unary operator that works with Integers

        // comparing a primitive functional interface with is generic type.
            IntBinaryOperator intBinaryOperator = (i1, i2) -> i1+i2;    // didn't need generic type. Efficient.
            BinaryOperator<Integer> binaryOperator = (i1, i2) -> i1+i2; // need a generics type. Inefficient!!!

        IntConsumer intConsumer = i -> System.out.println(i);
        IntFunction intFunction = i -> 10 * i;
        IntPredicate intPredicate = i -> i%2==0;
        IntSupplier intSupplier = () -> new Random().nextInt();
        IntToDoubleFunction intToDoubleFunction = i -> i / 2;
        IntToLongFunction intToLongFunction = i -> (long)i;
        IntUnaryOperator intUnaryOperator = i -> i * 3;
        BooleanSupplier booleanSupplier = () -> true;

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
