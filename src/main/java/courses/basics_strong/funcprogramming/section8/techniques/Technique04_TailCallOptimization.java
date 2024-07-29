package courses.basics_strong.funcprogramming.section8.techniques;

public class Technique04_TailCallOptimization {
    public static void main(String[] args) {
        System.out.println(reFact(4));
    }

    // calc factorial
    // here we are using "regular recursion"
    public static long reFact(int n) {
        // base condition to stop execution
        if( n <= 1) {
            return 1;
        } else {
            return n * reFact(n - 1);
        }

        // analyze the execution
        // given 4 as value of first invocation
        //
        //   EXECUTION STEP  |       EVAL               | VALUE RETURNED
        // last call         |   base condition reached |   1
        // third call        |   n = 3, n-1 = 2         |   2 * reFact(1)
        // second call       |   n = 3, n-1 = 2         |   3 * reFact(2)
        // first call        |   n = 4, n-1 = 3         |   4 * reFact(3)
        //
        // when the end of condition is reached
        //
        //                           EVAL               | VALUE RETURNED
        //                       2 * 1                  |   2
        //                       3 * 2                  |   6
        //                       4 * 6                  |   24
    }

    /**
     *
     * @param n is the factorial to calculate
     * @param a is the accumulator to accumulate the product
     * @return
     */
    public static long tailReFact(int n, int a) {
        // base condition to stop execution
        if( n <= 1) {
            return a; // in TCO we need to return the accumulator
        } else {
            return tailReFact(n - 1, n * a);
        }

        // analyze the execution
        // given 4 as value of first invocation
        //
        //   EXECUTION STEP  |       EVAL                 | VALUE RETURNED
        // last call         |   base condition reached   |   [24]
        // third call        |   n=2, a=12, n-1=1, n*a=24 |   [24]
        // second call       |   n=3, a=4,  n-1=2, n*a=12 |   [12]
        // first call        |   n=4, a=1,  n-1=3, n*a=4  |   [4]
        //
        // when the end of condition is reached
        // each call to "tailReFact" method doesn't need to hold anything and wait the result of next call.
    }
}
