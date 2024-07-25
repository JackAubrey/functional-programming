package courses.basics_strong.funcprogramming.section8.techniques.fi;

@FunctionalInterface
public interface ConsumerWithChain<T> extends ConsumerBasic<T> {

    default ConsumerWithChain<T> andThen(ConsumerWithChain<T> next) {
        return (T t) -> {
            this.accept(t);
            next.accept(t);
        };
    }
}
