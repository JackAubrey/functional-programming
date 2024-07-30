package courses.basics_strong.funcprogramming.section9.design_patterns;

import java.util.List;
import java.util.function.Function;

/**
 * This is my own implementation. Is not taken from the course because I'm not agree with their example.
 * Also this example shown the usage of Fluent Interfaces
 */
public class PatternDecorator {
    public static void main(String[] args) {
        Function<Burger, Burger> ingredients = b -> b.withIngredients(List.of("salad", "tomato", "tuna sauce"));
        Function<Burger, Burger> meat = b -> b.withMeatType(Burger.MeatType.Chicken);
        Function<Burger, Burger> bread = b -> b.withBreadType(Burger.BreadType.Soy);

        Burger burger = Burger.prepare(ingredients.andThen(meat).andThen(bread));
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

    /**
     * is private because the building of object is delegate to the "prepare" static method
     */
    private Burger() {
    }

    /**
     * is private because is used by the "withXXXX" methods.
     * All this "withXXXX" methods returns a new instance in order to respect the "no input object state should be changed"
     * @param meatType
     * @param ingredients
     * @param breadType
     */
    private Burger(MeatType meatType, List<String> ingredients, BreadType breadType) {
        this.meatType = meatType;
        this.ingredients = ingredients;
        this.breadType = breadType;
    }

    public Burger withBreadType(BreadType breadType) {
        this.breadType = breadType;
        return new Burger(this.meatType, this.ingredients, this.breadType);
    }

    public Burger withMeatType(MeatType meatType) {
        this.meatType = meatType;
        return new Burger(this.meatType, this.ingredients, this.breadType);
    }

    public Burger withIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return new Burger(this.meatType, this.ingredients, this.breadType);
    }

    public static Burger prepare(Function<Burger, Burger> function) {
        return function.apply(new Burger());
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
