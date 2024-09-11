package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class SingleScheduler extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Given of list of Single Tasks
        // WE ASSUME FOR EXAMPLE THE TASK TO BE PERFORMED IS NOT THREAD SAFE.
        // In this case, the "single" scheduler is useful, it will use only one thread and perform all the operations sequentially.
        Observable<SingleTask> source = Observable.just(
                        create("First Single Task"), create("Second Single Task"),
                        create("Third Single Task"), create("Fourth Single Task"))
                // we want to use a Scheduler for "singled" when will subscribe on this Observable
                .subscribeOn(Schedulers.single());
        int numAvailableProc = Runtime.getRuntime().availableProcessors();
        log("Number of available CPU: "+numAvailableProc);

        // Now we are going to subscribe a certain number of subscribers to our source
        // My current machine has 12 processors available.
        //
        // Now we are going to subscribe 1 Observable and wait for 5 seconds.
        log("--> Going to subscribe 1 Observable for 5 seconds");
        disposables.add( source.subscribe(SingleTask::doSensitiveTask) );
        sleep(5, TimeUnit.SECONDS);

        // So we are going to subscribe other 2 Observers in order to see how the thread will be used.
        // You'll see only one thread will be used and all task performed sequentially
        log("\n--> Going to subscribe other 2 Observables!");
        disposables.add( source.subscribe(SingleTask::doSensitiveTask) );
        disposables.add( source.subscribe(SingleTask::doSensitiveTask) );

        // wait 10 seconds
        sleep(10, TimeUnit.SECONDS);

        // and dispose all
        log("Ends...");
        disposables.dispose();

    }

    private static SingleTask create(String label) {
        return new SingleTask() {
            @Override
            void doSensitiveTask() {
                sleep(nextRandomLong(250, 1000), TimeUnit.MILLISECONDS);
                log(label+" | Single Task Operation done by : "+Thread.currentThread().getName());
            }
        };
    }
}

abstract class SingleTask extends BasicExampleClass {
    abstract void doSensitiveTask();
}
