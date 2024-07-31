package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.builder;

public class ManufacturerFactory {
    public static final MobileExample buildAndroid() {
        return new AndroidMobileExampleBuilder().build();
    }

    public static final MobileExample buildApple() {
        return new AppleMobileExampleBuilder().build();
    }
}
