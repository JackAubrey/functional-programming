package courses.basics_strong.generics.section26.upper.and.lower.bounding.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shark implements ICarnivorous, IAnimal {
    private static final Logger logger = LoggerFactory.getLogger(Shark.class);

    @Override
    public String kind() {
        return "Shark is a Carnivorous";
    }

    @Override
    public void move() {
        logger.info("Shark is moving");
    }

    @Override
    public void eat() {
        logger.info("Shark is eating");
    }
}
