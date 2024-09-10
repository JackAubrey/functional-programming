package courses.basics_strong.reactive.section18;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class CreatingObservable extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    private static final Random random = new Random();

    public static void main(String[] args) {
        // this will be our common ObservableOnSubscribe
        ObservableOnSubscribe<Integer> observableOnSubscribe = createCommonObservableOnSubscribe();

        // create via factory method "create". Is rarely used.
        Observable<Integer> observableFromFactoryMethodCreate = createViaFactoryMethodCreate(observableOnSubscribe);

        // create via factory method "just". Is used to directly push one or more values
        Observable<Integer> observableFromFactoryMethodJust = createViaFactoryMethodJust();

        // create via factory method "fromIterable". Is used to directly push values from an Iterable
        Observable<Integer> observableFromFactoryMethodFromIterable = createViaFactoryMethodFromIterable();

        // create via factory method "range". Is used to directly push values start from a certain value till a total
        Observable<Integer> observableFromFactoryMethodRange = createViaFactoryMethodRange();

        // create via factory method "interval". Is used to directly push values every certain time interval
        Observable<Long> observableFromFactoryMethodInterval = createViaFactoryMethodInterval();

        // create via factory method "intervalRange". Is used to directly push values from a start till a count every certain time intervalRange
        Observable<Long> observableFromFactoryMethodIntervalRange = createViaFactoryMethodIntervalRange();

        // create via factory method "future". Is not easy to use, we need to read carefully its java-doc
        // Observable<String> observableFromFactoryMethodFromFuture = createViaFactoryMethodFromFuture();

        // create via factory method "error". It used to send an error event
        // Observable<String> observableFromFactoryMethodError = createViaFactoryMethodError();

        // create via factory method "defer". It used to defer events produced by a Supplier of another Observable
        List<String> names = new ArrayList<>(List.of("Mike", "John"));
        Observable<String> observableFromFactoryDefer = createViaFactoryMethodDefer(names);

        // now we are going to subscribe the observables
        disposables.add( observableFromFactoryMethodCreate.subscribe(e -> log("Created via FactoryMethod Create: "+e)) );
        disposables.add( observableFromFactoryMethodJust.subscribe(e -> log("Created via FactoryMethod Just: "+e)) );
        disposables.add( observableFromFactoryMethodFromIterable.subscribe(e -> log("Created via FactoryMethod From Iterable: "+e)) );
        disposables.add( observableFromFactoryMethodRange.subscribe(e -> log("Created via FactoryMethod Range: "+e)) );
        Disposable intervalDisposable = observableFromFactoryMethodInterval.subscribe(e -> log("Created via FactoryMethod Interval: " + e));
        disposables.add( observableFromFactoryMethodIntervalRange.subscribe(e -> log("Created via FactoryMethod Interval Range: " + e)) );

        // PAY Attention to use the "fromFuture". Is not easy to use it!!!
        //observableFromFactoryMethodFromFuture.subscribe(e -> log("Created via FactoryMethod From Future: " + e));

        // NOTE needs of an Observer able to handle the "onError"
        //observableFromFactoryMethodError.subscribe(e -> log("Created via FactoryMethod Error: " + e));

        // this is the first subscription, after that we modify the list and create a new subscription
        disposables.add( observableFromFactoryDefer.subscribe(e -> log("Created via FactoryMethod Defer | First : " + e)) );
        names.add("Paul");
        names.add("Lisa");
        disposables.add( observableFromFactoryDefer.subscribe(e -> log("Created via FactoryMethod Defer | Second : " + e)) );

        int cnt = 0;
        while ( !intervalDisposable.isDisposed() ) {
            sleep(1000, TimeUnit.MILLISECONDS);
            cnt++;
            if(cnt>10) {
                log("Disposing interval");
                intervalDisposable.dispose();
            } else {
                log("Try "+cnt+"/10. Waiting for ends...");
            }

        }

        disposables.dispose();
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
        Stream.Builder<Integer> streamBuilder = Stream.builder();

        for (int i=0; i < random.nextInt(10, 15); i++) {
            streamBuilder.add(random.nextInt(100, 300));
        }

        Stream<Integer> builtStream = streamBuilder.build();
        return Observable.fromIterable(builtStream.toList());
    }

    /**
     * Create an Observable using Observable factory method "range"
     *
     * @return an Observable instance
     */
    private static Observable<Integer> createViaFactoryMethodRange() {
        return Observable.range(20, 10);
    }

    /**
     * Create an Observable using Observable factory method "interval"
     *
     * @return an Observable instance
     */
    private static Observable<Long> createViaFactoryMethodInterval() {
        return Observable.interval(3, 3, TimeUnit.SECONDS);
    }

    /**
     * Create an Observable using Observable factory method "intervalRange"
     *
     * @return an Observable instance
     */
    private static Observable<Long> createViaFactoryMethodIntervalRange() {
        return Observable.intervalRange(1000, 3, 2, 2, TimeUnit.SECONDS);
    }

    /**
     * Create an Observable using Observable factory method "fromFuture"
     *
     * @return an Observable instance
     */
    private static Observable<String> createViaFactoryMethodFromFuture() {
        return Observable.fromFuture( new FutureTask<>( () -> "Hello"));
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
