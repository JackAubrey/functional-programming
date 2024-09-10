package courses.basics_strong.reactive.section18;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Random;

public class ObservableAndObserver extends BasicExampleClass {
    public static void main(String[] args) {
        final Random random = new Random();

        Observable<Integer> observable = Observable.create( source -> {
            int cnt = 0;

            try {
                while (++cnt < 10) {
                    int value = random.nextInt(1, 110);

                    if(value <= 100) {
                        source.onNext(value);
                    } else {
                        throw new IllegalArgumentException("Found a value greater than 100: "+value);
                    }
                }
                source.onComplete();
            } catch (Exception e) {
                source.onError( new RuntimeException("Caught error!", e));
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                log("On Subscribe of Disposable"+d);
            }

            @Override
            public void onNext(@NonNull Integer value) {
                log("On Next value: "+value);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                logErr("On Error: "+e);
            }

            @Override
            public void onComplete() {
                log("On Complete");
            }
        };

        observable.subscribe(observer);
    }
}
