package courses.basics_strong.reactive.section23;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ThrottlingLast extends BasicExampleClass {
    public static void main(String[] args) {
        Observable<String> source = Observable.create(emitter -> {
           emitter.onNext("A");

           sleep(200, TimeUnit.MILLISECONDS);
           emitter.onNext("B");

            sleep(100, TimeUnit.MILLISECONDS);
            emitter.onNext("C");

            sleep(400, TimeUnit.MILLISECONDS);
            emitter.onNext("D");

            sleep(300, TimeUnit.MILLISECONDS);
            emitter.onNext("E");

            sleep(800, TimeUnit.MILLISECONDS);
            emitter.onNext("F");

            sleep(900, TimeUnit.MILLISECONDS);
            emitter.onNext("X");

            sleep(600, TimeUnit.MILLISECONDS);
            emitter.onNext("Y");

            sleep(1000, TimeUnit.MILLISECONDS);
            emitter.onNext("Z");

            emitter.onComplete();
        });

        Disposable disposable = source
                // Give the javadoc:
                //
                //      Emits only the last item emitted by the current Observable during sequential time windows of a specified duration.
                //      This differs from throttleFirst in that this ticks along at a scheduled interval whereas throttleFirst does not tick,
                //      it just tracks passage of time.
                //
                // Looking the output, only the last emission will be emitted during the specified interval time
                //
                // NOTE: throttleLast(...) is an alias of sample(...)
                //      we can use simply: sample(1, TimeUnit.SECONDS)
                .throttleLast(1, TimeUnit.SECONDS)
                .subscribe(
                        item -> log("On Next : " + item),
                        Throwable::printStackTrace,
                        () -> log("On Complete")
        );

        log("--> Ends...");
        disposable.dispose();
    }
}
