package courses.basics_strong.reactive.section17;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class CreatingObservable {
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        // this will be our common ObservableOnSubscribe
        ObservableOnSubscribe<Integer> observableOnSubscribe = createCommonObservableOnSubscribe();

        // create via factory method "create". Is rarely used.
        Observable<Integer> observableFromFactoryMethodCreate = createViaFactoryMethodCreate(observableOnSubscribe);

        // create via factory method "just". Is used to directly push one or more values
        Observable<Integer> observableFromFactoryMethodJust = createViaFactoryMethodJust();

        // create via factory method "fromIterable". Is used to directly push values from an Iterable
        Observable<Integer> observableFromFactoryMethodFromIterable = createViaFactoryMethodFromIterable();

        // create via factory method "range". Is used to directly push values start from a certain value till a total
        Observable<Integer> observableFromFactoryMethodRange = createViaFactoryMethodRange(20, 10);

        // create via factory method "interval". Is used to directly push values every certain time interval
        Observable<Long> observableFromFactoryMethodInterval = createViaFactoryMethodInterval(3, 3, TimeUnit.SECONDS);

        // create via factory method "intervalRange". Is used to directly push values from a start till a count every certain time intervalRange
        Observable<Long> observableFromFactoryMethodIntervalRange = createViaFactoryMethodIntervalRange(1000,3, 2, 2, TimeUnit.SECONDS);

        // create via factory method "future". Is not easy to use, we need to read carefully its java-doc
        Observable<String> observableFromFactoryMethodFromFuture = createViaFactoryMethodFromFuture();

        // create via factory method "error". It used to send an error event
        Observable<String> observableFromFactoryMethodError = createViaFactoryMethodError();

        // create via factory method "defer". It used to defer events produced by a Supplier of another Observable
        List<String> names = new ArrayList<>(List.of("Mike", "John"));
        Observable<String> observableFromFactoryDefer = createViaFactoryMethodDefer(names);

        // now we are going to subscribe the observables
        observableFromFactoryMethodCreate.subscribe(e -> System.out.println("Created via FactoryMethod Create: "+e));
        observableFromFactoryMethodJust.subscribe(e -> System.out.println("Created via FactoryMethod Just: "+e));
        observableFromFactoryMethodFromIterable.subscribe(e -> System.out.println("Created via FactoryMethod From Iterable: "+e));
        observableFromFactoryMethodRange.subscribe(e -> System.out.println("Created via FactoryMethod Range: "+e));
        Disposable intervalDisposable = observableFromFactoryMethodInterval.subscribe(e -> System.out.println("Created via FactoryMethod Interval: " + e));
        observableFromFactoryMethodIntervalRange.subscribe(e -> System.out.println("Created via FactoryMethod Interval Range: " + e));

        // PAY Attention to use the "fromFuture". Is not easy to use it!!!
        //observableFromFactoryMethodFromFuture.subscribe(e -> System.out.println("Created via FactoryMethod From Future: " + e));

        // NOTE needs of an Observer able to handle the "onError"
        //observableFromFactoryMethodError.subscribe(e -> System.out.println("Created via FactoryMethod Error: " + e));

        // this is the first subscription, after that we modify the list and create a new subscription
        observableFromFactoryDefer.subscribe(e -> System.out.println("Created via FactoryMethod Defer | First : " + e));
        names.add("Paul");
        names.add("Lisa");
        observableFromFactoryDefer.subscribe(e -> System.out.println("Created via FactoryMethod Defer | Second : " + e));

        int cnt = 0;
        while ( !intervalDisposable.isDisposed() ) {
            TimeUnit.MILLISECONDS.sleep(1000);
            cnt++;
            if(cnt>10) {
                System.out.println("Disposing interval");
                intervalDisposable.dispose();
            } else {
                System.out.println("Try "+cnt+"/10. Waiting for ends...");
            }

        }
    }

    /**
     * Create an Observable using Observable factory method "create"
     *
     * @param observableOnSubscribe the observable
     * @return an Observable instance
     */
    private static Observable<Integer> createViaFactoryMethodCreate(ObservableOnSubscribe<Integer> observableOnSubscribe) {
        return Observable.create(observableOnSubscribe);
    }

    /**
     * Create an Observable using Observable factory method "just"
     *
     * @return an Observable instance
     */
    private static Observable<Integer> createViaFactoryMethodJust() {
        return Observable.just(1,2,3,4,5,6,7);
    }

    /**
     * Create an Observable using Observable factory method "fromIterable"
     *
     * @return an Observable instance
     */
    private static Observable<Integer> createViaFactoryMethodFromIterable() {
        Stream.Builder<Integer> streamBuilder = Stream.<Integer>builder();

        for (int i=0; i < random.nextInt(10, 15); i++) {
            streamBuilder.add(random.nextInt(100, 300));
        }

        Stream<Integer> builtStream = streamBuilder.build();
        return Observable.fromIterable(builtStream.toList());
    }

    /**
     * Create an Observable using Observable factory method "range"
     *
     * @param start value from which to start
     * @param count the total count
     * @return an Observable instance
     */
    private static Observable<Integer> createViaFactoryMethodRange(int start, int count) {
        return Observable.range(start, count);
    }

    /**
     * Create an Observable using Observable factory method "interval"
     *
     * @param initialDelay initial delay
     * @param period period between two events
     * @param unit time unit of period
     * @return an Observable instance
     */
    private static Observable<Long> createViaFactoryMethodInterval(int initialDelay, long period, TimeUnit unit) {
        return Observable.interval(initialDelay, period, unit);
    }

    /**
     * Create an Observable using Observable factory method "intervalRange"
     *
     * @param start the initial value
     * @param count the number of values to emit in total, if zero, the operator emits an onComplete after the initial delay
     * @param initialDelay initial delay
     * @param period period between two events
     * @param unit time unit of period
     * @return an Observable instance
     */
    private static Observable<Long> createViaFactoryMethodIntervalRange(int start, int count, int initialDelay, long period, TimeUnit unit) {
        return Observable.intervalRange(start, count, initialDelay, period, unit);
    }

    /**
     * Create an Observable using Observable factory method "fromFuture"
     *
     * @return an Observable instance
     */
    private static Observable<String> createViaFactoryMethodFromFuture() {
        return Observable.fromFuture( new FutureTask<>( () -> "Helloooo"));
    }

    /**
     * Create an Observable using Observable factory method "error"
     *
     * @return an Observable instance
     */
    private static Observable<String> createViaFactoryMethodError() {
        return Observable.error( () -> new IllegalArgumentException("This is an error"));
    }

    /**
     * Create an Observable using Observable factory method "defer"
     *
     * @return an Observable instance
     */
    private static Observable<String> createViaFactoryMethodDefer(List<String> list) {
        return Observable.defer( () -> Observable.fromIterable(list));
    }

    /**
     *
     * @return ObservableOnSubscribe lambda
     */
    private static ObservableOnSubscribe<Integer> createCommonObservableOnSubscribe() {
        return emitter -> {
            int cnt = 0;

            while (++cnt < 10) {
                int value = random.nextInt(1, 110);
                emitter.onNext(value);
            }

            emitter.onComplete();
        };
    }
}
