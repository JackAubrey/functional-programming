package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ReplayingAndCachingUsingBuffer extends BasicExampleClass {
    private static final int WAIT_BEFORE_CONTINUE = 3;
    private static final int EMISSION_INTERVAL = 1;
    private static final int CACHE_SIZE = 5;
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // This example is near equals to the previous "Replaying" example but in this case we are caching only a certain number of the latest values.
        // To activate the caching, simply put the cache size as number parameter to the "reply" method.
        //
        // In order to see the caching behaviour, since the interval is $EMISSION_INTERVAL seconds and the cache size is $CACHE_SIZE
        // we need sleep the main thread for $EMISSION_INTERVAL * $CACHE_SIZE * 2 seconds
        //
        // Given an interval observable.
        Observable<String> source = Observable.interval(EMISSION_INTERVAL, TimeUnit.SECONDS)
                // we map the numeric value to a string
                .map(l -> "Emission: " + l+" - Is Even ? "+(l%2 ==0))
                // activate the Replaying (it returns a ConnectableObservable)
                .replay(CACHE_SIZE) // CACHING - we are caching only the latest CACHE_SIZE value
                .autoConnect(); // we auto connect in order to immediately starts firing events)

        final long waitFor = EMISSION_INTERVAL * CACHE_SIZE * 2L;

        // now subscribe the first observer
        log("---> Subscribing Observer 1 and wait "+waitFor+" seconds before to proceed with the other Observer");
        Disposable disposable1 = source.subscribe(e -> log("Observer 1 - " + e));
        disposables.add(disposable1);

        // wait for $EMISSION_INTERVAL * $CACHE_SIZE * 2 seconds
        sleep(waitFor, TimeUnit.SECONDS);

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
