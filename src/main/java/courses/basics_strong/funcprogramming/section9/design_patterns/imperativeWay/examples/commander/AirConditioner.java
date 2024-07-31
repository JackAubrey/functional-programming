package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.commander;

import java.util.UUID;

// this is our receiver
public class AirConditioner {
    private String name = null;

    public AirConditioner () {
        name = UUID.randomUUID().toString();
    }

    public AirConditioner (String name) {
        this.name = name;
    }

    public void turnOn() {
        System.out.println("Turning ON the Air Conditioner ["+name+"]");
    }

    public void turnOff() {
        System.out.println("Turning OFF the Air Conditioner ["+name+"]");
    }

    public void increaseTemperature() {
        System.out.println("Increasing temperature on ["+name+"]");
    }

    public void decreaseTemperature() {
        System.out.println("Decreasing temperature on ["+name+"]");
    }
}
