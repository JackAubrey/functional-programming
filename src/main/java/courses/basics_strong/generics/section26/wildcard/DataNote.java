package courses.basics_strong.generics.section26.wildcard;

public class DataNote<E> {
    private E data;
    private DataNote<E> next;

    public DataNote(E data, DataNote<E> next) {
        this.data = data;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public DataNote<E> getNext() {
        return next;
    }

    public void setNext(DataNote<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "DataNote{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}
