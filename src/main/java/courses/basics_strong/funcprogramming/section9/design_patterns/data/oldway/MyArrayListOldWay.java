package courses.basics_strong.funcprogramming.section9.design_patterns.data.oldway;

import java.util.Iterator;

public class MyArrayListOldWay implements Iterable<Object> {
    private static final int BUF_SIZE = 10;
    private Object [] elements = new Object [BUF_SIZE];

    public MyArrayListOldWay(Object[] elements) {
        this.elements = elements;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            int pointer = 0;
            @Override
            public boolean hasNext() {
                return pointer < elements.length;
            }

            @Override
            public Object next() {
                return elements[pointer++];
            }
        };
    }
}
