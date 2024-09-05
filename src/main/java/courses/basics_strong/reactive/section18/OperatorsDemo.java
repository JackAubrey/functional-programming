package courses.basics_strong.reactive.section18;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.Random;
import java.util.stream.Stream;

public class OperatorsDemo {
    public static void main(String[] args) {
        Stream<Integer> intStream = buildRandomIntegerStream(30, 100, 300);
        Predicate<Integer> isEvenPredicate = i -> i % 2 == 0;
        Name fakerName = new Faker().name();

        // given a stream of integers
        Observable.fromStream(intStream)
                // we want only numbers greater than 150
                .filter( isGreaterThan(150) ) // returns an Observable<Integer>
                // we want only even numbers
                .filter( isEvenPredicate ) // returns an Observable<Integer>
                // and also we want sort
                .sorted() // returns an Observable<Integer>
                // now we want map it to other object
                .map( i -> new User(fakerName.fullName(), i)) // returns an Observable<User>
                //finally we subscribe
                .subscribe(OperatorsDemo::log);
    }

    private static void log(@NonNull Object e) {
        System.out.println(e);
    }

    private static Predicate<Integer> isGreaterThan(@NonNull Integer thanValue) {
        return i -> i > thanValue;
    }

    private static Stream<Integer> buildRandomIntegerStream(int numElements, int origin, int bound) {
        Stream.Builder<Integer> builder = Stream.builder();
        Random random = new Random();

        for(int i= 0; i<numElements; i++) {
            builder.add(random.nextInt(origin, bound));
        }

        return builder.build();
    }

    private record User(String name, Integer userId) {}
}
