package courses.in28min.funcprogramming;

import java.util.Random;
import java.util.stream.IntStream;

public class FP17PlayWith_Threads {
    public static void main(String[] args) {
        //runThreadInOldFashionWay();
        //runThreadInOldFashionWayUsingRunnableLambda();
        runThreadStreamsAndLambda();
    }

    static void runThreadInOldFashionWay() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++) {
                    System.out.println(Thread.currentThread().getName()+": "+Thread.currentThread().threadId()+": "+i);
                    try {
                        Thread.sleep(new Random().nextLong(50, 200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        runThread(runnable, "Old Fashion Way Runnable");
        runThread(runnable, "Old Fashion Way Runnable");
        runThread(runnable, "Old Fashion Way Runnable");
    }

    static void runThreadInOldFashionWayUsingRunnableLambda() {
        Runnable runnable = () -> {
            for(int i=0; i<100; i++) {
                System.out.println(Thread.currentThread().getName()+": "+Thread.currentThread().threadId()+": "+i);
                try {
                    Thread.sleep(new Random().nextLong(50, 200));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        runThread(runnable, "Old Fashion Way Using Runnable Lambda");
        runThread(runnable, "Old Fashion Way Using Runnable Lambda");
        runThread(runnable, "Old Fashion Way Using Runnable Lambda");
    }

    static void runThreadStreamsAndLambda() {
        Runnable runnable = () -> {
            IntStream.rangeClosed(0, 100)
                    .forEach(i -> {
                        System.out.println(Thread.currentThread().getName()+": "+Thread.currentThread().threadId()+": "+i);
                        try {
                            Thread.sleep(new Random().nextLong(50, 200));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
        };

        runThread(runnable, "Threads Using Stream and Runnable Lambda");
        runThread(runnable, "Threads Using Stream and Runnable Lambda");
        runThread(runnable, "Threads Using Stream and Runnable Lambda");
    }

    static void runThread(Runnable runnable, String name) {
        Thread thread = new Thread(runnable, name);
        thread.start();
    }
}
