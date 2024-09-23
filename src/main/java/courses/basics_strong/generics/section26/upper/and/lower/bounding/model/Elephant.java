package courses.basics_strong.generics.section26.upper.and.lower.bounding.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Elephant implements IHerbivorous, IAnimal {
    private static final Logger logger = LoggerFactory.getLogger(Elephant.class);

    @Override
    public String kind() {
        return "Elephant is a Herbivorous";
    }

    @Override
    public void move() {
        logger.info("Elephant is moving");
    }

    @Override
    public void eat() {
        logger.info("Elephant is eating");
    }
}
