package courses.basics_strong.reactive.section16;

import courses.basics_strong.reactive.section16.interfaces.CallBack;
import courses.basics_strong.reactive.section16.interfaces.CallBackPush;
import net.datafaker.Faker;
import net.datafaker.providers.base.Animal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CallBackPushDemo {
    private final Random random = new Random();

    public static void main(String[] args) {
        long mainThreadId = Thread.currentThread().threadId();
        System.out.println("Main Thread: "+mainThreadId);

        Runnable runnable = () -> new CallBackPushDemo().runAsync(getCallBackPush());
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        while(t1.isAlive() || t2.isAlive()) {
            System.out.println("I'm waiting threads completing....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Main Thread ["+mainThreadId+"] completed");
    }

    private static CallBackPush<String> getCallBackPush() {
        return new CallBackPush<>() {
            @Override
            public void pushData(long threadId, String data) {
                System.out.println("<< CalBack [Push Data] called by thread:" + threadId + " with data: " + data);
            }

            @Override
            public void pushCompleted(long threadId) {
                System.out.println("<< CalBack [Push Complete] called by thread:" + threadId);
            }

            @Override
            public void pushError(long threadId, Exception exception) {
                System.err.println("<< CalBack [Push Error] called by thread:" + threadId + " with error: " + exception);
            }
        };
    }

    public void runAsync(CallBackPush<String> callBackPush) {
        long threadId = Thread.currentThread().threadId();
        Animal animal = new Faker().animal();
        System.out.println(">> I'm running on thread: " + threadId);

        try {
            callBackPush.pushData(threadId, "1 of 4 Animal - " + animal.name());
            callBackPush.pushData(threadId, "2 of 4 Animal - " + animal.name());
            callBackPush.pushData(threadId, "3 of 4 Animal - " + animal.name());
            callBackPush.pushData(threadId, "4 of 4 Animal - " + animal.name());
            sleep(random.nextLong(500, 2000));
            callBackPush.pushCompleted(threadId);
        } catch (Exception e) {
            callBackPush.pushError(threadId, new RuntimeException(e) );
            Thread.currentThread().interrupt();
        }
    }

    private void sleep(long millis) throws InterruptedException {
        if(millis>1500) {
            throw new IllegalArgumentException("Value bigger than 1500");
        }
        TimeUnit.MILLISECONDS.sleep(millis);
    }
}
