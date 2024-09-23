package courses.basics_strong.generics.section26.intro;

public interface IData<T> {
    T getDataStored();
    void setDataStored(T dataStored);
}
