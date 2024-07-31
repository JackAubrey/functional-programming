package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.commander;

public abstract class AbstractCommandAc implements ICommandAc {
    private AirConditioner ac;

    protected AbstractCommandAc(AirConditioner ac) {
        this.ac = ac;
    }

    protected AirConditioner getAc() {
        return ac;
    }
}
