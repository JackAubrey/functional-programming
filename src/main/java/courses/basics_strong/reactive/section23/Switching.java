package courses.basics_strong.reactive.section23;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Switching extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // given an observable source
        Observable<String> source = Observable.just("John", "Lilly", "Emma", "Ryan", "Darshan")
                // now lets introducing a delay between two consecutive emissions
                // to do that we'll use "concatMap" and giving a delay for each new observable
                // the mapper passed to the "concatMap"
                //      1- take the emission (e)
                //      2- create the Observable with "just" the same element (e) received
                //      3- and then introduce a random delay
                .concatMap( e -> Observable.just(e).delay(ThreadLocalRandom.current().nextLong(1000), TimeUnit.MILLISECONDS));

        // Now see how the "switchMap" operator works

        // Create a new time-based Observable
        Observable<String> interval = Observable.interval(2, TimeUnit.SECONDS)
                // and on this Observable apply the "switchMap" operator
                // as mapper, we take the emission and going to write using the source
                .switchMap(i -> source
                        // we map the source with a new string in order to see
                        //      - the interval element "i"
                        //      - the time of invocation
                        //      - and the source value
                        .map(s -> i+" - ["+LocalTime.now()+"] - "+s)
                        // when the source will be disposed we get a log
                        .doOnDispose(
                                () -> log("Disposing and restart the "+i+" iteration.")
                        ));

        disposables.add( interval.subscribe(Switching::log) );

        sleep(10, TimeUnit.SECONDS);

        // if you see we are getting emissions that are emitted by the source in this duration
        // so in two seconds the elements returned by the source
        // are getting printed on the console, and then it again restarts
        // play with ThreadLocal value and Observable interval value

        log("--> Ends...");
        disposables.dispose();
    }
}
