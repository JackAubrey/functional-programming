package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.builder;

public class AndroidMobileExampleBuilder extends AbstractMobileExampleBuilder {
    @Override
    public void withRam() {
        getMobile().setRam(16);
    }

    @Override
    public void withStorage() {
        getMobile().setStorage(256);
    }

    @Override
    public void withBattery() {
        getMobile().setBattery("5200 mAh");
    }

    @Override
    public void withCpu() {
        getMobile().setCpu("SnapDragon 12");
    }

    @Override
    public void withWireless() {
        getMobile().setWireless("Intel Mobile Wireless 5Gz");
    }

    @Override
    public void withScreenSize() {
        getMobile().setScreenSize(8.0);
    }
}
