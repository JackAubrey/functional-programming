package courses.basics_strong.reactive.section18;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Predicate;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.List;
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

        // given a sequence of numbers we want see in action the difference between scan and reduce
        log("\n Scan result of numbers from 1 to 10");
        Observable.just(1,2,3,4,5,6,7,8,9,10)
                .scan( (x,y) -> x+y)
                .subscribe(OperatorsDemo::log);
        log("\nReduce result of numbers from 1 to 10");
        Observable.just(1,2,3,4,5,6,7,8,9,10)
                .reduce( (x,y) -> x+y)
                .subscribe(OperatorsDemo::log);
        // as you can see they are identical except scan emit every step, reduce only the final

        // given a sequence of list
        log("\nFlatMap usage example");
        Observable.just(
                List.of("Language: Java", "IDE: Eclipse", "Framework: Spring"),
                List.of("Language: Java", "IDE: IntelliJ", "Framework: Spring"),
                List.of("Language: Go", "IDE: IntelliJ", "Framework: Nothing"),
                List.of("Language: Go", "IDE: MS Source", "Framework: Something"),
                List.of("Language: Python", "IDE: Idea Intellij", "Framework: Nothing"),
                List.of("Language: PHP", "IDE: Idea Intellij", "Framework: PHP Storm")
        )
                .filter(l -> l.stream().anyMatch(s -> s.contains("Java")))
                .doOnNext(e -> log("--> After Filter: "+e) )
                .flatMap(Observable::fromIterable)
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
