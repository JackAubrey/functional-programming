package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.builder;

public abstract class AbstractMobileExampleBuilder {
    private MobileExample mobile;

    MobileExample getMobile() {
        return mobile;
    }

    void setMobile(MobileExample mobile) {
        this.mobile = mobile;
    }

    public abstract void withRam();
    public abstract void withStorage();
    public abstract void withBattery();
    public abstract void withCpu();
    public abstract void withWireless();
    public abstract void withScreenSize();

    public final MobileExample build() {
        MobileExample mobile = new MobileExample();
        setMobile(mobile);
        withRam();
        withStorage();
        withBattery();
        withCpu();
        withWireless();
        withScreenSize();
        return mobile;
    }
}
