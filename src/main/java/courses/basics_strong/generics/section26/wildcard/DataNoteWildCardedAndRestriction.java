package courses.basics_strong.generics.section26.wildcard;

public class DataNoteWildCardedAndRestriction<E> {
    private E data;
    private DataNoteWildCardedAndRestriction<? extends Number> next;

    public DataNoteWildCardedAndRestriction(E data, DataNoteWildCardedAndRestriction<? extends Number> next) {
        this.data = data;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public DataNoteWildCardedAndRestriction<? extends Number> getNext() {
        return next;
    }

    public void setNext(DataNoteWildCardedAndRestriction<? extends Number> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "DataNoteWildCardedAndRestriction{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}
