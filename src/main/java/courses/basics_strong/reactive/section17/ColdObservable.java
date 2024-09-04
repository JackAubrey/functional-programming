package courses.basics_strong.reactive.section17;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;

public class ColdObservable {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(16, 17, 18));

        Observable<Integer> coldObservable = Observable.fromIterable(list);

        // subscribing the cold observable, wil be emitted values: 16, 17, 18
        System.out.println("First Subscription");
        coldObservable.subscribe(System.out::println);

        // now we want update our list
        list = update(list, 19, 20, 21);

        // if we subscribe again to our observable source we would expect to see old and new values.
        // as we expected we both old and new values
        System.out.println("\nSecond Subscription");
        coldObservable.subscribe(System.out::println);
    }

    private static List<Integer> update(List<Integer> list, int... newValues) {
        for(int n:newValues) {
            list.add(n);
        }
        return list;
    }
}
