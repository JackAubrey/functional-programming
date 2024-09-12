package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class ConcurrencyUsingFlatMap extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // NOTE: this technique works fine but use it with care because creating an observable for each emission might create some unwanted overhead
        Observable<String> source = Observable
                // we have some elements to be emitted, and we want process in parallel
                .just("Pasta", "Pizza", "Fries", "Curry", "Chow mein")
                // so for each emission we need to convert it in an Observable
                // to achieve this we use "flatMap"... See how to!
                .flatMap(
                        // given the element
                        // we convert to an Observable
                        e -> Observable.just(e)
                                // and process it in parallel using a scheduler for each Observable
                                .subscribeOn(Schedulers.computation())
                                // and also map each result
                                .map(ConcurrencyUsingFlatMap::transform)
                );

        // Subscribe the source and let see what happens.
        Disposable disposable = source.subscribe(ConcurrencyUsingFlatMap::log);
        disposables.add(disposable);

        // You'll see that each element will be executed by a ComputationThread in parallel.
        // The faster one will be printed as first and so on!
        sleep(5, TimeUnit.SECONDS);
        disposables.dispose();
    }

    public static String transform(String element) {
        long start = System.currentTimeMillis();
        sleep(nextRandomLong(200, 2500), TimeUnit.MILLISECONDS);
        return element + " : Printed By : "+Thread.currentThread().getName()+" at : "+ LocalTime.now()+" in : "+(System.currentTimeMillis()-start)+" millis";
    }
}
