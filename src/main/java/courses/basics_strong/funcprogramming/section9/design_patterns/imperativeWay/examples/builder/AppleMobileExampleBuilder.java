package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.builder;

public class AppleMobileExampleBuilder extends AbstractMobileExampleBuilder {
    @Override
    public void withRam() {
        getMobile().setRam(16);
    }

    @Override
    public void withStorage() {
        getMobile().setStorage(128);
    }

    @Override
    public void withBattery() {
        getMobile().setBattery("3200 mAh");
    }

    @Override
    public void withCpu() {
        getMobile().setCpu("ARM A15");
    }

    @Override
    public void withWireless() {
        getMobile().setWireless("Apple Mobile Wireless 5Gz");
    }

    @Override
    public void withScreenSize() {
        getMobile().setScreenSize(6.3);
    }
}
