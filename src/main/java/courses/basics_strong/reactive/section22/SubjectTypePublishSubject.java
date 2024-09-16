package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class SubjectTypePublishSubject extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // in this example we have created a PublishedSubject
        // You'll notice looking the output the emission of the second subscriber contains only value emitted after its subscriptions.
        // Unlike the first subscriber will receive all the emissions.
        Subject<Object> subject = PublishSubject.create();

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
