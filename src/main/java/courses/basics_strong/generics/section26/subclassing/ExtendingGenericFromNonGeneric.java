package courses.basics_strong.generics.section26.subclassing;

public class ExtendingGenericFromNonGeneric {
    interface GenericInterface<T> {
        T getData();
        void setData(T data);
    }

    static class GenericClass<T> {
        T data;

        T getData() {
            return data;
        }

        void setData(T data) {
            this.data = data;
        }
    }

    interface NonGenericInterface extends GenericInterface<Integer> {
    }

    static class NonGenericClass implements GenericInterface<String> {
        private String data;

        @Override
        public String getData() {
            return data;
        }

        @Override
        public void setData(String data) {
            this.data = data;
        }
    }

    static class NonGenericClass2 extends GenericClass<NonGenericInterface> {
    }
}
