package courses.basics_strong.generics.section26.intro;

import courses.basics_strong.reactive.BasicExampleClass;

import java.time.LocalDateTime;

public class DataDemo extends BasicExampleClass {
    public static void main(String[] args) {
        Data<String> s1 = new Data<>("This is my string data");
        Data<Integer> i1 = new Data<>(10);
        IData<LocalDateTime> dt1 = new Data<>(LocalDateTime.now());

        log("String data is: "+s1);
        log("Integer data is: "+i1);
        log("DateTime data is: "+dt1);
    }
}
