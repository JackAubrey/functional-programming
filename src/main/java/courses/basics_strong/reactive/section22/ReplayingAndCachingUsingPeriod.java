package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ReplayingAndCachingUsingPeriod extends BasicExampleClass {
    private static final int WAIT_BEFORE_CONTINUE = 5;
    private static final long EMISSION_INTERVAL = 1500;
    private static final long CACHE_PERIOD = 2500;
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // This example is near equals to the previous "ReplayingAndCachingUsingBuffer" example but in this case we are caching latest values using a period as time to live.
        // To activate the caching, simply put the period to the "reply" method.
        //
        // In order to see the caching behaviour, we set:
        //      - emission period $EMISSION_INTERVAL milliseconds
        //      - cache only emission in the last $CACHE_PERIOD milliseconds
        //
        // Given an interval observable.
        Observable<String> source = Observable.interval(EMISSION_INTERVAL, TimeUnit.MILLISECONDS)// 1.5 seconds
                // we map the numeric value to a string
                .map(l -> "Emission: " + l+" - Is Even ? "+(l%2 ==0))
                // activate the Replaying (it returns a ConnectableObservable)
                .replay(CACHE_PERIOD, TimeUnit.MILLISECONDS) // CACHING - we are caching only the latest emissions emitted in the last 2.5 seconds
                .autoConnect(); // we auto connect in order to immediately starts firing events

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
