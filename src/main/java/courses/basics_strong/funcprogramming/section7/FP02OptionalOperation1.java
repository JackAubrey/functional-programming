package courses.basics_strong.funcprogramming.section7;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class FP02OptionalOperation1 {
    public static void main(String[] args) {
        Optional<Integer> optional = Optional.of(100);
        Function<Integer, String> mapIntToString = (i) -> "Updated. Original value [" + i +"]";
        Predicate<Integer> isEven = (i) -> i % 2 == 0;

        // map
        Optional<String> stringOptional = optional.map(mapIntToString);
        Optional<String> emptyStringOptional = Optional.<Integer>empty().map(mapIntToString);
        System.out.println("Map of optional value = " + stringOptional.orElse("None"));
        System.out.println("Map of empty optional value = " + emptyStringOptional.orElse("None"));

        // filter
        Optional<Integer> intOptional = optional.filter(isEven);
        Optional<Integer> emptyIntOptional = Optional.<Integer>empty().filter(isEven);
        System.out.println("Filter of optional value = " + intOptional.orElse(-1));
        System.out.println("Filter empty optional value = " + emptyIntOptional.orElse(-1));

        // flatMap
        //  - map wrap the return of the mapper function to an optional
        //  - flatMap doesn't do it because the mapper function already returns an optional.
        Optional<String> map = optional.map( mapIntToString);
        Optional<String> flatMap = optional.map( mapIntToString)
                                        .flatMap( (s) -> Optional.of("Length of ["+s+"] is: "+ s.length()));
        System.out.println("Flat Map result: "+flatMap.orElse("NONE"));
    }
}
