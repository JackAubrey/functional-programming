package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ConcurrencyInRxJava extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();
    public static final String OBSERVE_1 = "Observe 1: ";
    public static final String OBSERVE_2 = "Observe 2: ";
    public static final String ON_THREAD = " - On Thread: ";

    public static void main(String[] args) {
        // In order to understand the Concurrency in RxJava.
        // We are going to resume the first Observable creation example. We will se run the emission on the main thread.
        // This means they are running sequentially one after the other.
        log("## Simple sequentially example");
        simpleExample();

        // What if we do this emission work or these method calls inside a runnable and create a thread and start it?
        // Both observers will run on separated threads.
        // This occurs because we implemented and started a new Thread inside on the "subscribe" method provided by "ObservableOnSubscribe" interface.
        // We did this using a lambada to implement that methods.
        //
        // So when an Observer comes and subscribe it always gets the emissions from a newly created thread.
        // So you create any number of observers THEY'LL ALL GET EMISSIONS ASYNCHRONOUSLY
        // and THIS WILL EVEN NOT BREAK THE OBSERVABLE CONTRACT WHICH DOES NOT ALLOW TO PASS EMISSIONS ASYNCHRONOUSLY.
        //
        // WE ARE NOT PASSING THE EMISSIONS ASYNCHRONOUSLY rather always a new thread sends the emissions to the observer synchronously.
        // Async behavior is all about processing multiple subscriptions in parallel rather than processing the emissions to the same sub observer concurrently.
        //
        // The interleaving is not visible here because we are not doing much in these chains, but they are getting executed in parallel mode.
        //
        // NOTE:    Ee don't always go with creating thread in observable to achieve this.
        //          RxJava HAS ALREADY IMPLEMENTED DIFFERENT SCHEDULERS FOR US THAT ARE USED TO HAVE THE ASYNC BEHAVIOR.
        log("\n## Simple example with emission produced by a thread");
        simpleExampleUsingThread();
    }

    private static void simpleExample() {
        Observable<String> source = Observable.create( e -> {
            e.onNext("Hello");
            e.onNext("RxJava");
        });

        disposables.add(
                source.subscribe(e -> BasicExampleClass.log(OBSERVE_1 +e+ ON_THREAD +Thread.currentThread().getName()))
        );

        disposables.add(
                source.subscribe(e -> BasicExampleClass.log(OBSERVE_2 +e+ ON_THREAD +Thread.currentThread().getName()))
        );
    }

    private static void simpleExampleUsingThread() {
        Observable<String> source = Observable.create( e ->
            new Thread( () -> {
                e.onNext("Hello");
                e.onNext("RxJava");
            }).start()
        );

        disposables.add(
                source.subscribe(e -> BasicExampleClass.log(OBSERVE_1 +e+ ON_THREAD +Thread.currentThread().getName()))
        );

        disposables.add(
                source.subscribe(e -> BasicExampleClass.log(OBSERVE_2 +e+ ON_THREAD +Thread.currentThread().getName()))
        );
    }
}
