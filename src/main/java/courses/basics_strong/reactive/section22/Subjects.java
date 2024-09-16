package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Subjects extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // we can use subject for multiple observable sources
        // so subject will work as observer for those sources
        //
        // let's create two observable sources
        Observable<Integer> source1 = Observable.just(5, 10, 15, 20);
        Observable<Integer> source2 = Observable.just(50, 100, 150, 200);

        // so suppose a single observer was going to subscribe to these observable sources
        // if this was the case we could have subscribed to these sources using that observer
        // and these sources would have emitted their emissions one after the other
        //
        // for example:
        // "source1" will emit for its observer and then "source2" will emit for the same observer.
        log("## Normal way");
        disposables.add( source1.subscribe(e -> log("Observer 1: "+e)) );
        disposables.add( source2.subscribe(e -> log("Observer 2: "+e)) );

        // now let's see how we can use subject to subscribe both the sources simultaneously,
        // and we'll also see the difference between these two approaches.
        //
        // let's create the subject publish.
        // NOTE: "PublishSubject" is an implementation of "Subject"
        Subject<Object> subject = PublishSubject.create();

        // So as we know we can use subject as observable and observer both, and it works as a link between observable and observer
        //
        // In this first example below we have one observer that is subscribing to two sources.
        log("\n\n## Using Subjects");
        // so now the Subject is working as an Observable for the Observer used in "subscribe" method
        // that was directly subscribing to both "source1" and "source2"
        disposables.add( subject.subscribe(e -> log("Subject | Observer: "+e)) );

        // Is now subscribing to the subject and the subject itself will subscribe to these sources so it will work as a link.
        source1.subscribe(subject);
        source2.subscribe(subject);

        // Looking the result something looks like is going wrong, because only the "source1" emissions will be printed!
        //
        // This occurs because Subjects can multicast. Subjects are HotObservable that is they start emitting only once.
        // So when Subjects subscribe to the "source1" it started executing the observer passed to it
        // and that's why we didn't get emissions from the "source2" for this observer.
        //
        // See the Subject Async.

        log("--> Ends...");
        disposables.dispose();
    }
}
