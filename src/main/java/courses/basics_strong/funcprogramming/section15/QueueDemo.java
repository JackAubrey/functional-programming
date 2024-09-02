package courses.basics_strong.funcprogramming.section15;

import courses.basics_strong.funcprogramming.section15.datastructures.QueueFun;

public class QueueDemo {
    public static void main(String[] args) {
        QueueFun<String> queue = QueueFun.queue();

        QueueFun<String> enqueue = queue.enqueue("Hey").enqueue("How are you?").enqueue("Fine?");
        System.out.println("--- ENQUEUE");
        enqueue.forEach(System.out::println);

        QueueFun<String> dequeue = enqueue.dequeue().enqueue("Hello").enqueue("world").dequeue();
        System.out.println("--- DEQUEUE ");
        dequeue.forEach(System.out::println);

        String peek = dequeue.peek();
        System.out.println("Peeked = "+peek);
        System.out.println("Dequeue is empty ? "+dequeue.isEmpty());
        System.out.println("Dequeue size = "+dequeue.size());
        QueueFun<String> dequeueFull = dequeue.dequeue().dequeue().dequeue();
        System.out.println("And Now Dequeue is empty ? "+dequeueFull.isEmpty());
        System.out.println("Dequeue size = "+dequeueFull.size());
    }
}
