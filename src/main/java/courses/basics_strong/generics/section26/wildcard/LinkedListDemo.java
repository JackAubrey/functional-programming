package courses.basics_strong.generics.section26.wildcard;

import courses.basics_strong.reactive.BasicExampleClass;

public class LinkedListDemo extends BasicExampleClass {
    public static void main(String[] args) {
        DataNote<Integer> leaf = new DataNote<>(5, null);
        DataNote<Integer> node3 = new DataNote<>(1, leaf);
        DataNote<Integer> node2 = new DataNote<>(10, node3);
        DataNote<Integer> node1 = new DataNote<>(27, node2);
        DataNote<Integer> root = new DataNote<>(0, node1);

        log(root);

        // you CAN NOT DO this because:
        //     - "node1" DataNote is Integer type
        //     - "rootNumber" DataNote is Number Type
        // you can pass an Integer to a Number generic type because Integer is a subtype of Number.
        // but you can not do this with generic class.
        //
        // Number is parent of Integer but DataNode<Number> is not parent of DataNote<Integer>
        //
        // DataNote<Number> rootNumber = new DataNote<>(0, null)
        // rootNumber.setNext(node1)

        // in order to fix this problem we can use wildcards
        //
        // in this case the nodes can have any kind od generic type
        DataNoteWildCarded<String> node1Integer = new DataNoteWildCarded<>("Hello!!", null);
        DataNoteWildCarded<Integer> node2Integer = new DataNoteWildCarded<>(27, node1Integer);
        DataNoteWildCarded<Number> rootNumber = new DataNoteWildCarded<>(0, null);
        rootNumber.setNext(node2Integer);

        log(rootNumber);

        // but we want limit only to numbers
        DataNoteWildCardedAndRestriction<Double> node1ResDouble = new DataNoteWildCardedAndRestriction<>(23.4, null);
        DataNoteWildCardedAndRestriction<Integer> node2ResInteger = new DataNoteWildCardedAndRestriction<>(270, node1ResDouble);
        DataNoteWildCardedAndRestriction<Number> rootResNumber = new DataNoteWildCardedAndRestriction<>(1234, null);
        rootResNumber.setNext(node2ResInteger);

        log(rootResNumber);
    }
}
