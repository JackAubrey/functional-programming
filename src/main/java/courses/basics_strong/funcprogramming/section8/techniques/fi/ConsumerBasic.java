package courses.basics_strong.funcprogramming.section8.techniques.fi;

@FunctionalInterface
public interface ConsumerBasic<T> {
    void accept(T t);
}
