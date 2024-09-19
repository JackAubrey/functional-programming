package courses.basics_strong.reactive.section24;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BackPressureWithLimit extends BasicExampleClass {
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

        // now we subscribe on it using a "Subscriber"
        //
        // REMEMBER: this "subscribe" signature returns nothing.
        //
        // going back to the "BackPressure" example, we can read on the notes what happens looking the produced output.
        // briefly: the system strives to produce 96 emission and then consume it.
        //
        // now we want control the number of emissions
        // to do that we can implement the Subscriber interface
        flowable.subscribe(new Subscriber<String>() {
            Subscription subscription;
            final AtomicInteger count = new AtomicInteger();
            static final int REQUESTS_LIMIT = 20;

            // 1- first of all, we store the Subscription using the "onSubscribe" method
            // also here we set the number of request we want use has limit
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                log("Subscribing Subscription Asking "+ REQUESTS_LIMIT +" requests");
                this.subscription.request(REQUESTS_LIMIT);
            }

            // 2- now we consume the emission
            // We need also increment the counter and verify if it is multiple of REQUESTS_LIMIT.
            // If the counter is a multiple of REQUESTS_LIMIT we need to request nex REQUESTS_LIMIT elements.
            @Override
            public void onNext(String s) {
                if( count.getAndIncrement() % REQUESTS_LIMIT == 0) {
                    log("\t[" + LocalTime.now() + "] \t --> Asking for next : " + REQUESTS_LIMIT + " items : "+Thread.currentThread().getName());
                    subscription.request(REQUESTS_LIMIT);
                }

                log("\t[" + LocalTime.now() + "] --> Consumed is : " + s + " : "+Thread.currentThread().getName());
                sleep(OBSERVER_SLEEP, TimeUnit.MILLISECONDS);
            }

            @Override
            public void onError(Throwable t) {
                logErr(t);
            }

            @Override
            public void onComplete() {
                log("\t[" + LocalTime.now() + "] --> Completed : "+Thread.currentThread().getName());
            }
        });

        // we pause the main thread for a while in order to see what happens
        sleep(RANGE_MAX * (long)OBSERVER_SLEEP, TimeUnit.MILLISECONDS);

        log("--> Ends...");
    }
}
