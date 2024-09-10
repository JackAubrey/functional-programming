package courses.basics_strong.reactive.section18;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.ArrayList;
import java.util.List;

public class ColdObservable extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(16, 17, 18));

        Observable<Integer> coldObservable = Observable.fromIterable(list);

        // subscribing the cold observable, wil be emitted values: 16, 17, 18
        log("First Subscription");
        disposables.add(
                coldObservable.subscribe(BasicExampleClass::log)
        );

        // now we want update our list
        update(list, 19, 20, 21);

        // if we subscribe again to our observable source we would expect to see old and new values.
        // as we expected we both old and new values
        log("\nSecond Subscription");
        disposables.add( 
                coldObservable.subscribe(BasicExampleClass::log)
        );
    }

    private static List<Integer> update(List<Integer> list, int... newValues) {
        for(int n:newValues) {
            list.add(n);
        }
        return list;
    }
}
