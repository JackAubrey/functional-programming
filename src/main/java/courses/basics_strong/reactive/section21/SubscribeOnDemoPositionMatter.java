package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class SubscribeOnDemoPositionMatter extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Execute this program and look the output.
        // You'll notice that only closest to source "subscribeOn(Schedulers.computation())" will be used.
        //
        // In this case the position MATTERS
        Disposable subscribe = Observable.just("Pasta", "Pizza", "Fries", "Curry", "Chow mein")
                // FIRST "subscribeOn(...)" call
                .subscribeOn(Schedulers.computation())
                .map(String::toUpperCase)
                .doOnNext( e -> log("After First SubscribeOn | Thread: "+Thread.currentThread().getName()))
                // SECOND "subscribeOn(...)" call
                .subscribeOn(Schedulers.newThread())
                .filter(e -> e.startsWith("P"))
                .doOnNext( e -> log("After Second SubscribeOn | Thread: "+Thread.currentThread().getName()))
                .subscribe(SubscribeOnDemoPositionMatter::print);

        disposables.add(subscribe);

        sleep(6, TimeUnit.SECONDS);


        log("Ends...");
        disposables.dispose();
    }

    public static void print(String element) {
        log(element + " : Printed By : "+Thread.currentThread().getName());
    }
}
