package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CustomScheduler extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Before to use the custom scheduler, we need to create an Executor service.
        //
        // Note: we are declaring a fixed thread pool with only 2 thread. Play with executor services and number of thread in order to the thread usage.
        // NOTE: THIS KIND OF THREAD POOL IS NOT A DAEMON unlike other cases where RxJava create its Scheduler.
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Scheduler scheduler = Schedulers.from(executor);

        // Given of list of Custom Tasks
        Observable<CustomTask> source = Observable.just(
                        create("First Custom Task"), create("Second Custom Task"),
                        create("Third Custom Task"), create("Fourth Custom Task"))
                // we want to use a Scheduler from an Executor Service when will subscribe on this Observable
                .subscribeOn(scheduler)
                // PAY ATTENTION. In order to complete the executor-service when need to invoke its shutdown when all ends or when all disposed
                .doFinally(executor::shutdown);
        int numAvailableProc = Runtime.getRuntime().availableProcessors();
        log("Number of available CPU: "+numAvailableProc);

        // Now we are going to subscribe a certain number of subscribers to our source
        // My current machine has 12 processors available.
        //
        // Now we are going to subscribe 1 Observable and wait for 5 seconds.
        log("--> Going to subscribe 1 Observable");
        disposables.add( source.subscribe( e -> e.doSomeTask("1th Observer")) );

        // So we are going to subscribe other 2 Observers in order to see how the thread will be used.
        // The thread usage now depends on the executor service chose.
        // Since we choose a single fixed thread pool with only 2 thread
        log("\n--> Going to subscribe other 3 Observables!");
        disposables.add( source.subscribe(e -> e.doSomeTask("2th Observer")) );
        disposables.add( source.subscribe(e -> e.doSomeTask("3th Observer")) );
        disposables.add( source.subscribe(e -> e.doSomeTask("4th Observer")) );

        // Since the executor service is not a daemon we don't need of these
        // But obviously if we want to dispose the subscribers before the tasks ends, we can do it.
//        log("Before Sleep..")
//        sleep(2, TimeUnit.SECONDS)
//        log("Ends...")
//        disposables.dispose()
    }

    private static CustomTask create(String label) {
        return new CustomTask() {
            @Override
            void doSomeTask(String message) {
                sleep(nextRandomLong(250, 1000), TimeUnit.MILLISECONDS);
                log(label+" | "+message+" | Custom Task Operation done by : "+Thread.currentThread().getName());
            }
        };
    }
}

abstract class CustomTask extends BasicExampleClass {
    abstract void doSomeTask(String message);
}
