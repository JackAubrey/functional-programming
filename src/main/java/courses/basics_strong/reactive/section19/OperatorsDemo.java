package courses.basics_strong.reactive.section19;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Predicate;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class OperatorsDemo extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    private static final Random random = new Random();

    public static void main(String[] args) {
        Stream<Integer> intStream = buildRandomIntegerStream();
        Predicate<Integer> isEvenPredicate = i -> i % 2 == 0;
        Name fakerName = new Faker().name();

        // given a stream of integers
        disposables.add(
                Observable.fromStream(intStream)
                    // we want only numbers greater than 150
                    .filter( isGreaterThan() ) // returns an Observable<Integer>
                    // we want only even numbers
                    .filter( isEvenPredicate ) // returns an Observable<Integer>
                    // and also we want sort
                    .sorted() // returns an Observable<Integer>
                    // now we want map it to other object
                    .map( i -> new User(fakerName.fullName(), i)) // returns an Observable<User>
                    //finally we subscribe
                    .subscribe(OperatorsDemo::log)
        );

        // given a sequence of numbers we want see in action the difference between scan and reduce
        log("\n Scan result of numbers from 1 to 10");
        disposables.add(
                Observable.just(1,2,3,4,5,6,7,8,9,10)
                    .scan(Integer::sum)
                    .subscribe(OperatorsDemo::log)
        );
        log("\nReduce result of numbers from 1 to 10");
        disposables.add(
                Observable.just(1,2,3,4,5,6,7,8,9,10)
                    .reduce(Integer::sum)
                    .subscribe(OperatorsDemo::log)
        );
        // as you can see they are identical except scan emit every step, reduce only the final

        // given a sequence of list
        log("\nFlatMap usage example");
        disposables.add(
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
                        .subscribe(OperatorsDemo::log)
        );

        disposables.dispose();
    }

    private static Predicate<Integer> isGreaterThan() {
        return i -> i > 150;
    }

    private static Stream<Integer> buildRandomIntegerStream() {
        Stream.Builder<Integer> builder = Stream.builder();

        for(int i = 0; i< 30; i++) {
            builder.add(random.nextInt(100, 300));
        }

        return builder.build();
    }

    private record User(String name, Integer userId) {}
}
