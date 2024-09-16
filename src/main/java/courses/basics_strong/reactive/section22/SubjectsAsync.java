package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.concurrent.TimeUnit;

public class SubjectsAsync extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // As we saw in "Subjects" example, it is HotObservable.
        // In order to solve the problem saw in that example we will use async sources
        //
        // let's create two async observable sources using the Schedulers
        Observable<Integer> source1 = Observable.just(5, 10, 15, 20)
                .subscribeOn(Schedulers.computation());
        Observable<Integer> source2 = Observable.just(50, 100, 150, 200)
                .subscribeOn(Schedulers.computation());

        // let's create the subject publish.
        Subject<Object> subject = PublishSubject.create();

        // and subscribe the Subject to Observer
        log("\n\n## Using ASYNC Subjects");
        disposables.add( subject.subscribe(e -> log("Subject | ASYNC Observer: "+e)) );

        // and now link the async sources to our subject
        // subscribing to the subject at the same time rather than subscribing it sequentially
        //
        // we are getting all the emissions merged from "source1" and "source2" all at one place at one time.
        source1.subscribe(subject);
        source2.subscribe(subject);

        // a sleep to see the async result
        sleep(2, TimeUnit.SECONDS);

        // now we get all the emissions they are working in parallel
        // because now these sources are working concurrently
        // these chains will be executed on different threads



        // when we have single observable and multiple observers
        // you can pass it to a single observable source.
        // see the SubjectAsync2 example

        log("--> Ends...");
        disposables.dispose();
    }
}
