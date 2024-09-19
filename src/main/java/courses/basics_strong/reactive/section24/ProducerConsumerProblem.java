package courses.basics_strong.reactive.section24;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerProblem extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    private static final int RANGE_MAX = 1000;
    private static final int OBSERVER_SLEEP = 100;

    public static void main(String[] args) {

        // In order to show the Produce/Consumer problem
        // we are going to create an observable range source that will emit numbers from 1 to RANGE_MAX
        Observable<String> observable = Observable.range(1, RANGE_MAX)
                // than map the value just to do something inside and producing a log
                .map(n -> {
                    log("[" + LocalTime.now() + "] Produced item is : " + n + " : "+Thread.currentThread().getName());
                    return "Mapped Value is : " + n;
                })
                // and observing on different thread using I/O Scheduler
                .observeOn(Schedulers.io());

        // now we subscribe on it.
        // we pause a bit this subscriber and also produce a log
        Disposable disposable = observable.subscribe(e -> {
            sleep(OBSERVER_SLEEP, TimeUnit.MILLISECONDS);
            log("\t[" + LocalTime.now() + "] --> Consumed is : " + e + " : "+Thread.currentThread().getName());
        });
        disposables.add(disposable);

        // we pause the main thread for a while in order to see what happens
        sleep(RANGE_MAX * (long)OBSERVER_SLEEP, TimeUnit.MILLISECONDS);

        // You'll notice the emissions are being produced much faster than the observer can process
        // and the backlogged emissions get queued up by "observeOn" in an unbounded manner.
        // This could lead to many problems such as out of memory error.
        //
        // This is the reason why we need back pressure to avoid this Producer/Consumer problem.
        // We will see on another example how to address this problem using RxJav Flowables.

        log("--> Ends...");
        disposables.dispose();
    }
}
