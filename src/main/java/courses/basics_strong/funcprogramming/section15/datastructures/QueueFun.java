package courses.basics_strong.funcprogramming.section15.datastructures;

import java.util.function.Consumer;

public final class QueueFun<T> {
    private ListFun<T> front;
    private ListFun<T> rear;

    private QueueFun() {
        this.front = ListFun.list();
        this.rear = ListFun.list();
    }

    // Empty Queue
    public static <T> QueueFun<T> queue() {
        return new QueueFun<>();
    }

    private QueueFun(QueueFun<T> queue, T element) {
        final boolean frontIsEmpty = queue.front.isEmpty();
        this.front = frontIsEmpty ? queue.front.addOnTop(element) : queue.front;
        this.rear = frontIsEmpty ? queue.rear : queue.rear.addOnTop(element);
    }

    // enqueue
    public QueueFun<T> enqueue(T t) {
        return new QueueFun<>(this, t);
    }

    // dequeue
    private QueueFun(ListFun<T> front, ListFun<T> rear) {
        final boolean frontIsEmpty = front.isEmpty();
        this.front = frontIsEmpty ? rear.reverseList() : front;
        this.rear = frontIsEmpty ? front : rear;
    }

    public QueueFun<T> dequeue() {
        return new QueueFun<>(this.front.tail(), rear);
    }

    public void forEach(Consumer<? super T> action) {
        T current = this.front.head();
        ListFun<T> temp = ListFun.concat(this.front.tail(), this.rear.reverseList());

        while (temp != null) {
            action.accept(current);
            current = temp.head();
            temp = temp.tail();
        }
    }

    public int size() {
        return front.length() + rear.length();
    }

    public T peek() {
        if(this.size() == 0)
            return null;
        return this.front.head();
    }

    public boolean isEmpty() {
        return this.front.isEmpty() && this.rear.isEmpty();
    }
}
