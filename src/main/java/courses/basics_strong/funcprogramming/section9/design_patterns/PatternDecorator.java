package courses.basics_strong.funcprogramming.section9.design_patterns;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This is my own implementation. Is not taken from the course because I'm not agree with their example.
 */
public class PatternDecorator {
    public static void main(String[] args) {
        Function<Burger, Burger> decorator = b -> b;
        Function<Burger, Burger> ingredients = b -> b.setIngredients(List.of("salad", "tomato"));
        Function<Burger, Burger> meat = b -> b.setMeatType(Burger.MeatType.Pork);
        Function<Burger, Burger> bread = b -> b.setBreadType(Burger.BreadType.Wholemeal);

        Burger burger = decorator
                .andThen(ingredients)
                .andThen(meat)
                .andThen(bread)
                .apply(new Burger());

        System.out.println(burger);
    }

}

class Burger {
    enum MeatType {
        Beef, Pork, Chicken, Vegan
    }

    enum BreadType {
        Wheat, Soy, Rye, Wholemeal
    }

    private MeatType meatType = Burger.MeatType.Beef;
    private List<String> ingredients = List.of("salad", "tomato", "mustard", "ketchup", "cheese");
    private BreadType breadType = BreadType.Wheat;

    public Burger() {

    }

    public Burger(MeatType meatType) {
        this.meatType = meatType;
    }

    public Burger setBreadType(BreadType breadType) {
        this.breadType = breadType;
        return this;
    }

    public Burger setMeatType(MeatType meatType) {
        this.meatType = meatType;
        return this;
    }

    public Burger setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    @Override
    public String toString() {
        return "Burger{" +
                "meatType=" + meatType +
                ", ingredients=" + ingredients +
                ", breadType=" + breadType +
                '}';
    }
}
