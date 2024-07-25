package courses.basics_strong.funcprogramming.section8.techniques.fi;

import java.util.Objects;

@FunctionalInterface
public interface ConsumerWithChainAndFailFast<T> extends ConsumerBasic<T> {

    default ConsumerWithChainAndFailFast<T> andThen(ConsumerWithChainAndFailFast<T> next) {
        // before to return our function, we are going to test if the parameter is NotNull
        Objects.requireNonNull(next);

        // we ensured input parameter is not null, and we can return our chaining function.
        return (T t) -> {
            this.accept(t);
            next.accept(t);
        };
    }
}
