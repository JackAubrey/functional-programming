package courses.basics_strong.reactive.section16.interfaces;

/**
 * A Subject is also known as Observable in RXJava.
 */
public interface SubjectLibrary {
    void subscribeObservable(Observable observable);
    void unsubscribeObservable(Observable observable);
    void notifyObserver();
}
