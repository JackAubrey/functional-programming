package courses.basics_strong.funcprogramming.section15.datastructures;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class ListFun<T> {
    public abstract T head();
    public abstract ListFun<T> tail();
    public abstract boolean isEmpty();

    @SuppressWarnings("rawtypes")
    public static final ListFun NIL = new Nil();

    @Override
    public String toString() {
        return "ListFun{head=" + head() + ", "
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

    public int length() {
        int l = 0;
        ListFun<T> temp = this;

        while ( !temp.equals(NIL) ) {
            l++;
            temp = temp.tail();
        }

        return l;
    }

    public static <T> ListFun<T> list() {
        return NIL;
    }

    @SafeVarargs
    public static <T> ListFun<T> list(T... ts) {
        ListFun<T> temp = list();

        for(int i = ts.length - 1; i >= 0; i-- ) {
            temp = new Construct<>(ts[i], temp);
        }

        return temp;
    }

    public ListFun<T> addOnTop(T e) {
        return new Construct<>(e, this);
    }

    public ListFun<T> addOnBottom(T e) {
        return rebuildChain(e, -1, 0, this);
    }

    public ListFun<T> addAtPosition(int pos, T e) {
        int len = length() ;

        if(pos < 0 || pos > len) {
            throw new IndexOutOfBoundsException("Position must to be between 0 and "+len+" - received: "+pos);
        }

        return rebuildChain(e, pos, 0, this);
    }

    private ListFun<T> rebuildChain(T e, int pos, int currIndex, ListFun<T> tail) {
        if ( tail.equals(NIL) ) {
            return new Construct<>(e,tail);
        } else if(currIndex == pos) {
            return new Construct<>(e,tail);
        } else {
            ListFun<T> childTail = tail.tail();
            T childHead = tail.head();
            ListFun<T> tailNew = tail.rebuildChain(e, pos, currIndex+1, childTail);
            return new Construct<>(childHead,tailNew);
        }
    }

    public void forEach(Consumer<ListFun<T>> action) {
        ListFun<T> temp = this;

        for( int i = 0; i < length(); i++) {
            action.accept(temp);
            temp = temp.tail();
        }
    }

    public void forEachHead(Consumer<? super T> action) {
        T current = this.head();
        ListFun<T> temp = this;

        for( int i = 0; i < length(); i++) {
            action.accept(current);
            temp = temp.tail();
            current = temp.head();
        }
    }

    public ListFun<T> remove(T ele){
        if(this.length() == 0)
            return this;
        else if(ele.equals(this.head()))
            return tail();
        else {
            ListFun<T> newTail = tail().remove(ele);
            return new Construct<>(head(), newTail);
        }
    }

    public ListFun<T> removeAt(int pos){
        if(pos < 0 || pos > length())
            throw new IndexOutOfBoundsException();
        if(pos == 0)
            return tail();
        return new Construct<>(head(),tail().removeAt(pos-1));
    }

    public ListFun<T> reverseList() {
        ListFun<T> list = list();
        T current = this.head();
        ListFun<T> temp = this;

        while (!temp.equals(NIL)) {
            list = list.addOnTop(current);
            temp = temp.tail();
            current = temp.head();
        }
        return list;
    }

    public static <T> ListFun<T> concat(ListFun<T> list1, ListFun<T> list2){
        return list1.isEmpty()
                ? list2
                : new Construct<>(list1.head(), concat(list1.tail(), list2));
    }

    public ListFun<T> addAllOnBottom(final Collection<? extends T> list){
        ListFun<T> result = this ;
        for (T t : list) {
            result = result.addOnBottom(t);
        }
        return result;
    }

    public ListFun<T> addAllOnTop(final Collection<? extends T> collection){
        ListFun<T> result = this ;
        List<? extends T> list = collection.stream().toList();
        for(int i = list.size() - 1; i >= 0; i-- ) {
            result = result.addOnTop(list.get(i));
        }
        return result;
    }

    public ListFun<T> clear(){
        return list();
    }

    private static final class Nil<T> extends ListFun<T> {

        @Override
        public T head() {
            return null;
        }

        @Override
        public ListFun<T> tail() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    }

    private static final class Construct<T> extends ListFun<T> {
        private final T head;
        private final ListFun<T> tail;

        private Construct(T head, ListFun<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public ListFun<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return length() == 0;
        }
    }
}
