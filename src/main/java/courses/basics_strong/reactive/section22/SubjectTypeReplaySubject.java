package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;

public class SubjectTypeReplaySubject extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // in this example we have created a ReplaySubject
        // You'll notice looking the output the emission:
        //      - first subscriber receive all the emissions from A to C
        //      - the second subscriber now comes in play
        //      - it will receive all the emission produces till that moment
        //      - now both continue to receive all the rest of emissions
        Subject<Object> subject = ReplaySubject.create();

        // and then an observer is subscribing to the subject
        disposables.add( subject.subscribe(e -> log("Subscriber 1 : "+e)) );

        // then we have some "onNext" calls on the subject to emit some elements
        subject.onNext("A");
        subject.onNext("B");
        subject.onNext("C");

        // after we have another observer that is subscribing to the subject
        disposables.add( subject.subscribe(e -> log("Subscriber 2 : "+e)) );

        // and then some more "onNext" calls to emit some data
        subject.onNext("E");
        subject.onNext("F");
        subject.onNext("G");

        // and then finally we have the "onComplete"
        subject.onComplete();

        log("--> Ends...");
        disposables.dispose();
    }
}
