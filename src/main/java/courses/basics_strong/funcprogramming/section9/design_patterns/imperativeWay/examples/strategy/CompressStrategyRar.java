package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.strategy;

public class CompressStrategyRar implements CompressStrategy {
    @Override
    public void compress(String filename) {
        System.out.println("File "+filename+" has been successfully converted to RAR");
    }
}
