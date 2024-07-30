package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.decorator;

public class PlainBurger implements Burger {
    @Override
    public void makeBurger() {
        System.out.println("Plain Burger is ready");
    }
}
