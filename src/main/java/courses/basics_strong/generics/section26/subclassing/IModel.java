package courses.basics_strong.generics.section26.subclassing;

public interface IModel<I, T> extends IBasicModel<I> {
    T getData();
}
