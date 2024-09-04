package courses.basics_strong.reactive.section17;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class HelloRxJava {
    public static void main(String[] args) {
        // this is RxJava Observable. Do not confuse it with our previous example interface.
        //
        // we will use it to emit events or data one after one like a stream
        // We create using its factory method "create"
        //
        // this factory method wants an ObservableOnSubscribe<T>
        //
        // ObservableOnSubscribe<String> sub = (source) -> ......;
        //
        // We are configuring our observable source
        Observable<String> sourceObservable = Observable.create( source -> {
            // this e is an emitter, it will emit one element then the next element and so on
            // for emitting the element one after the other we have one next method
            source.onNext("Hello");
            source.onNext("RxJava");
        });

        // we're subscribing our observable source to ITSELF, and printing the events received
        // two things happening here
        //  1 - we are subscribing our Observable to itself in order to allow itself the access to stream ov events.
        //  2 - for each event emitted from the Observable source, it prints
        // this is one observer that is listening to the source observable but of course there can be many and this is what the idea is
        Disposable subscribe1 = sourceObservable.subscribe(e -> System.out.println("Observer 1: " + e));
        // we can create another observable
        Disposable subscribe2 = sourceObservable.subscribe(e -> System.out.println("Observer 2: " + e));
        // now both the observers will listen to the events emitted by the source observable one after the other
        //
        // NOTE: for now everything is SYNCHRONOUS, WE CAN'T GO ASYNC OR CONCURRENT until unless we don't actually ask for it.
        //       This sync behaviour is the default.
        //
        // The point here is we can create as many observer as we need that does operations on the same data emitted by observable source.

        // just print the status of Disposable
        System.out.println("First Observable Is disposed ? "+subscribe1.isDisposed());
        System.out.println("Second Observable Is disposed ? "+subscribe2.isDisposed());
    }
}
