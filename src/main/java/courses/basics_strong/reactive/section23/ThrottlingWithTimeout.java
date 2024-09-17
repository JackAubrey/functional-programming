package courses.basics_strong.reactive.section23;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ThrottlingWithTimeout extends BasicExampleClass {
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
                //      Mirrors the current Observable, except that it drops items emitted by the current Observable that are followed by newer items before a timeout value expires.
                //      The timer resets on each emission (alias to debounce(long, TimeUnit, Scheduler)).
                //      Note: If items keep being emitted by the current Observable faster than the timeout then no items will be emitted by the resulting Observable.
                //
                // Looking the output, only the emission after 700 milliseconds of silence of the source
                //
                // NOTE: throttleWithTimeout(...) is an alias of debounce(...)
                //      we can use simply: debounce(1, TimeUnit.SECONDS)
                .throttleWithTimeout(700, TimeUnit.MILLISECONDS)
                .subscribe(
                        item -> log("On Next : " + item),
                        Throwable::printStackTrace,
                        () -> log("On Complete")
        );

        log("--> Ends...");
        disposable.dispose();
    }
}
