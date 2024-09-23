package courses.basics_strong.generics.section26.subclassing;

public class ExtendingNonGenericFromGeneric {
    interface NonGenericInterface {
        String getData();
        void setData(String data);
    }

    static class NonGenericClass {
        String data;

        String getData() {
            return data;
        }

        void setData(String data) {
            this.data = data;
        }
    }

    interface GenericInterface<T> extends NonGenericInterface {
        T getId();
    }

    static class GenericClass<T> implements NonGenericInterface {
        private T id;
        private String data;

        public T getId() {
            return id;
        }

        public void setId(T id) {
            this.id = id;
        }

        @Override
        public String getData() {
            return data;
        }

        @Override
        public void setData(String data) {
            this.data = data;
        }
    }

    static class GenericClass2<T> extends NonGenericClass {
        private T id;

        public T getId() {
            return id;
        }

        public void setId(T id) {
            this.id = id;
        }
    }
}
