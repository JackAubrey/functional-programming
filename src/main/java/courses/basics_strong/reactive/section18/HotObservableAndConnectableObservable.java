package courses.basics_strong.reactive.section18;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class HotObservableAndConnectableObservable extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    
    public static void main(String[] args) {
        // We need of a concurrent list implementation because the second HotObservable need to run on a separated thread.
        List<Integer> list = new CopyOnWriteArrayList<>(List.of(16, 17, 18));

        // This is an Interval Emitter Source. It is handled directly from RxJava
        ConnectableObservable<Long> hotObsInterval = Observable.interval(1, TimeUnit.SECONDS).publish();

        // NOTE:    This is a wrong example because an HoTObservable should emit continuously new values.
        //          Here we are forcing this. I left here this one just as example.
        //          Looking the output we see the circular printing of the list.
        //          An HotObservable should emit new values each time.
        // We want an Async Emitter to see the changes on our List
        // To achieve this we need to create a Thread that will start on our Observable when its "subscribe" method will be invoked
        // when we subscribe our Observable just created.
        ObservableOnSubscribe<Integer> observableOnSubscribe = e -> listAsyncEmitter(e, list);
        // So we create our Observable using the "ObservableOnSubscribe".
        ConnectableObservable<Integer> hotObsCreate = Observable.create( observableOnSubscribe ).publish();

        // we need to start this connectable in order start the firing of emitting
        log("--> Connecting to First Observable");
        hotObsInterval.connect();
        log("--> Connecting to Second Observable");
        hotObsCreate.connect();

        // subscribing the hot observable, wil be emitted values: 16, 17, 18
        log("--> First Subscribing to First Observable");
        disposables.add(
                hotObsInterval.subscribe(e -> log("Observer 1 | Interval | First Subscription Value: " + e))
        );

        log("--> First Subscribing to Second Observable");
        disposables.add(
                hotObsCreate.subscribe(e -> log("Observer 2 | Create | First Subscription Value: " + e))
        );

        log("--> Going to sleep for 3 seconds");
        sleep(3, TimeUnit.SECONDS);

        // now we want update our list
        log("--> Going to update the list");
        update(list, 19, 20, 21, 22);

        // if we subscribe again to our observable source we would expect to see only new values.
        log("--> Second Subscribing to First Observable");
        disposables.add(
                hotObsInterval.subscribe(e -> log("Observer 1 | Interval | Second Subscription Value: " + e))
        );

        log("--> Second Subscribing to Second Observable");
        disposables.add(
                hotObsCreate.subscribe(e -> log("Observer 2 | Create | Second Subscription Value: " + e))
        );

        log("--> Again! Going to sleep for 5 seconds");
        sleep(5, TimeUnit.SECONDS);

        log("--> Dispose all!");
        disposables.dispose();
    }

    private static void listAsyncEmitter(ObservableEmitter<Integer> e, List<Integer> list) {
        // as we will see later, RxJava has an Observable contract
        // The Observable must be sync, so to be compliance with this contract, we implement and start a thread.
        // In this manner the observable is sync but the emissions will be done in async manner.
        new Thread( () -> {
            // will loop our list until the disposables will not dispose
            while( !disposables.isDisposed() ) {
                for (Integer n : list) {
                    sleep(500, TimeUnit.MILLISECONDS);
                    e.onNext(n);
                }
            }
            e.onComplete();
        }).start();
    }

    private static void update(List<Integer> list, int... newValues) {
        for(int n:newValues) {
            list.add(n);
        }
    }
}
