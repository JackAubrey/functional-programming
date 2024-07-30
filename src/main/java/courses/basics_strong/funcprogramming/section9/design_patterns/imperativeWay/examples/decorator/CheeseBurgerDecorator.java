package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.decorator;

public class CheeseBurgerDecorator extends AbstractBurgerDecorator {
    public CheeseBurgerDecorator(Burger burger) {
        super(burger);
    }

    @Override
    public void makeBurger() {
        burger.makeBurger();
        System.out.println(", cheese added!");
    }
}
