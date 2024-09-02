package courses.basics_strong.funcprogramming.section15;

import courses.basics_strong.funcprogramming.section15.datastructures.TreeFun;

public class TreeDemo {
    public static void main(String[] args) {
        TreeFun<Integer> t= TreeFun.tree(23, 5, 76, 10, 3, 45);
        TreeFun<Integer> tree = t.remove(10);
        System.out.println(tree);

        System.out.println("10 is member ? " + tree.isMember(10));
        System.out.println("23 is member ? " + tree.isMember(23));
        System.out.println("Max value is: " + tree.max());
        System.out.println("Min value is: " + tree.min());
    }
}
