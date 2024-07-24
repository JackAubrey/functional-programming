package courses.basics_strong.funcprogramming.section6;

import java.util.List;
import java.util.function.*;

public class FP02StaticInstanceMethod {
    public static void main(String[] args) {
        // look!
        // MyMainClass has a static instance of MyChildClass
        //
        // we can use the Class::instanceMethod in this manner:
        //  - using the dot to refer to the static instance of object inside of class
        //  - and after the double column to refer to its methods
        // remember: those methods must be used in according to the signature of functional interfaces

        // Some examples:
        //
        // a Predicate wants an input and return a boolean
        //  - MyMainClass.child is referring to the static instance MyChildClass declared in MyMainClass
        //  - MyChildClass contains a method called "predicate" that respect the Predicate signature
        //          boolean predicate(String s)
        Predicate<String> predicate = MyMainClass.child::predicate;

        // a Consumer wants an input and return nothing
        //  - MyMainClass.child is referring to the static instance MyChildClass declared in MyMainClass
        //  - MyChildClass contains a method called "consume" that respect the Consumer signature
        //          void consume(String s)
        Consumer<String> consumer = MyMainClass.child::consume;

        // a Supplier wants no input and return a value
        //  - MyMainClass.child is referring to the static instance MyChildClass declared in MyMainClass
        //  - MyChildClass contains a method called "supply" that respect the Supplier signature
        //          String supply()
        Supplier<String> supplier = MyMainClass.child::supply;

        // a Function wants an input and return a value
        //  - MyMainClass.child is referring to the static instance MyChildClass declared in MyMainClass
        //  - MyChildClass contains a method called "map" that respect the Function signature
        //          String map(Integer i)
        Function<Integer, String> function = MyMainClass.child::map;

        // now test those:
        System.out.println("Test if string is empty: Expected TRUE, received ["+predicate.test("")+"]");
        System.out.println("Test if string is empty: Expected FALSE, received ["+predicate.test("Basics")+"]");

        consumer.accept("If you are looking this message means that consumer is working fine");
        System.out.println("The supplier has supplied value: " + supplier.get() );
        System.out.println("The mapping function has returned value: " + function.apply(1234) );

        // this is instead a static method reference
        // Since the "myStaticMapMethod" is a static method of MyMainClass, we are referring directly to it
        Function<String, Integer> stringLength = MyMainClass::myStaticMapMethod;
        System.out.println("Static Mapping Method. "+stringLength.apply("This is a string"));

        // also this is a static methods reference
        // "sum" a static method of MyMainClass that wants 2 integer and return an integer.
        // if you think about the "sum" signature, this one remember BiFunction or also BinaryOperator
        // lets try...
        BiFunction<Integer, Integer, Integer> biFunction = MyMainClass::sum;
        BinaryOperator<Integer> binaryOperator = MyMainClass::sum;
        System.out.println("BiFunction sum "+biFunction.apply(3, 5));
        System.out.println("BinaryOperator sum "+binaryOperator.apply(3, 5));

        Function<String, Integer> strLenFunc = String::length;
        final String str = "BasicStrong";
        final int strLen = strLenFunc.apply(str);
        System.out.println("The string ["+str+"] has a length of: "+strLen);
    }
}

class MyMainClass {
    static MyChildClass child = new MyChildClass();

    static Integer myStaticMapMethod(String s) {
        return s.length();
    }

    static Integer sum(Integer a, Integer b) {
        return a+b;
    }
}

class MyChildClass {
    void consume(String s) {
        System.out.println(s);
    }

    String supply() {
        return "a string";
    }

    boolean predicate(String s) {
        return s == null || s.trim().isEmpty();
    }

    String map(Integer i) {
        return "Your value is: "+i;
    }
}
