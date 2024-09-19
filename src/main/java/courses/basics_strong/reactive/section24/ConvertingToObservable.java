package courses.basics_strong.reactive.section24;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ConvertingToObservable extends BasicExampleClass {
    public static void main(String[] args) {
        Disposable disposable = Flowable
                .range(1, 1000000)
                .toObservable()
                .observeOn(Schedulers.io())
                .subscribe(e -> log(e + " - " + Thread.currentThread().getName()));

        sleep(5, TimeUnit.SECONDS);

        log("--> Ends...");
        disposable.dispose();
    }
}
