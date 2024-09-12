package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ObserveOnDemo extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Execute this program and look the output.
        // You'll notice that the upstream "subscribeOn(...)" will not impacted by the "observeOn(...)" but only the downstream will be impacted.
        // Looking the result you'll see how the Scheduler type changes after the "observeOn(...)" calls.
        //
        // In this case the position MATTERS
        Disposable subscribe = Observable.just("Pasta", "Pizza", "Fries", "Curry", "Chow mein")
                // FIRST "subscribeOn(...)" call
                .subscribeOn(Schedulers.computation())
                .map(String::toUpperCase)
                .doOnNext( e -> log("After First ObserveOn | Thread: "+Thread.currentThread().getName()))
                // SECOND "observeOn(...)" call
                .observeOn(Schedulers.newThread())
                .doOnNext( e -> log("After Second ObserveOn | Thread: "+Thread.currentThread().getName()))
                .filter(e -> e.startsWith("P"))
                // THIRD "observeOn(...)" call
                .observeOn(Schedulers.io())
                .doOnNext( e -> log("After Third ObserveOn | Thread: "+Thread.currentThread().getName()))
                .subscribe(SubscribeOnDemo::print);

        disposables.add(subscribe);

        sleep(6, TimeUnit.SECONDS);


        log("Ends...");
        disposables.dispose();
    }

    public static void print(String element) {
        log(element + " : Printed By : "+Thread.currentThread().getName());
    }
}
