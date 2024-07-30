package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.strategy;

public class StrategyDesignPattern {
    public static void main(String[] args) {
        CompressContext context = new CompressContext();
        context.setStrategy(new CompressStrategyRar());
        context.generateFile("Abc.txt");
    }
}
