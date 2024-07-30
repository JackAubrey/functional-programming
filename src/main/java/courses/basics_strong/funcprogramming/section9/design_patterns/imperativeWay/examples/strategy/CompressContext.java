package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.strategy;

public class CompressContext {
    private CompressStrategy strategy;

    public void setStrategy(CompressStrategy strategy) {
        this.strategy = strategy;
    }

    public void generateFile(String filename) {
        this.strategy.compress(filename);
    }
}
