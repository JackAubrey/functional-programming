package courses.basics_strong.generics.section26.subclassing;

public class BasicModel<I> implements IBasicModel<I> {
    private final I id;

    public BasicModel(I id) {
        this.id = id;
    }

    @Override
    public I getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BasicModel{" +
                "id=" + id +
                '}';
    }
}
