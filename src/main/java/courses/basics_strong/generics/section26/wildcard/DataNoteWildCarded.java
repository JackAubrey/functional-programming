package courses.basics_strong.generics.section26.wildcard;

public class DataNoteWildCarded<E> {
    private E data;
    private DataNoteWildCarded<?> next;

    public DataNoteWildCarded(E data, DataNoteWildCarded<?> next) {
        this.data = data;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public DataNoteWildCarded<?> getNext() {
        return next;
    }

    public void setNext(DataNoteWildCarded<?> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "DataNoteWildCarded{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}
