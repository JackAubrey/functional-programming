package courses.basics_strong.reactive.section17;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HotObservableAndConnectableObservable {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>(List.of(16, 17, 18));

        ConnectableObservable<Long> hotObservable = Observable.interval(1, TimeUnit.SECONDS).publish();
        ConnectableObservable<Integer> hotObservable2 = Observable.fromIterable(list).publish();

        // we need to start this connectable in order start the firing of emitting
        hotObservable.connect();
        hotObservable2.connect(); // seems to not works. Why? I'm waiting the teacher response.

        // subscribing the cold observable, wil be emitted values: 16, 17, 18
        hotObservable.subscribe(e -> System.out.println("First Subscription Value: "+e));
        System.out.println("First Subscription");
        hotObservable2.subscribe(e -> System.out.println("Second Case | First Subscription Value: "+e)); // nothing will be printed. Why? I'm waiting the teacher response.

        TimeUnit.SECONDS.sleep(3);

        // now we want update our list
        list = update(list, 19, 20, 21);

        // if we subscribe again to our observable source we would expect to see old and new values.
        // as we expected we both old and new values
        hotObservable.subscribe(e -> System.out.println("Second Subscription Value: "+e));
        System.out.println("Second Subscription");
        hotObservable2.subscribe(e -> System.out.println("Second Case | Second Subscription Value: "+e)); // nothing will be printed. Why? I'm waiting the teacher response.

        TimeUnit.SECONDS.sleep(3);
    }

    private static List<Integer> update(List<Integer> list, int... newValues) {
        for(int n:newValues) {
            list.add(n);
        }
        return list;
    }
}
