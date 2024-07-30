package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.decorator;

public class BarbecueBurgerDecorator extends AbstractBurgerDecorator {
    public BarbecueBurgerDecorator(Burger burger) {
        super(burger);
    }

    @Override
    public void makeBurger() {
        burger.makeBurger();
        System.out.println(", vegetables and cheese added!");
    }
}
