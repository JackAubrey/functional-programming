package courses.basics_strong.funcprogramming.section9.design_patterns;

import courses.basics_strong.funcprogramming.section9.design_patterns.model.Mobile;
import courses.basics_strong.funcprogramming.section9.design_patterns.functionalWay.MobileBuilderFunctionalWay;
import courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.MobileBuilderImperativeWay;

public class PatternBuilder {
    public static void main(String[] args) {
        Mobile mobile1 = new MobileBuilderImperativeWay()
                .withRam(16)
                .withStorage(64)
                .build();

        Mobile mobile2 = new MobileBuilderFunctionalWay()
                .with( b -> {
                    b.screenSize = 4.1;
                    b.cpu = "AMD";
                }).build();

        System.out.println(mobile1);
        System.out.println(mobile2);
    }
}
