package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.commander;

// our invokers
public class CommandFactory {
    public static ICommandAc turnOn(AirConditioner ac) {
        return new AbstractCommandAc(ac) {
            @Override
            public void execute() {
                getAc().turnOn();
            }
        };
    }

    public static ICommandAc turnOff(AirConditioner ac) {
        return new AbstractCommandAc(ac) {
            @Override
            public void execute() {
                getAc().turnOff();
            }
        };
    }

    public static ICommandAc increaseTemperature(AirConditioner ac) {
        return new AbstractCommandAc(ac) {
            @Override
            public void execute() {
                getAc().increaseTemperature();
            }
        };
    }

    public static ICommandAc decreaseTemperature(AirConditioner ac) {
        return new AbstractCommandAc(ac) {
            @Override
            public void execute() {
                getAc().decreaseTemperature();
            }
        };
    }
}
