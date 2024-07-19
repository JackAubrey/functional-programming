package courses.basics_strong.funcprogramming.section3;

import java.util.stream.IntStream;

public class FP03ImperativeVsDeclarative {
    public static void main(String[] args) {
        // we want sum the first even numbers from 1 to 100
        //
        // let's do in an imperative old style manner
        imperativeStyle();
        // an in new way declarative manner
        declarativeStyle();
    }

    private static void imperativeStyle() {
        // familiar for those are used to works with imperative style
        // but look is ugly, verbose
        int sumOfEvens = 0;
        for(int i = 1; i<=100; i++) {
            if(i%2 == 0) {
                sumOfEvens += i;
            }
        }
        System.out.println("Sum Evens using imperative = "+sumOfEvens);
    }

    private static void declarativeStyle() {
        // at the first glance for those that comes from imperative style could be strange to see this
        // but after a bit of training, declarative style will look like more fluent.
        //
        // we are just saying what to do rather than how to do
        // we are using expressions not statements
        //
        // IMPORTANT: In this code, we are not mutating any variable.
        // All the mutation is going on under the hood, but jvm is smart enough to take care of that
        // That's why this code is thread safe and can actually work fine in multi-threaded environments
        int result = IntStream.rangeClosed(1, 100)
                .filter( i -> i%2 == 0)
                .reduce( (x,y) -> x+y)
                .getAsInt();
        System.out.println("Sum Evens using declarative = "+result);
    }
}
