package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.Subject;
import io.reactivex.rxjava3.subjects.UnicastSubject;

import java.util.concurrent.TimeUnit;

public class SubjectTypeUnicastSubject extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // in this example we have created a UnicastSubject
        // this example differs to previous because with UnicastSubject we can use only one subscriber.
        //
        // looking the output:
        //      - we are creating an interval observable that emit an event every 1/2 second
        //      - and subscribe it to a subject
        //      - doing the subscribing to a subject firing immediately starts but will buffer until some observer will be subscribed.
        //      - but after this we paused the main thread for 2 seconds
        //      - when the sleep ends, we subscribe our subject to the source
        //      - now all the emission produced by our Interval till that moment immediately fired altogether and received from our Subject Observer.
        //      - going to sleep for other 2 seconds during that the subject observer will receive emission every 1/2 second.
        Subject<Object> subject = UnicastSubject.create();

        // Now we are going to create an Observable using interval.
        // and subscribe it to the subject
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .subscribe(subject);

        // now sleep the main thread for 2 seconds
        log("--> Going to sleep for 2 seconds");
        sleep(2, TimeUnit.SECONDS);

        // and then an observer is subscribing to the subject
        log("--> Subscribing the Observer");
        disposables.add( subject.subscribe(e -> log("Subscriber : "+e)) );

        log("--> Again going to sleep for 2 seconds");
        sleep(2, TimeUnit.SECONDS);

        // and then finally we have the "onComplete"
        subject.onComplete();

        log("--> Ends...");
        disposables.dispose();
    }
}
