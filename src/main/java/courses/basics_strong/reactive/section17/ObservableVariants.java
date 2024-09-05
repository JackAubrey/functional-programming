package courses.basics_strong.reactive.section17;


import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ObservableVariants {
    public static void main(String[] args) {
        Observable<String> source = Observable.just("Alex", "Justin", "Jack");
        Observable<String> sourceEmpty = Observable.empty();
        final String LINE = "###################################";

        // ###################################
        // ## Single Observable Variants
        // ###################################
            // We want to find the first element emitted from the "source"
            // To do that, we need to invoke on "source" the "first" method, and then we can subscribe on it
            // The method "first" wants a default value.
            log(LINE);
            log("## Single Observable Variants");
            log(LINE);

            log("# On Filled Source");
            source
                    .first("Default Value")
                    .subscribe(
                            e -> log("\tFirst on Filled List is: "+e));

            // if we do the same on empty source, will take our default value
            log("\n# On Empty Source");
            sourceEmpty
                    .first("Default Value")
                    .subscribe(e -> log("\tFirst on Empty List is: "+e));

            // anywhere in our program we need to create a single we can create it like this
            log("\n# Directly usage");
            Single<String> singleSource = Single.just("Hanna");
            singleSource
                    .subscribe(e -> log("\tFirst from Single.just(T) is: "+e));

        // ###################################
        // ## Maybe Observable Variants
        // ###################################
            // We would like to know if a particular user exists in our database or not.
            // So the user may or may not exist, so for it, we may have zero, or we may have one emission.
            // To do that, we need to invoke on "source" the "firstElement" method, and then we can subscribe on it
            log("\n"+LINE);
            log("## Maybe Observable Variants");
            log(LINE);

            log("# On Filled Source");
            // Note "onSuccess", "onError" and "onComplete" are mutually exclusive events; unlike Observable, "onSuccess" is never followed by "onError" or "onComplete".
            // Looking the logs you'll notice the "onComplete" will not invoke.
            source
                    .firstElement()
                    .subscribe(
                            e -> log("\tFirst Element is: "+e),
                            ex -> log("\tFound error: "+ex),
                            () -> log("\tMaybe Observable Completed"));

            // if we do the same on empty source, will take nothing.
            // in this case will be invoked only the "onComplete" method
            log("\n# On Empty Source");
            sourceEmpty
                    .firstElement()
                    .subscribe(
                            e -> log("\tFirst Element is: "+e),
                            ex -> log("\tFound error: "+ex),
                            () -> log("\tMaybe Observable Completed"));

            // also "Maybe" interface like "Single" offer factory methods
            log("\n# Directly usage");
            Maybe<String> maybeSource = Maybe.just("Hanna");
            maybeSource
                    .subscribe(e -> log("\tFirst from Maybe.just(T) is: "+e));


        // ###################################
        // ## Completable Observable Variants
        // ###################################
            log("\n"+LINE);
            log("## Completable Observable Variants");
            log(LINE);
            // we can obtain a Completable from one source methods
            source
                    .concatMapCompletable( s -> Completable.fromRunnable( () -> log("Concat Map: "+s) ))
                    .subscribe(() -> log("\tCompleted Action from ConcatMap"));

            // We can obtain a Completable using one of its factory methods
            log("\n# Directly usage");
            Completable.fromSingle(Single.just("Elisa"))
                .subscribe(() -> log("\tCompleted Action from Single"));

            Completable completableSource = Completable.fromRunnable( () -> {
                log("\n--> Into Completable.fromRunnable... we will wait 2 seconds");
                sleep(2, TimeUnit.SECONDS);
                log("\n--> Exiting Completable.fromRunnable");
            });

            Disposable completableDisposable = completableSource
                    .subscribe(() -> log("\tCompleted Action from Runnable"));

            while ( !completableDisposable.isDisposed() ) {
                log("\t* Waiting Completable ends....");
                sleep(1000, TimeUnit.MILLISECONDS);
            }
    }

    private static void sleep(int period, TimeUnit unit) {
        try {
            unit.sleep(period);
        } catch (InterruptedException e) {
            log(e.toString());
            Thread.currentThread().interrupt();
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
