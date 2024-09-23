package courses.basics_strong.generics.section26.upper.and.lower.bounding;

import courses.basics_strong.generics.section26.upper.and.lower.bounding.model.*;
import courses.basics_strong.reactive.BasicExampleClass;

import java.util.List;

public class UpperBoundedTypeDemo extends BasicExampleClass {
    public static void main(String[] args) {
        List<IAnimal> animals = List.of(new Tiger(), new Shark(), new Cow(), new Elephant());
        List<ICarnivorous> carnivorous = List.of(new Tiger(), new Shark());
        List<IHerbivorous> herbivorous = List.of(new Cow(), new Elephant());

        printUpperBoundedAnimalList(animals);
        printUpperBoundedCarnivorousList(carnivorous);
        printUpperBoundedHerbivorousList(herbivorous);
    }

    private static void printUpperBoundedAnimalList(List<? extends IAnimal> list) {
        log("\t## Animals");
        list.forEach(a -> {
            a.move();
            a.eat();
        });
    }

    private static void printUpperBoundedCarnivorousList(List<? extends ICarnivorous> list) {
        log("\t## Carnivorous");
        list.forEach(a -> {
            a.move();
            a.eat();
        });
    }

    private static void printUpperBoundedHerbivorousList(List<? extends IHerbivorous> list) {
        log("\t## Herbivorous");
        list.forEach(a -> {
            a.move();
            a.eat();
        });
    }
}
