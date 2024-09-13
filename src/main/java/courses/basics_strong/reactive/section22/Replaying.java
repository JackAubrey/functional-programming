package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Replaying extends BasicExampleClass {
    private static final int WAIT_BEFORE_CONTINUE = 3;
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // given an interval observable
        Observable<String> source = Observable.interval(1, TimeUnit.SECONDS)
                // we map the numeric value to a string
                .map(l -> "Emission: " + l)
                // activate the Replaying (it returns a ConnectableObservable)
                .replay()
                .autoConnect(); // we auto connect in order to immediately starts firing events)

        // now subscribe the first observer
        log("---> Subscribing Observer 1 and wait "+WAIT_BEFORE_CONTINUE+" seconds before to proceed with the other Observer");
        Disposable disposable1 = source.subscribe(e -> log("Observer 1 - " + e));
        disposables.add(disposable1);

        // wait $WAIT_BEFORE_CONTINUE seconds
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
