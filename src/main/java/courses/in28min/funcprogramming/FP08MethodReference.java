package courses.in28min.funcprogramming;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class FP08MethodReference {
    public static void main(String[] args) {
        List<String> courses = List.of("Spring", "Spring Boot", "API", "Microservices", "AWS", "PCF", "Azure", "Docker", "Kubernetes");

        // Case: Lambda
        courses.stream()
                .map(str -> str.toUpperCase())
                .forEach(str -> System.out.println(str));

        // Case: Method Reference
        courses.stream()
                .map(String::toUpperCase) // <- Method reference works with Object instance
                .forEach(FP08MethodReference::print); // <- and also works with static method

        // Constructor Reference
        Supplier<String> supplier = String::new;

        // Another Constructor Reference
        Function<String, MyClass> operator = MyClass::new;

        courses.stream()
                .map(operator) // we are going to use the function to ma a string with MyClass constructor reference
                .map(MyClass::getValue) // after use the instance method reference to take its value
                .forEach(FP08MethodReference::print); // and then use the static method reference to print the value
    }

    private static void print(String str) {
        System.out.println(str);
    }
}

class MyClass {
    private String value;

    public MyClass(String s) {
        this.value = "Hello: "+s;
    }

    public String getValue(){
        return value;
    }
}
