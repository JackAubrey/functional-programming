package courses.basics_strong.funcprogramming.section9.design_patterns.functionalWay;

import courses.basics_strong.funcprogramming.section9.design_patterns.model.Mobile;

import java.util.function.Consumer;

public class MobileBuilderFunctionalWay {
    public int ram;
    public int storage;
    public int battery;
    public String cpu;
    public double screenSize;

    public MobileBuilderFunctionalWay with(Consumer<MobileBuilderFunctionalWay> buildFields) {
        buildFields.accept(this);
        return this;
    }

    public Mobile build() {
        return new Mobile(ram, storage, battery, cpu, screenSize);
    }
}
