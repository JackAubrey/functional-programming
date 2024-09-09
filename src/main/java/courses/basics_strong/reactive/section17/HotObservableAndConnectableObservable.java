package courses.basics_strong.reactive.section17;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HotObservableAndConnectableObservable extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>(List.of(16, 17, 18));

        ConnectableObservable<Long> hotObservable = Observable.interval(1, TimeUnit.SECONDS).publish();
        ConnectableObservable<Integer> hotObservable2 = Observable.fromIterable(list).publish();

        // we need to start this connectable in order start the firing of emitting
        hotObservable.connect();
        hotObservable2.connect(); // seems to not works. Why? I'm waiting the teacher response.

        // subscribing the cold observable, wil be emitted values: 16, 17, 18
        disposables.add(
                hotObservable.subscribe(e -> log("First Subscription Value: "+e))
        );
        log("First Subscription");
        disposables.add(
                // nothing will be printed. Why? I'm waiting the teacher response.
                hotObservable2.subscribe(e -> log("Second Case | First Subscription Value: "+e))
        );

        TimeUnit.SECONDS.sleep(3);

        // now we want update our list
        update(list, 19, 20, 21);

        // if we subscribe again to our observable source we would expect to see old and new values.
        // as we expected we both old and new values
        disposables.add(
                hotObservable.subscribe(e -> log("Second Subscription Value: "+e))
        );
        log("Second Subscription");
        disposables.add(
                // nothing will be printed. Why? I'm waiting the teacher response.
                hotObservable2.subscribe(e -> log("Second Case | Second Subscription Value: "+e))
        );

        TimeUnit.SECONDS.sleep(3);
    }

    private static void update(List<Integer> list, int... newValues) {
        for(int n:newValues) {
            list.add(n);
        }
    }
}
