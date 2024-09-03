package courses.basics_strong.reactive.section16.interfaces;

public interface CallBackPush<T> {
    void pushData(long threadId, T data);
    void pushCompleted(long threadId);
    void pushError(long threadId, Exception exception);
}
