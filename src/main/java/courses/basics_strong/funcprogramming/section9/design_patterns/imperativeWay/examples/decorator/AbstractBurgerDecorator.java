package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.decorator;

public abstract class AbstractBurgerDecorator extends PlainBurger {
    protected Burger burger;

    public AbstractBurgerDecorator(Burger burger) {
        this.burger = burger;
    }

    @Override
    public void makeBurger() {
        burger.makeBurger();
    }
}
