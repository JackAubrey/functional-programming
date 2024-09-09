package courses.basics_strong.reactive;

import io.reactivex.rxjava3.annotations.NonNull;

import java.util.concurrent.TimeUnit;

public abstract class BasicExampleClass {
    protected static void log(@NonNull Object e) {
        System.out.println(e);
    }

    protected static void logErr(@NonNull Object e) {
        System.err.println(e);
    }

    protected static void sleep(long period, @NonNull TimeUnit unit) {
        try {
            unit.sleep(period);
        } catch (InterruptedException e) {
            log(e.toString());
            Thread.currentThread().interrupt();
        }
    }
}
