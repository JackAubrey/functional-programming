package courses.basics_strong.generics.section26.intro;

public class Data<T> implements IData<T> {
    private T dataStored;

    public Data(T dataStored) {
        this.dataStored = dataStored;
    }

    @Override
    public T getDataStored() {
        return dataStored;
    }

    @Override
    public void setDataStored(T dataStored) {
        this.dataStored = dataStored;
    }

    @Override
    public String toString() {
        return "Data{" +
                "dataStored=" + dataStored +
                '}';
    }
}
