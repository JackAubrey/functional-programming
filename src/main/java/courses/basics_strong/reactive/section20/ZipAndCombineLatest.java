package courses.basics_strong.reactive.section20;


import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;

import java.util.concurrent.TimeUnit;

public class ZipAndCombineLatest extends BasicExampleClass {

    public static void main(String[] args) {
        Observable<Long> source1 = Observable.interval(200, TimeUnit.MILLISECONDS);
        Observable<Long> source2 = Observable.interval(1, TimeUnit.SECONDS);

        // Zip.
        // As we can see running this demo, despite the two emissions has different ratio, the faster one must wait the slower.
        // Both emissions will be printed in pair.
        // NOTE: If you try to limit the source using "take(count)", you'll not see any difference on resulting output.
        BiFunction<Long, Long, String> zipper = (e1, e2) -> "Zipped [Source-1: "+e1 + ", Source-2: " + e2+"]";
        Disposable zipDisposable = Observable.zip(source1, source2, zipper)
                .subscribe(ZipAndCombineLatest::log);

        sleep(7, TimeUnit.SECONDS);
        zipDisposable.dispose();

        // Combine Latest
        // As we can see running this demo, the latest value given from slower one will be combined with the latest value received from faster one.
        // NOTE: If you try to limit the source using "take(count)", you'll see the latest value emitted by the first one will be used to pair with all rest ov values emitted by the slower one.
        BiFunction<Long, Long, String> combiner = (e1, e2) -> "Combined [Source-1: "+e1 + ", Source-2: " + e2+"]";
        Disposable combinedDisposable = Observable.combineLatest(source1, source2, combiner)
                .subscribe(ZipAndCombineLatest::log);

        sleep(10, TimeUnit.SECONDS);

        combinedDisposable.dispose();
    }
}
