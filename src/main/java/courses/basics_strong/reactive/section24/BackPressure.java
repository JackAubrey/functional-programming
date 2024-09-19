package courses.basics_strong.reactive.section24;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class BackPressure extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    private static final int RANGE_MAX = 1000;
    private static final int OBSERVER_SLEEP = 100;

    public static void main(String[] args) {

        // In order to show to solve the Produce/Consumer problem using Flowable and BackPressure
        // we are going to create a Flowable instead of Observable range source that will emit numbers from 1 to RANGE_MAX
        // this chain will not have the producer consumer problem anymore
        Flowable<String> flowable = Flowable.range(1, RANGE_MAX)
                // than map the value just to do something inside and producing a log
                .map(n -> {
                    log("[" + LocalTime.now() + "] Produced item is : " + n + " : "+Thread.currentThread().getName());
                    return "Mapped Value is : " + n;
                })
                // and observing on different thread using I/O Scheduler
                .observeOn(Schedulers.io());

        // now we subscribe on it.
        // we pause a bit this subscriber and also produce a log
        //
        // NOTE: Flowable expose almost Observable creation factory methods and also the almost same "subscribe(...)" Observable methods that returns a Disposable.
        //       But also expose two "subscribe" that returns nothing and accept a Subscriber.
        //       We'll see them on the second version of this example "BackPressureWithLimit".
        Disposable disposable = flowable.subscribe(e -> {
            sleep(OBSERVER_SLEEP, TimeUnit.MILLISECONDS);
            log("\t[" + LocalTime.now() + "] --> Consumed is : " + e + " : "+Thread.currentThread().getName());
        });
        disposables.add(disposable);

        // we pause the main thread for a while in order to see what happens
        sleep(RANGE_MAX * (long)OBSERVER_SLEEP, TimeUnit.MILLISECONDS);

        // You'll notice:
        //      1- first 128 emissions were immediately pushed from the Flowable range and then 128 items got consumed
        //      2- and after that "observeOn" pushed 96 item of them downstream to subscriber and then these 96 emission consumed by the subscriber.
        //      3- and then another 96 were pushed from the source and then another were passed to the subscriber.
        // So this is what is happening here it is almost like the entire flowable chain
        // strives to have no more than 96 emissions in its pipeline at a given time.
        // So now the producer is producing in a limit and when those item gets consumed then the producer is producing next elements.
        // THIS IS WHAT WE CALL BACK PRESSURE
        // Now you are able to handle the producer consumer problem.

        log("--> Ends...");
        disposables.dispose();
    }
}
