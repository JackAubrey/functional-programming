package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ReplayingAndCachingUsingSelectorFunction extends BasicExampleClass {
    private static final int WAIT_BEFORE_CONTINUE = 5;
    private static final long EMISSION_INTERVAL = 1500;
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // This example is near equals to the previous "ReplayingAndCachingUsingPeriod" example but in this case we are caching latest values using a selector function.
        // To activate the caching, simply put a function as selector to the "reply" method.
        //
        // In order to see the caching behaviour, we set select only emission that contains even values
        //
        // Given an interval observable.
        Observable<String> source = Observable.interval(EMISSION_INTERVAL, TimeUnit.MILLISECONDS)// 1.5 seconds
                // we map the numeric value to a string
                .map(l -> "Emission: " + l+" - Is Even ? "+(l%2 ==0))
                // activate the Replaying (it returns a ConnectableObservable)
                .replay( e -> e.filter(v -> v.contains("Is Even ? true"))); // CACHING - we use a selector function

        // now subscribe the first observer
        log("---> Subscribing Observer 1 and wait "+WAIT_BEFORE_CONTINUE+" seconds before to proceed with the other Observer");
        Disposable disposable1 = source.subscribe(e -> log("Observer 1 - " + e));
        disposables.add(disposable1);

        // wait for $EMISSION_INTERVAL * $CACHE_SIZE * 2 seconds
        sleep(WAIT_BEFORE_CONTINUE, TimeUnit.SECONDS);

        // now subscribe the second observer
        log("---> Subscribing Observer 2");
        Disposable disposable2 = source.subscribe(e -> log("Observer 2 - " + e));
        disposables.add(disposable2);

        // wait $WAIT_BEFORE_CONTINUE seconds
        sleep(WAIT_BEFORE_CONTINUE, TimeUnit.SECONDS);

        log("---> Ends..");
        disposables.dispose();
    }
}
