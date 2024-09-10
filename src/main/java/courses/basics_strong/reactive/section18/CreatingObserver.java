package courses.basics_strong.reactive.section18;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class CreatingObserver extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // this is our observable
        Observable<String> observable = Observable.just("John", "Mark", "Zelda");

        // we create an observer implementing it
        Observer<String> observerFromInnerClass = creatingFromInnerClass();

        // now we are going to subscribe the Observable to the Observers
        observable.subscribe(observerFromInnerClass);
        // in this case we are providing Consumer lambadas. there are different variants
        disposables.add(
                observable.subscribe(s -> log("On Next simple: "+s))
        );
        disposables.add(
                observable.subscribe(
                    s -> log("On Next: "+s),
                    e ->logErr("On Error: "+e),
                    () -> log("On Complete")
                )
        );

        disposables.dispose();
    }


    private static Observer<String> creatingFromInnerClass() {
        return new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                log("Subscribed");
            }

            @Override
            public void onNext(@NonNull String s) {
                log("Value = "+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
               logErr("Error:" + e);
            }

            @Override
            public void onComplete() {
                log("Finish");
            }
        };
    }
}
