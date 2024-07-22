package courses.basics_strong.funcprogramming.section7;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FP02OptionalOperation2 {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("Basics");
        Optional<String> optionalEmpty = Optional.empty();
        Consumer<String> consumer = (s) -> System.out.println("Consuming value: "+s);
        Runnable orElse = () -> System.out.println("Optional is empty");
        Supplier<String> supplier = () -> "Supplied value";
        Supplier<Optional<String>> supplierOptional = () -> Optional.of("Optional Supplied value");

        // ifPresent
        optional.ifPresent( consumer ); // only this will be executed
        optionalEmpty.ifPresent( consumer ); // this wil not executed because is empty

        // ifPresentOrElse
        optional.ifPresentOrElse( s -> System.out.println("the optional is present"), orElse ); // this will use the consumer
        optionalEmpty.ifPresentOrElse( s -> System.out.println("the optional is NOT present"), orElse ); // this wil use the runnable because this optional is empty

        // stream
        optional.stream() // this one will produce a stream
                .forEach(s -> System.out.println("Streamed value: "+s));
        optionalEmpty.stream()  // this one will produce an empty stream
                .forEach(s -> System.out.println("you'll never see this"));

        // or
        optional.or(supplierOptional).ifPresent(s -> System.out.println( "OR Method of Optional: "+s));
        optionalEmpty.or(supplierOptional).ifPresent(s -> System.out.println( "OR Method of empty Optional: "+s));

        // orElse
        System.out.println( "OrElse Method: " + optional.orElse("Supplied OrElse Method value") );
        System.out.println( "OrElse Method: " + optionalEmpty.orElse("Supplied OrElse Method value") );

        // orElseGet
        System.out.println( "OrElseGet Method: " + optional.orElseGet(supplier) );
        System.out.println( "OrElseGet Method: " + optionalEmpty.orElseGet(supplier) );

        // equals
        Optional<String> opt1 = Optional.of("Basics");
        Optional<String> opt2 = Optional.of("Basics");
        Optional<String> opt3 = Optional.of("Strong");
        Optional<String> empty1 = Optional.empty();
        Optional<String> empty2 = Optional.empty();

        System.out.println("Empty-1 is equals to Empty-2 ? "+(empty1.equals(empty2)));
        System.out.println("Empty-1 is equals to Optional-1 ? "+(empty1.equals(opt1)));
        System.out.println("Optional-1 is equals to Optional-2 ? "+(opt1.equals(opt2)));
        System.out.println("Optional-1 is equals to Optional-3 ? "+(opt1.equals(opt3)));

        // hashcode
        System.out.println("Hashcode of Optional-1 is: "+opt1.hashCode());
        System.out.println("Hashcode of Optional-2 is: "+opt2.hashCode());
        System.out.println("Hashcode of Optional-3 is: "+opt3.hashCode());
        System.out.println("Hashcode of Empty-1 is: "+empty1.hashCode());
    }
}
