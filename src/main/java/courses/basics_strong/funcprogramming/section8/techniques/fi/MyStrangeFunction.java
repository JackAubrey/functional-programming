package courses.basics_strong.funcprogramming.section8.techniques.fi;

import java.util.Objects;

@FunctionalInterface
public interface MyStrangeFunction<T, U, E, R> {
    R apply(T t, U u, E e);

    default <C> MyStrangeFunction<T, U, E, C> andThen(MyStrangeFunction<T, U, R, C> next) {
        Objects.requireNonNull(next);

        return (t, u, e) -> {
          return next.apply(t, u, apply(t, u, e)) ;
        };
    }
}
