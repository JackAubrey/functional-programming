package courses.basics_strong.reactive.section17;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneratingEvents {
    public static void main(String[] args) {
        log("Starting Process!");
        Name fakerName = new Faker().name();

        AtomicInteger x = new AtomicInteger();
        Observable<String> source = Observable.generate( c -> {
            c.onNext(fakerName.fullName());
            if(x.incrementAndGet() > 10) {
                c.onComplete();
            } else {
                sleep(700, TimeUnit.MILLISECONDS);
            }
        });

        Disposable disposable = source.subscribe(
                n -> log("Received Name: " + n),
                ex -> log("Found an error: " + ex),
                () -> log("Completed")
        );

        log("Is Generator disposed ? "+disposable.isDisposed());

        log("Process finished!");
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
