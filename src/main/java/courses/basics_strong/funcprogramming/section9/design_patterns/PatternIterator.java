package courses.basics_strong.funcprogramming.section9.design_patterns;

import courses.basics_strong.funcprogramming.section9.design_patterns.data.functionalway.MyArrayListFunctionalWay;
import courses.basics_strong.funcprogramming.section9.design_patterns.data.oldway.MyArrayListOldWay;

import java.util.Iterator;

public class PatternIterator {
    public static void main(String[] args) {
        MyArrayListOldWay myArrayListOldWay = new MyArrayListOldWay(new Object[]{"OldWay | Big", "OldWay | Boss", "OldWay | Basic", "OldWay | Strong"});
        Iterator<Object> iterator = myArrayListOldWay.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        // using functional interface we have no need to implements an iterator, simply loop data and pass it to a consumer.
        // in functional programming we won't be using conditional statements and assignments
        MyArrayListFunctionalWay myArrayListFunctionalWay = new MyArrayListFunctionalWay(new Object[]{"Big", "Boss", "Basic", "Strong"});
        myArrayListFunctionalWay.forEach(e -> System.out.println(e));
    }
}
