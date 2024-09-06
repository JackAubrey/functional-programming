package courses.basics_strong.reactive.section19;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class MergeAndConcat {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // given this two sources
        Observable<String> source1 = Observable.just("Ella", "Alexa", "Lilly");
        Observable<String> source2 = Observable.just("Priya", "Chloe");

        // we want merge
        log("## Merging two Sources");
        Disposable mergeDisposable = Observable
                .merge(source1, source2)
                .subscribe(MergeAndConcat::log);
        disposables.add(mergeDisposable);

        // we want concat
        log("\n## Concatenating two Sources");
        Disposable concatDisposable = Observable
                .concat(source1, source2)
                .subscribe(MergeAndConcat::log);
        disposables.add(concatDisposable);

        // NOTE that since we are using "just" in a single thread both results are equals
        // But wat about if we use source in a separated threads?
        Observable<String> intervalMergeSource1 = Observable.interval(1, TimeUnit.SECONDS).map(l -> "Merge Src-1: "+l);
        Observable<String> intervalMergeSource2 = Observable.interval(2, TimeUnit.SECONDS).map(l -> "Merge Src-2: "+l);

        log("\n## Merging two Sources came from interval");
        Disposable intervalMergeDisposable = Observable
                .merge(intervalMergeSource1, intervalMergeSource2)
                .subscribe(MergeAndConcat::log);
        disposables.add(intervalMergeDisposable);

        // We need to wait a bit to see the merging result
        sleep(5, TimeUnit.SECONDS);
        intervalMergeDisposable.dispose();

        // since concat need to work sequentially, if we do not limit the first source, the second one will not be concatenated.
        Observable<String> intervalConcatSource1 = Observable.interval(1, TimeUnit.SECONDS).map(l -> "Concat Src-1: "+l).take(3);
        Observable<String> intervalConcatSource2 = Observable.interval(2, TimeUnit.SECONDS).map(l -> "Concat Src-2: "+l).take(7);

        log("\n## Concatenating two Sources came from interval");
        Disposable intervalConcatDisposable = Observable
                .concat(intervalConcatSource1, intervalConcatSource2)
                .subscribe(MergeAndConcat::log);
        disposables.add(intervalConcatDisposable);

        // We need to wait a bit to see the concatenating result
        sleep(10, TimeUnit.SECONDS);

        disposables.dispose();
    }

    private static void log(@NonNull Object e) {
        System.out.println(e);
    }

    private static void sleep(int period, TimeUnit unit) {
        try {
            unit.sleep(period);
        } catch (InterruptedException e) {
            log(e.toString());
            Thread.currentThread().interrupt();
        }
    }
}
