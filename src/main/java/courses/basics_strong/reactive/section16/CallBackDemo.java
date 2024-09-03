package courses.basics_strong.reactive.section16;

import courses.basics_strong.reactive.section16.interfaces.CallBack;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CallBackDemo {
    private final Random random = new Random();

    public static void main(String[] args) {
        long mainThreadId = Thread.currentThread().threadId();
        System.out.println("Main Thread: "+mainThreadId);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new CallBackDemo().runAsync(new CallBack() {
                    @Override
                    public void call(long threadId) {
                        System.out.println("<< CalBack called by thread:"+threadId);
                    }
                });
            }
        };

        Runnable runnableFunc = () -> new CallBackDemo().runAsync( threadId -> System.out.println("<< CalBack called by thread:"+threadId) );

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnableFunc);

        t1.start();
        t2.start();

        while(t1.isAlive() || t2.isAlive()) {
            System.out.println("I'm waiting threads completing....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Main Thread ["+mainThreadId+"] completed");
    }

    public void runAsync(CallBack callBack) {
        long threadId = Thread.currentThread().threadId();
        System.out.println(">> I'm running on thread: "+threadId);
        sleep(random.nextLong(500, 2000));
        callBack.call(threadId);
    }

    private void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
