package courses.basics_strong.generics.section26;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Behavior {
    private static final Logger logger = LoggerFactory.getLogger(Behavior.class);

    public static void main(String[] args) {
        // Behavior: Generic Area call Non-Generic Area
        List<String> stringList = new ArrayList<>(List.of("Basic", "Strong"));
        nonGenericMethod(stringList);
        logger.info("## String List");
        logger.info("{}", stringList);

        // using stream we'll have ClassCastException.
        // stringList.stream().map( s -> (Object)s).forEach(o -> logger.info("[{}] type {}", o, o.getClass().getSimpleName()) )

        // iterating via "for" we can see the data
        for(Object o:stringList) {
            logger.info("[{}] type >> [{}]", o, o.getClass().getSimpleName());
        }

        // Behavior: Non-Generic Area call Generic Area
        List rawList = new ArrayList<>(List.of("Basic", "Strong"));
        rawList.add(1001);
        rawList.add(false);
        rawList.add(LocalDateTime.now());
        genericMethod(rawList);
        logger.info("");
        logger.info("## Raw List");
        logger.info("{}", rawList);

        // RawList is a raw type, this means use Object and we can iterate via stream
        rawList.stream().map( s -> (Object)s).forEach(o -> logger.info("[{}] type {}", o, o.getClass().getSimpleName()) );
    }

    private static void nonGenericMethod(List rawList) {
        rawList.add(10);
        rawList.add("A String");
        rawList.add(true);
    }

    private static void genericMethod(List<String> stringList) {
        stringList.add("Foo");
        stringList.add("A String");
    }
}
