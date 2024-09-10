package courses.basics_strong.reactive.section21;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class IOScheduler extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Given of list of intensive I/O Operations
        Observable<? extends IOOperation> source = Observable.just(
                        new FileOperation(), new DatabaseOperation(),
                        new HttCallOperation(), new FtpOperation(),
                        new GrpcOperation())
                // we want to use a Scheduler for "I/O" when will subscribe on this Observable
                .subscribeOn(Schedulers.io());
        int numAvailableProc = Runtime.getRuntime().availableProcessors();
        log("Number of available CPU: "+numAvailableProc);

        // Now we are going to subscribe a certain number of subscribers to our source
        // My current machine has 12 processors available.
        // So we are going to subscribe a number of observers two times greater than our available processors in order to see how the thread will be used.
        // You'll see that thread grows up but since this kind of operation do nothing for a lot of time, this not create any kind of problem to our system.
        for(int i=0; i<numAvailableProc*2; i++) {
            disposables.add( source.subscribe(IOOperation::perform) );
        }

        // wait 10 seconds
        sleep(10, TimeUnit.SECONDS);

        // and dispose all
        log("Ends...");
        disposables.dispose();
    }
}

interface IOOperation {
    void perform();
}

class FileOperation extends BasicExampleClass implements IOOperation {
    @Override
    public void perform() {
        sleep(nextRandomLong(100, 500), TimeUnit.MILLISECONDS);
        log("File | IO Operation done by : "+Thread.currentThread().getName());
    }
}

class DatabaseOperation extends BasicExampleClass implements IOOperation {
    @Override
    public void perform() {
        sleep(nextRandomLong(50, 400), TimeUnit.MILLISECONDS);
        log("Database | IO Operation done by : "+Thread.currentThread().getName());
    }
}

class HttCallOperation extends BasicExampleClass implements IOOperation {
    @Override
    public void perform() {
        sleep(nextRandomLong(300, 1000), TimeUnit.MILLISECONDS);
        log("HttCall | IO Operation done by : "+Thread.currentThread().getName());
    }
}

class FtpOperation extends BasicExampleClass implements IOOperation {
    @Override
    public void perform() {
        sleep(nextRandomLong(500, 1500), TimeUnit.MILLISECONDS);
        log("Ftp | IO Operation done by : "+Thread.currentThread().getName());
    }
}

class GrpcOperation extends BasicExampleClass implements IOOperation {
    @Override
    public void perform() {
        sleep(nextRandomLong(100, 500), TimeUnit.MILLISECONDS);
        log("Grpc | IO Operation done by : "+Thread.currentThread().getName());
    }
}