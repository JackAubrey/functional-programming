package courses.basics_strong.funcprogramming.section15.datastructures;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class ListFunCourse<T> {

    public abstract T head();

    public abstract ListFunCourse<T> tail();

    public abstract boolean isEmpty();

    @SuppressWarnings("rawtypes")
    public static final ListFunCourse NIL = new Nil();

    @Override
    public String toString() {
        return "ListFunCourse{head=" + head() + ", "
                + "tail="+printTail()
                + "}";
    }

    private String printTail() {
        StringBuilder buffer = new StringBuilder();

        if(tail() != null) {
            tail().forEachHead( e -> buffer.append(e).append(", "));
        }

        buffer.append("NIL");

        return buffer.toString();
    }

    private static class Nil<T> extends ListFunCourse<T> {

        private Nil() {
        }

        @Override
        public T head() {
            return null;
        }

        @Override
        public ListFunCourse<T> tail() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    public int length() {
        int l = 0;

        ListFunCourse<T> temp = this;
        while(!temp.equals(NIL)) {
            l++;
            temp = temp.tail();
        }

        return l;
    }

    private static class Const<T> extends ListFunCourse<T> {

        private final T head;
        private final ListFunCourse<T> tail;

        Const(T element, ListFunCourse<T> list) {
            this.head = element;
            this.tail = list;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public ListFunCourse<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return length() == 0 ? true : false;
        }
    }

    public static <T> ListFunCourse<T> list(){
        return NIL ;
    }

    public static <T> ListFunCourse<T> list(T...t){

        ListFunCourse<T> temp = list();

        for(int i = t.length - 1; i >=0 ; i--) {
            temp = new Const<T>(t[i], temp);
        }

        return temp;

    }

    public ListFunCourse<T> addEle(T e){
        return new Const<T>(e, this);
    }

    //method to add element at specific position
    public ListFunCourse<T> addEle(int pos, T ele){
        if(pos < 1 || pos > length())
            throw new IndexOutOfBoundsException();
        if(pos == 1)
            return this.tail().addEle(ele);
        return new Const<T>(head(),tail().addEle(pos-1,ele));
    }

    public void forEach(Consumer<ListFunCourse<T>> action) {
        ListFunCourse<T> temp = this;

        for( int i = 0; i < length(); i++) {
            action.accept(temp);
            temp = temp.tail();
        }
    }

    public void forEachHead(Consumer<? super T> action) {
        T current = this.head();
        ListFunCourse<T> temp = this;

        for( int i = 0; i < length(); i++) {
            action.accept(current);
            temp = temp.tail();
            current = temp.head();
        }
    }


    public ListFunCourse<T> removeEle(T ele){
        if(this.length() == 0)
            return this;
        else if(ele.equals(this.head()))
            return tail();
        else {
            ListFunCourse<T> newTail = tail().removeEle(ele);
            return new Const<T>(head(), newTail);
        }
    }

    //to remove from specific position
    public ListFunCourse<T> removeEle(int pos){
        if(pos < 0 || pos > length())
            throw new IndexOutOfBoundsException();
        if(pos == 0)
            return tail();
        return new Const<T>(head(),tail().removeEle(pos-1));
    }

    public ListFunCourse<T> reverseList() {
        ListFunCourse<T> list = list();
        T current = this.head();
        ListFunCourse<T> temp = this;

        while (!temp.equals(NIL)) {
            list = list.addEle(current);
            temp = temp.tail();
            current = temp.head();
        }
        return list;
    }

    public static <T> ListFunCourse<T> concat(ListFunCourse<T> list1, ListFunCourse<T> list2){
        return list1.isEmpty()
                ? list2
                : new Const<>(list1.head(), concat(list1.tail(), list2));
    }

    public ListFunCourse<T> addAllEle(final Collection<? extends T> list){
        ListFunCourse<T> result = this ;
        for (T t : list) {
            result = result.addEle(t);
        }
        return result;
    }

    public ListFunCourse<T> clear(){
        return list();
    }
}
