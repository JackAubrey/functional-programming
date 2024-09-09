package courses.basics_strong.reactive.section16;

import courses.basics_strong.reactive.BasicExampleClass;
import courses.basics_strong.reactive.section16.interfaces.CallBack;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CallBackDemo extends BasicExampleClass {
    private final Random random = new Random();

    public static void main(String[] args) {
        long mainThreadId = Thread.currentThread().threadId();
        log("Main Thread: "+mainThreadId);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new CallBackDemo().runAsync(new CallBack() {
                    @Override
                    public void call(long threadId) {
                        log("<< CalBack called by thread:"+threadId);
                    }
                });
            }
        };

        Runnable runnableFunc = () -> new CallBackDemo().runAsync( threadId -> log("<< CalBack called by thread:"+threadId) );

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnableFunc);

        t1.start();
        t2.start();

        while(t1.isAlive() || t2.isAlive()) {
            log("I'm waiting threads completing....");
            sleep(1, TimeUnit.SECONDS);
        }

        log("Main Thread ["+mainThreadId+"] completed");
    }

    public void runAsync(CallBack callBack) {
        long threadId = Thread.currentThread().threadId();
        log(">> I'm running on thread: "+threadId);
        sleep(random.nextLong(500, 2000), TimeUnit.MILLISECONDS);
        callBack.call(threadId);
    }
}
