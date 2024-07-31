package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay;

import courses.basics_strong.funcprogramming.section9.design_patterns.data.Mobile;

public class MobileBuilderImperativeWay {
    private int ram;
    private int storage;
    private int battery;
    private String cpu;
    private double screenSize;

    public Mobile build() {
        return new Mobile(ram, storage, battery, cpu, screenSize);
    }

    public MobileBuilderImperativeWay withScreenSize(double screenSize) {
        this.screenSize = screenSize;
        return this;
    }

    public MobileBuilderImperativeWay withCpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public MobileBuilderImperativeWay withBattery(int battery) {
        this.battery = battery;
        return this;
    }

    public MobileBuilderImperativeWay withStorage(int storage) {
        this.storage = storage;
        return this;
    }

    public MobileBuilderImperativeWay withRam(int ram) {
        this.ram = ram;
        return this;
    }
}
