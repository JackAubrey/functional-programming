package courses.basics_strong.funcprogramming.section9.design_patterns;

import java.util.function.Supplier;

public class PatternFactoryMethod {
    public static void main(String[] args) {
        // suppose we have to decide the flooring that we should use in any housing unit
        // and the criteria to decide on that is the climate,
        // or you can say the temperature variation of that particular city or the region.
        // like if you live in a hot climate concrete floors can be a choice because concrete floors
        // are cool compared to most other kinds
        Supplier<Flooring> flooring = FlooringFactory.getFlooring(-1, 18);
        flooring.get().installation();

        Supplier<Flooring> flooring2 = FlooringFactory.getFlooring(13, 46);
        flooring.get().installation();
    }


    class FlooringFactory {
        public static Supplier<Flooring> getFlooring(int minTemp, int maxTemp) {
            // I'm going to take the courses example. Is not important the algorithm but the idea.
            Supplier<Flooring> flooring;

            if(minTemp <= 5 && maxTemp <= 20) {
                flooring = () -> new WoodenFlooring();
            } else if(minTemp <= 5 && maxTemp >= 45) {
                flooring = () -> new CorkFlooring();
            } else {
                flooring = () -> new ConcreteFlooring();
            }

            return flooring;
        }
    }


}

interface Flooring {
    void installation();
}

class WoodenFlooring implements Flooring {

    @Override
    public void installation() {
        System.out.println("The Wooden Flooring is installed!");
    }
}

class ConcreteFlooring implements Flooring {

    @Override
    public void installation() {
        System.out.println("The Concrete Flooring is installed!");
    }
}

class CorkFlooring implements Flooring {

    @Override
    public void installation() {
        System.out.println("The Cork Flooring is installed!");
    }
}
