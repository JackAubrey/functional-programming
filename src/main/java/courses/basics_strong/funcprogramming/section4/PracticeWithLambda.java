package courses.basics_strong.funcprogramming.section4;

public class PracticeWithLambda {
    public static void main(String[] args) {
        // a simple lambda
        //  - with no input parameter
        //  - with no return
        SimpleMethod simpleMethod = () -> System.out.println("Simple method");
        simpleMethod.doSome();

        // two simple lambdas
        //  - with input parameters
        //  - with no return
        SimpleMathOperator simpleSum = (a, b) -> System.out.println("Simple Math SUM operation. "+a+" + "+b+" = "+(a+b));
        SimpleMathOperator simpleMultiply = (a, b) -> System.out.println("Simple Math MULTIPLY operation. "+a+" * "+b+" = "+(a*b));
        simpleSum.operate(5,10);
        simpleMultiply.operate(5,10);

        // a simple lambda
        //  - with no input parameter
        //  - with a return
        SimpleMethodReturnSomething simpleMethodReturnSomething = () -> "Simple method that return something";
        String retValue = simpleMethodReturnSomething.doSome();
        System.out.println(retValue);

        // other two simple lambdas
        //  - with input parameters
        //  - with a return
        MathOperator sum = (a, b) -> a+b;
        MathOperator multiply = (a, b) -> a*b;
        int retSum = sum.operate(5,10);
        int retMultiply = multiply.operate(5,10);
        System.out.println("Math SUM operation result: "+retSum);
        System.out.println("Math MULTIPLY operation result: "+retMultiply);
    }
}

@FunctionalInterface
interface SimpleMethod {
    void doSome();
}

@FunctionalInterface
interface SimpleMathOperator {
    void operate(int a, int b);
}

@FunctionalInterface
interface SimpleMethodReturnSomething {
    String doSome();
}

@FunctionalInterface
interface MathOperator {
    int operate(int a, int b);
}