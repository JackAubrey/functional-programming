package courses.basics_strong.generics.section26.upper.and.lower.bounding.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cow implements IHerbivorous, IAnimal {
    private static final Logger logger = LoggerFactory.getLogger(Cow.class);

    @Override
    public String kind() {
        return "Cow is a Herbivorous";
    }

    @Override
    public void move() {
        logger.info("Cow is moving");
    }

    @Override
    public void eat() {
        logger.info("Cow is eating");
    }
}
