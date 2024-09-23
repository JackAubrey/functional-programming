package courses.basics_strong.generics.section26.upper.and.lower.bounding.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tiger implements ICarnivorous, IAnimal {
    private static final Logger logger = LoggerFactory.getLogger(Tiger.class);

    @Override
    public String kind() {
        return "Tiger is a Carnivorous";
    }

    @Override
    public void move() {
        logger.info("Tiger is moving");
    }

    @Override
    public void eat() {
        logger.info("Tiger is eating");
    }
}
