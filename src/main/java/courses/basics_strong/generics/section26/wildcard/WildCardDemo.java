package courses.basics_strong.generics.section26.wildcard;

import courses.basics_strong.reactive.BasicExampleClass;

import java.util.List;

public class WildCardDemo extends BasicExampleClass {
    public static void main(String[] args) {
        printData(List.of("Hello", "There", "BasicStrong"));
        printData(List.of(1,2,4,5,6,8));

        printDataUsingWildCard(List.of("Hello", "There", "BasicStrong"));
        printDataUsingWildCard(List.of(1,2,4,5,6,8));
    }

    private static <T> void printData(List<T> list) {
        for (T t:list) {
            BasicExampleClass.log(t);
        }
    }

    private static void printDataUsingWildCard(List<?> list) {
        for (Object t:list) {
            BasicExampleClass.log(t);
        }
    }
}