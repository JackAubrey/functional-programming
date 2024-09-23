package courses.basics_strong.generics.section26.upper.and.lower.bounding;

import courses.basics_strong.generics.section26.upper.and.lower.bounding.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Zoo<A extends IAnimal> {
    private static final Logger logger = LoggerFactory.getLogger(Zoo.class);

    private final List<A> animals = new ArrayList<>();

    public static void main(String[] args) {
        Zoo<IAnimal> zoo = new Zoo<>();
        zoo.add(new Tiger());
        zoo.add(new Cow());
        zoo.add(new Elephant());
        zoo.add(new Shark());

        logger.info("Zoo is taking {} animals", zoo.size());
        logger.info("The first one was {}", zoo.getAnimalAt(0));
    }

    public void add(A animal) {
        logger.info("Added animal: {}", animal);
        animals.add(animal);
    }

    public A getAnimalAt(int index) {
        return animals.get(index);
    }

    public int size() {
        return animals.size();
    }
}
