package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.builder;

public class MobileExample {
    private int ram;
    private int storage;
    private String battery;
    private String cpu;
    private String wireless;
    private double screenSize;

    public int getRam() {
        return ram;
    }

    void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    void setStorage(int storage) {
        this.storage = storage;
    }

    public String getBattery() {
        return battery;
    }

    void setBattery(String battery) {
        this.battery = battery;
    }

    public String getCpu() {
        return cpu;
    }

    void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getWireless() {
        return wireless;
    }

    void setWireless(String wireless) {
        this.wireless = wireless;
    }

    public double getScreenSize() {
        return screenSize;
    }

    void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    @Override
    public String toString() {
        return "MobileExample{" +
                "ram=" + ram +
                ", storage=" + storage +
                ", battery='" + battery + '\'' +
                ", cpu='" + cpu + '\'' +
                ", wireless='" + wireless + '\'' +
                ", screenSize=" + screenSize +
                '}';
    }
}
