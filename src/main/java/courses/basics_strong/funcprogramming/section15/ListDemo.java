package courses.basics_strong.funcprogramming.section15;

import courses.basics_strong.funcprogramming.section15.datastructures.ListFun;
import courses.basics_strong.funcprogramming.section15.datastructures.ListFunCourse;

import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        // we are going to implement a List (a LinkedList) data structure in a functional way
        ListFun<Integer> list = ListFun.list(3,6,9);
        ListFun<Integer> list2 = ListFun.list(12,15,18);
        ListFunCourse<Integer> listCourse = ListFunCourse.list(3,6,9);
        list.forEach(System.out::println);

        System.out.println("----- Mine");
        ListFun<Integer> newList = list.addOnTop(56).addOnTop(34).addOnBottom(24);
        newList.forEach(System.out::println);

        System.out.println("----- Mine With Add on Pos and Remove and Reverse");
        ListFun<Integer> newListWithAdd = list.addOnTop(56)
                .addOnTop(34)
                .addOnBottom(24)
                .addAtPosition(2, 15)
                .addOnTop(11)
                .addOnBottom(21)
                .addOnBottom(22)
                .remove(15)
                .removeAt(0)
                .remove(123)
                .removeAt(7)
                .reverseList();
        newListWithAdd.forEach(System.out::println);

        System.out.println("----- Concatenate");
        ListFun<Integer> listConc = ListFun.concat(list, list2);
        listConc.forEach(System.out::println);

        System.out.println("----- AddAll on Bottom");
        ListFun<Integer> listAddAllOnBottom = listConc.addAllOnBottom(List.of(21,24,27));
        listAddAllOnBottom.forEach(System.out::println);

        System.out.println("----- AddAll on Top");
        ListFun<Integer> listAddAllOnTop = listConc.addAllOnTop(List.of(21,24,27));
        listAddAllOnTop.forEach(System.out::println);

        System.out.println("----- Clear");
        ListFun<Integer> listClear = listAddAllOnBottom.clear();
        listClear.forEach(System.out::println);

        System.out.println("----- Course");
        ListFunCourse<Integer> newListCourse = listCourse.addEle(56).addEle(34).addEle(5,24).addEle(2, 15);
        newListCourse.forEach(System.out::println);
    }
}
