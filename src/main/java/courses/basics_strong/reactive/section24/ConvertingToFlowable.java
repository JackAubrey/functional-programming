package courses.basics_strong.reactive.section24;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ConvertingToFlowable extends BasicExampleClass {
    public static void main(String[] args) {
        Disposable disposable = Observable
                .range(1, 1000000)
                .toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(Schedulers.io())
                .subscribe(e -> log(e + " - " + Thread.currentThread().getName()));

        sleep(5, TimeUnit.SECONDS);

        log("--> Ends...");
        disposable.dispose();
    }
}
