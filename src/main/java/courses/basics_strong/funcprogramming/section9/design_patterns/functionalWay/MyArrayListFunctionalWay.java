package courses.basics_strong.funcprogramming.section9.design_patterns.functionalWay;

import java.util.function.Consumer;

public class MyArrayListFunctionalWay {
    private static final int BUF_SIZE = 10;
    private Object [] elements = new Object [BUF_SIZE];

    public MyArrayListFunctionalWay(Object[] elements) {
        this.elements = elements;
    }

    public void forEach(Consumer<Object> action) {
        for(int i=0; i< elements.length; i++) {
            action.accept(elements[i]);
        }
    }
}
