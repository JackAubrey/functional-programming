package courses.basics_strong.generics.section26;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ErasureDemo2 {
    private static final Logger logger = LoggerFactory.getLogger(ErasureDemo2.class);

    // now we take a look to what happens int this second case
    public static void main(String[] args) {
        // we have a list declared in this manner
        //
        // At the time of compilation, as the last step, Java compiler will use the type erasure feature to remove the generic syntax.
        // What happened here is type erasure feature removed the generic syntax
        // hence the generic syntax was not available to JVM.
        // This was equivalent to "ArrayList list = new ArrayList()"
        ArrayList list = new ArrayList<String>();

        // now we are going to add elements
        // we have declared an ArrayList<String> but we are able to add whatever in this case.
        list.add(12);
        list.add(true);
        list.add("Hello");

        logger.info( "{}", list );

    }
}
