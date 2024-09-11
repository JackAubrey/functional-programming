package courses.basics_strong.reactive.section21.schedulers;

import courses.basics_strong.reactive.BasicExampleClass;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class ComputationScheduler extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        // Given of list of intensive calculations algorithms
        Observable<? extends Calculation> source = Observable.just(
                new AICalculation(), new MatrixCalculation(),
                new FlowDynamicCalculation(), new RenderingCalculation(),
                new SoundAnalysisCalculation())
                // we want to use a Scheduler for "computation" when will subscribe on this Observable
                .subscribeOn(Schedulers.computation());
        int numAvailableProc = Runtime.getRuntime().availableProcessors();
        log("Number of available CPU: "+numAvailableProc);

        // Now we are going to subscribe a certain number of subscribers to our source
        // My current machine has 12 processors available.
        // So we are going to subscribe a number of observers two times greater than our available processors in order to see how the thread will be used.
        // You'll see how the thread will be reused and no thread greater than available processor will be created
        for(int i=0; i<numAvailableProc*2; i++) {
            disposables.add( source.subscribe(Calculation::compute) );
        }

        // wait 20 seconds
        sleep(20, TimeUnit.SECONDS);

        // and dispose all
        log("Ends...");
        disposables.dispose();
    }
}

interface Calculation {
    void compute();
}

class AICalculation extends BasicExampleClass implements Calculation {

    @Override
    public void compute() {
        sleep(nextRandomLong(1000, 2000), TimeUnit.MILLISECONDS);
        log("AI | Computation done by : "+Thread.currentThread().getName());
    }
}

class MatrixCalculation extends BasicExampleClass implements Calculation {

    @Override
    public void compute() {
        sleep(nextRandomLong(800, 2000), TimeUnit.MILLISECONDS);
        log("Matrix | Computation done by : "+Thread.currentThread().getName());
    }
}

class FlowDynamicCalculation extends BasicExampleClass implements Calculation {

    @Override
    public void compute() {
        sleep(nextRandomLong(1000, 2500), TimeUnit.MILLISECONDS);
        log("FlowDynamic | Computation done by : "+Thread.currentThread().getName());
    }
}

class RenderingCalculation extends BasicExampleClass implements Calculation {

    @Override
    public void compute() {
        sleep(nextRandomLong(1200, 1800), TimeUnit.MILLISECONDS);
        log("Rendering | Computation done by : "+Thread.currentThread().getName());
    }
}

class SoundAnalysisCalculation extends BasicExampleClass implements Calculation {

    @Override
    public void compute() {
        sleep(nextRandomLong(500, 1200), TimeUnit.MILLISECONDS);
        log("SoundAnalysis | Computation done by : "+Thread.currentThread().getName());
    }
}
