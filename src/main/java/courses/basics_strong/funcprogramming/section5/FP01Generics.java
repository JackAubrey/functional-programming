package courses.basics_strong.funcprogramming.section5;

public class FP01Generics {
    public static void main(String[] args) {
        FunctionalGenericInterface<String, String> fun1 = s -> s.substring(0, 5);
        String substring = fun1.execute("Hello World");
        System.out.println(substring);

        FunctionalGenericInterface<String, Integer> fun2 = s -> s.length();
        Integer length = fun2.execute("Hello World");
        System.out.println(length);
    }

    /**
     * This is Functional Generics Interface
     * It is using java generics
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    interface FunctionalGenericInterface<T, R> {
        R execute(T t);
    }
}
