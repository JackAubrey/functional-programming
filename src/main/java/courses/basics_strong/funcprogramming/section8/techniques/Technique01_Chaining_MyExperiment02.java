package courses.basics_strong.funcprogramming.section8.techniques;

import java.util.Objects;

public class Technique01_Chaining_MyExperiment02 {
    public static void main(String[] args) {
        // This is another sample case used to show the chaining of functions that returns something
        MyStrangeFunction<String, String, String, Integer> myFun1 = (s1, s2, s3) -> (s1+s2+s3).length();
        MyStrangeFunction<String, String, Integer, Integer> myFun2 = (s1, s2, i) -> (s1+s2).length()+i;
        MyStrangeFunction<String, String, String, Integer> myFun = myFun1.andThen(myFun2);

        // myFun1 > ( "abc" + "def" + "ghi" ).length = 9
        // myFun2 > ( "abc" + "def" ).length + 9 = 15
        int result = myFun.apply("abc", "def", "ghi");
        System.out.println(result);
    }
}

@FunctionalInterface
interface MyStrangeFunction<T, U, E, R> {
    R apply(T t, U u, E e);

    // <C> is the return of the input function
    // but look: the input function use the return of the previous function as its third
    default <C> MyStrangeFunction<T, U, E, C> andThen(MyStrangeFunction<T, U, R, C> next) {
        Objects.requireNonNull(next);

        return (t, u, e) -> next.apply(t, u, apply(t, u, e)) ;
    }
}
