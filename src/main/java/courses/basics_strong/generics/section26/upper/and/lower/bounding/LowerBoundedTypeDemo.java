package courses.basics_strong.generics.section26.upper.and.lower.bounding;

import courses.basics_strong.generics.section26.upper.and.lower.bounding.model.*;
import courses.basics_strong.reactive.BasicExampleClass;

import java.util.List;

public class LowerBoundedTypeDemo extends BasicExampleClass {
    public static void main(String[] args) {
        List<IAnimal> animals = List.of(new Tiger(), new Shark(), new Cow(), new Elephant());
        List<ICarnivorous> carnivorous = List.of(new Tiger(), new Shark());
        List<IHerbivorous> herbivorous = List.of(new Cow(), new Elephant());

        printLowerBoundedAnimalsList(animals);
        printLowerBoundedCarnivorousList(carnivorous);
        printLowerBoundedHerbivorousList(herbivorous);
    }

    private static void print(IAnimal a) {
        a.move();
        a.eat();
    }

    private static void printLowerBoundedAnimalsList(List<? super IAnimal> list) {
        log("\t## Animals");
        list.forEach(a -> print((IAnimal)a));
    }

    private static void printLowerBoundedCarnivorousList(List<? super ICarnivorous> list) {
        log("\t## Carnivorous");
        list.forEach(a -> print((IAnimal)a));
    }

    private static void printLowerBoundedHerbivorousList(List<? super IHerbivorous> list) {
        log("\t## Herbivorous");
        list.forEach(a -> print((IAnimal)a));
    }
}
