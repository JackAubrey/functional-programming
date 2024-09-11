package courses.basics_strong.reactive.section21.schedulers;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class NewThreadScheduler extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Given of list of Tasks
        Observable<Task> source = Observable.just(
                        create("Generic Task"), create("File Move Task"),
                        create("DB Sanitize Task"), create("Log Analysis Task"),
                        create("Push Notifications Task"))
                // we want to use a Scheduler for "New Thread" when will subscribe on this Observable
                .subscribeOn(Schedulers.newThread());
        int numAvailableProc = Runtime.getRuntime().availableProcessors();
        log("Number of available CPU: "+numAvailableProc);

        // Now we are going to subscribe a certain number of subscribers to our source
        // My current machine has 12 processors available.
        //
        // Now we are going to subscribe 1 Observable and wait for 5 seconds.
        log("--> Going to subscribe 1 Observable for 5 seconds");
        disposables.add( source.subscribe(Task::doTask) );
        sleep(5, TimeUnit.SECONDS);

        // So we are going to subscribe a number of observers two times greater than our available processors in order to see how the thread will be used.
        // You'll see threads grows up but in this case gets terminated after completing the task and NOT REUSED.
        // If you look the output, THE FIRST THREAD used for the single subscription did upon, WILL NOT APPEAR AGAIN.
        log("\n--> Going to subscribe "+(numAvailableProc*2)+" Observables!");
        for(int i=0; i<numAvailableProc*2; i++) {
            disposables.add( source.subscribe(Task::doTask) );
        }

        // wait 10 seconds
        sleep(10, TimeUnit.SECONDS);

        // and dispose all
        log("Ends...");
        disposables.dispose();

    }

    private static Task create(String label) {
        return new Task() {
            @Override
            void doTask() {
                sleep(nextRandomLong(300, 1500), TimeUnit.MILLISECONDS);
                log(label+" | Task Operation done by : "+Thread.currentThread().getName());
            }
        };
    }
}

abstract class Task extends BasicExampleClass {
    abstract void doTask();
}
