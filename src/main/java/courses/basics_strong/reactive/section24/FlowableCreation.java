package courses.basics_strong.reactive.section24;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FlowableCreation extends BasicExampleClass {
    public static void main(String[] args) {
        // First BackPressure strategy
        Flowable<String> flowable = Flowable.create(
                        // to make more readable the code, the subscription has been moved in a Higher Order Function
                        flowableInstance(),
                        // These are the Buffer Strategies:
                        //  1. BUFFER:  queues up emissions in an unbounded queue.
                        //              Until then the downstream is able to consume them.
                        //              Once the downstream is ready it flushes the buffer, but it can cause an out of memory error
                        //              if the queues gets too large.
                        //  2. DROP:    drop if the downstream cannot keep up this will ignore the upstream emissions,
                        //              and will not queue anything while the downstream is busy.
                        //  3. ERROR:   Error signals are missing back pressure exception the moment the downstream cannot keep up with the source.
                        //  4. LATEST:  this will keep only the latest emission until the downstream is ready to receive it.
                        //  5. MISSING: it results in no back pressure implementation at all.
                        //              The downstream must deal with the back pressure overflow.
                        //
                        // in this example we are going to use the BUFFER strategy
                        BackpressureStrategy.BUFFER
                )
                // We are going to use an I/O Scheduler to observe using different threads.
                .observeOn(Schedulers.io());

        // now lets go to subscribe our flowable
        Disposable disposable = flowable.subscribe(FlowableCreation::log);

        // and put in wait the main thread for 5 seconds
        sleep(5, TimeUnit.SECONDS);

        log("--> Ends...");
        disposable.dispose();
    }

    private static @NonNull FlowableOnSubscribe<String> flowableInstance() {
        return emitter -> {
            // we create 5000 emission
            IntStream.rangeClosed(1, 5000)
                    // but break the stream if emitter is cancelled
                    .takeWhile(i -> !emitter.isCancelled())
                    // for each stream element we emit a string
                    .forEach(i -> emitter.onNext("Consuming : " + i));


            // finally invoke the "onComplete"
            log("Completing.....");
            emitter.onComplete();
        };
    }
}
