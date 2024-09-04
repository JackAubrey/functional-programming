package courses.basics_strong.reactive.section17;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class CreatingObserver {
    public static void main(String[] args) {
        // this is our observable
        Observable<String> observable = Observable.just("John", "Mark", "Zelda");

        // we create an observer implementing it
        Observer<String> observerFromInnerClass = creatingFromInnerClass();

        // now we are going to subscribe the Observable to the Observers
        observable.subscribe(observerFromInnerClass);
        // in this case we are providing Consumer lambadas. there are different variants
        observable.subscribe(
                s -> System.out.println("On Next simple: "+s)
        );
        observable.subscribe(
                s -> System.out.println("On Next: "+s),
                e -> System.err.println("On Error: "+e),
                () -> System.out.println("On Complete")
        );

    }


    private static Observer<String> creatingFromInnerClass() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("Value = "+s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.err.println("Error:" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("Finish");
            }
        };
    }
}
