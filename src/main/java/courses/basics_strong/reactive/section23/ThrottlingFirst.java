package courses.basics_strong.reactive.section23;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ThrottlingFirst extends BasicExampleClass {
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
                //      Emits only the first item emitted by the current Observable during sequential time windows of a specified duration,
                //      where the windows are managed by a specified Scheduler.
                //
                // Looking the output, only the first emission will be emitted during the specified interval time
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(
                        item -> log("On Next : " + item),
                        Throwable::printStackTrace,
                        () -> log("On Complete")
        );

        log("--> Ends...");
        disposable.dispose();
    }
}
