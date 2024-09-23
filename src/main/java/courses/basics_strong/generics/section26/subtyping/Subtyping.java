package courses.basics_strong.generics.section26.subtyping;

import courses.basics_strong.reactive.BasicExampleClass;

public class Subtyping extends BasicExampleClass {
    public static void main(String[] args) {
        Pair<Integer, Integer> single1 = new SingleItem<>(5);
        SingleItem<String> single2 = new SingleItem<>("aaaa");

        log(single1);
        log(single2);
    }
    interface Pair<K, V> {
        K getKey();
        V getValue();

        void setKey(K key);
        void setValue(V value);
    }

    static class SingleItem<E> implements Pair<E, E> {
        private E key;
        private E value;

        public SingleItem(E elem) {
            this.key = elem;
            this.value = elem;
        }

        @Override
        public E getKey() {
            return key;
        }

        @Override
        public E getValue() {
            return value;
        }

        @Override
        public void setKey(E key) {
            this.key = key;
        }

        @Override
        public void setValue(E value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "SingleItem{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
