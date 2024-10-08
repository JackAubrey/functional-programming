package courses.basics_strong.reactive.section20;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import java.util.List;

public class FlatMapAndConcatMap extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        List<String> list = List.of("Hello", "Reactive", "Programming");

        // given a list we create an Observable
        log("## FlatMap");
        disposables.add(
                Observable.fromIterable(list)
                    // the mapper used as lambda here
                    // takes each single string and transform it to an Observable from array
                    // this array is taken splitting the string element received
                    .flatMap( e -> Observable.fromArray(e.split("")))
                    // from the flatmap will obtain a sequence of single letters
                    .subscribe(FlatMapAndConcatMap::log)
        );

        // now do the same exercise but using concatMap
        log("\n## ConcatMap");
        disposables.add(
                Observable.fromIterable(list)
                    // the mapper used as lambda here
                    // takes each single string and transform it to an Observable from array
                    // this array is taken splitting the string element received
                    .concatMap( e -> Observable.fromArray(e.split("")))
                    // from the flatmap will obtain a sequence of single letters
                    .subscribe(FlatMapAndConcatMap::log)
        );

        // as we said, to see the difference we need use a mapper that works on separated thread to see merge working on interleaved manner.

        disposables.dispose();
    }
}
