package courses.basics_strong.reactive.section23;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Windowing extends BasicExampleClass {

    public static void main(String[] args) {
        // given observable with a range from 10 to 40
        log("## Simple Window Size");
        Disposable disposable1 = Observable.range(10, 30)
                // we are windowing 4 items.
                // buffer returns an Observable<Observable<T>>
                // ??
                .window(4)
                .subscribe(Windowing::log);
        disposable1.dispose();

        // looking the output you'll see data grouped on group of 4

        log("\n## Simple Window Size with skip");
        Disposable disposable3 = Observable.range(10, 30)
                // we are windowing 4 items skipping data.
                // skip means: how many items emitted by the current Observable should be skipped before starting a new buffer
                // it returns an Observable<Observable<T>>
                // ??
                .window(4, 7)
                .subscribe(Windowing::log);
        disposable3.dispose();

        log("\n## Window using interval span");
        Disposable disposable5 = Observable.interval(250, TimeUnit.MILLISECONDS)
                // we are windowing data with spans every 2 seconds
                // since
                //      - the interval emit a value every 1/4 seconds
                //      - buffer takes span every 2 seconds
                //      - sleep the main thread for 6 seconds
                // whe should obtain 3 groups with elements close to 8 (sometimes could be 7, sometimes 9)
                .window(2, TimeUnit.SECONDS)
                .subscribe(Windowing::log);
        sleep(6, TimeUnit.SECONDS);
        disposable5.dispose();

        log("\n## Window using interval span and max");
        Disposable disposable6 = Observable.interval(250, TimeUnit.MILLISECONDS)
                // we are windowing data with spans every 2 seconds and max 5 elements
                // since
                //      - the interval emit a value every 1/4 seconds
                //      - buffer takes span every 2 seconds but max 5 elements
                //      - sleep the main thread for 6 seconds
                // whe should obtain 5 groups of 5 elements plus another group of 1 element
                .window(2, TimeUnit.SECONDS, 5)
                .subscribe(Windowing::log);
        sleep(6, TimeUnit.SECONDS);
        disposable6.dispose();

        log("\n## Window using Observable boundaryIndicator");
        Observable<Long> boundary = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable7 = Observable.interval(500, TimeUnit.MILLISECONDS)
                // we are windowing data with spans using another interval as boundary
                // since
                //      - the boundary interval is 1 seconds
                //      - this interval emit an event every 1/2 second
                //      - sleep the main thread for 6 seconds
                // whe should obtain 6 groups of 2 elements
                .window(boundary)
                .subscribe(Windowing::log);
        sleep(6, TimeUnit.SECONDS);
        disposable7.dispose();

        log("--> Ends...");
    }
}
