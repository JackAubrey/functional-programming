package courses.basics_strong.reactive.section17;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.concurrent.TimeUnit;

public class Disposing extends BasicExampleClass {
    private static final CompositeDisposable compDisposable = new CompositeDisposable();

    public static void main(String[] args) {
        log("Starting Process!");

        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);

        compDisposable.add( source.subscribe(
                n -> log("Sub-1 | Received Number: " + n),
                ex -> log("Sub-1 | Found an error: " + ex),
                () -> log("Sub-1 | Completed")
        )) ;

        compDisposable.add( source.subscribe(
                n -> log("Sub-2 | Received Number: " + n),
                ex -> log("Sub-2 | Found an error: " + ex),
                () -> log("Sub-2 | Completed")
        )) ;

        int cnt = 0;
        while ( !compDisposable.isDisposed() ) {
            cnt++;
            if(cnt <= 10) {
                log("--> Wait " + cnt + "/10 ");
                sleep(1000, TimeUnit.MILLISECONDS);
            } else {
                log("--> Finish! I'm going to unsubscribe ");
                compDisposable.dispose();
            }
        }

        log("Process finished!");
    }
}
