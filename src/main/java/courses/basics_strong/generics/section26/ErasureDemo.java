package courses.basics_strong.generics.section26;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ErasureDemo {
    private static final Logger logger = LoggerFactory.getLogger(ErasureDemo.class);

    // we have this strange method (never do it something like this)
    public static String strangeMethod(Integer intValue) {
        // here we declare a typed List
        List<String> typedList = new LinkedList<>();
        // Compiler make erasure. This means the above line is equivalent to the below one:
        // List typedList = new LinkedList()

        // now we assign the typed list to a raw type list.
        // here the compiler is performing type-erasure
        List rawList = typedList;

        // add the input value to our rawList
        // the raw-type list behavior permit this ugly operation
        rawList.add( intValue );

        // now return the first element of out typed-list
        // REMEMBER: raw-typed list is referring to our typed-list.
        //           this means they have the same object reference.
        return typedList.getFirst();
        // Compiler inserted the necessary cast for this. This means the above line is equivalent to the below one:
        // return (String)typedList.getFirst()
    }

    // now we take a look to what happens
    public static void main(String[] args) {
        // let's go to use that ugly method
        // running the code we obtain a ClassCastException
        logger.info( strangeMethod(102340) );

    }
}
