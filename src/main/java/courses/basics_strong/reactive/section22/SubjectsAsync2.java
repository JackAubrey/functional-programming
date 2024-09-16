package courses.basics_strong.reactive.section22;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.concurrent.TimeUnit;

public class SubjectsAsync2 extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // when we have single observable and multiple observers
        //
        // let's create a single observable sources using the Schedulers
        Observable<Integer> source1 = Observable.just(5, 10, 15, 20)
                .subscribeOn(Schedulers.computation());

        // let's create the subject publish.
        Subject<Object> subject = PublishSubject.create();

        // and subscribe two times the Subject
        // you can pass it to a single observable source.
        log("\n\n## Using ASYNC Subjects");
        disposables.add( subject.subscribe(e -> log("Subject | ASYNC Observer-1: "+e)) );
        disposables.add( subject.subscribe(e -> log("Subject | ASYNC Observer-2: "+e)) );

        // it will provide emissions to all those observables
        // to let them do whatever they are going to do with those emissions
        source1.subscribe(subject);

        // a sleep to see the async result
        sleep(2, TimeUnit.SECONDS);

        // now we get all the emissions they are working in parallel
        // because now these sources are working concurrently
        // these chains will be executed on different threads




        // see the SubjectAsync2 example


        log("--> Ends...");
        disposables.dispose();
    }
}
