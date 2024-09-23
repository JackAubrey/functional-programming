package courses.basics_strong.generics.section26.subclassing;

public class Model<I, T> extends BasicModel<I> implements IModel<I, T> {
    private final T data;

    public Model(I id, T data) {
        super(id);
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + getId() +
                ", data=" + data +
                '}';
    }
}
