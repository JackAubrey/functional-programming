package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class SubjectsAddEmission extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Subjects exposes methods:
        //  - onNext
        //  - onError
        //  - onComplete
        // we can manually invoke them on the subject that will in turn pass the events downstream toward all of its observers or subscribers
        Subject<Object> subject = PublishSubject.create()
                // since Subject is not thread-safe we can invoke the "toSerialized" method to achieve this
                .toSerialized();

        // Let's see!!
        // We subscribe the Subject to an Observer in order to log all the emissions
        disposables.add( subject.subscribe(SubjectsAddEmission::log) );
        // UNCOMMENT AFTER
        // disposables.add( subject.subscribe( e -> log("Observer 2: "+e)) );

        // Now we manually invoke the OnNext method
        subject.onNext("Hello");
        subject.onNext("BasicStrong");

        // and now invoke OnComplete
        // NOTE: The "onComplete" specify the observer that observable is now done with emitting data.
        subject.onComplete();

        // since we invoked the "onComplete" method if we call again the "onNext", nothing will happen.
        subject.onNext("Hey!!!!");

        // now if we increase the observers that are observing these emissions
        // TO ACHIEVE uncomment the second subscription

        log("--> Ends...");
        disposables.dispose();
    }
}
