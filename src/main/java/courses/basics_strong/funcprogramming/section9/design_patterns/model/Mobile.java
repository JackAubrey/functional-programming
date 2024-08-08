package courses.basics_strong.funcprogramming.section9.design_patterns.model;

public class Mobile {
    private final int ram;
    private final int storage;
    private final int battery;
    private final String cpu;
    private final double screenSize;

    public Mobile(int ram, int storage, int battery, String cpu, double screenSize) {
        this.ram = ram;
        this.storage = storage;
        this.battery = battery;
        this.cpu = cpu;
        this.screenSize = screenSize;
    }

    public int getRam() {
        return ram;
    }

    public int getStorage() {
        return storage;
    }

    public int getBattery() {
        return battery;
    }

    public String getCpu() {
        return cpu;
    }

    public double getScreenSize() {
        return screenSize;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "ram=" + ram +
                ", storage=" + storage +
                ", battery=" + battery +
                ", cpu='" + cpu + '\'' +
                ", screenSize=" + screenSize +
                '}';
    }
}
