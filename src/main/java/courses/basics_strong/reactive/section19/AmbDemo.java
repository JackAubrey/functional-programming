package courses.basics_strong.reactive.section19;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AmbDemo extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args)  {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS)
                .take(10)
                .map(e -> "Source-1: "+e);
        Observable<String> source2 = Observable.interval(10, TimeUnit.MILLISECONDS)
                .take(10)
                .map(e -> "Source-2: "+e);

        disposables.add(
                Observable.amb(List.of(source1, source2))
                        .subscribe(BasicExampleClass::log)
        );

        sleep(5000, TimeUnit.MILLISECONDS);

        disposables.dispose();
    }
}
