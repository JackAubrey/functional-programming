package courses.basics_strong.reactive;

import io.reactivex.rxjava3.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class BasicExampleClass {
    private static final Logger logger = LoggerFactory.getLogger(BasicExampleClass.class);
    private static final Random random = new Random();

    protected BasicExampleClass() {
        super();
    }

    protected static long nextRandomLong(long from, long to) {
        return random.nextLong(from, to);
    }
    protected static void log(@NonNull Object e) {
        logger.info("{}", e);
    }

    protected static void logErr(@NonNull String e) {
        logger.error(e);
    }

    protected static void logErr(@NonNull Throwable t) {
        logger.error("Error: ", t);
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
