package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.builder;

public class BuilderDesignPattern {
    public static void main(String[] args) {
        MobileExample mobileAndroid = ManufacturerFactory.buildAndroid();
        MobileExample mobileApple = ManufacturerFactory.buildApple();

        System.out.println(mobileAndroid);
        System.out.println(mobileApple);
    }
}
