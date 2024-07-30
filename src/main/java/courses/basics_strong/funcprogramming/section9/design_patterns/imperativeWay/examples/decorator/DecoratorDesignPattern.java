package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.decorator;

public class DecoratorDesignPattern {
    public static void main(String[] args) {
        Burger burger = new PlainBurger();
        burger.makeBurger();

        CheeseBurgerDecorator cheeseBurgerDecorator = new CheeseBurgerDecorator(burger);
        cheeseBurgerDecorator.makeBurger();

        new BarbecueBurgerDecorator(cheeseBurgerDecorator).makeBurger();
    }
}
