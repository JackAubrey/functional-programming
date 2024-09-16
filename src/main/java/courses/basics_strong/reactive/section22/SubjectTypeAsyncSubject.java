package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class SubjectTypeAsyncSubject extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // in this example we have created a AsyncSubject
        // You'll notice looking the output the emission:
        //      - for both the subscribers or for both the observers it only gives the last emission
        //
        // One point to remember about AsyncSubject is that it will only emit the emissions when "onComplete" is called.
        // Try to comment the "onComplete" call, nothing will appear.
        // we shouldn't use this with infinite observables
        Subject<Object> subject = AsyncSubject.create();

        // and then an observer is subscribing to the subject
        log("--> Subscribing first Observer");
        disposables.add( subject.subscribe(e -> log("Subscriber 1 : "+e)) );

        // then we have some "onNext" calls on the subject to emit some elements
        subject.onNext("A");
        subject.onNext("B");
        subject.onNext("C");

        log("--> Subscribing second Observer");
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
