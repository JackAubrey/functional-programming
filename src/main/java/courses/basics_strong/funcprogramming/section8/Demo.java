package courses.basics_strong.funcprogramming.section8;

public class Demo {
    public static void main(String[] args) {
        // why "add" method is a pure function and "multiply" is not?
        //
        // let's look a first usage.
        // here we are adding the result of two multiply
        int result1 = add( multiply(2,3), multiply(3,4) );
        System.out.println(result1);
        // look the console log.
        //
        //      Returning 6 as the result of 2 * 3
        //      Returning 12 as the result of 3 * 4
        //      18


        // now substitution model says: "you can replace any function call with its return value"
        //
        // now if we replace this multiply call with each results
        int result2 = add( 6, 12 );
        System.out.println(result2);
        // the console log will only show:
        //
        //      18


        // so replacing the call to multiply function with its return value changes the significance of the program
        // because the log method will no longer be called and no logging will happen.
        // this might be important or not in case it changes the result of the program
        // so this multiply method is not a pure function.

        // also the "log" method is not a pure function because take something but doesn't return anything

    }

    /**
     * <strong>This is a pure function</strong>
     * @param a
     * @param b
     * @return
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /**
     * This is NOT a pure function
     * @param a
     * @param b
     * @return
     */
    public static int multiply(int a, int b) {
        log( String.format("Returning %s as the result of %s * %s", a*b, a, b) );
        return a * b;
    }

    /**
     * is not a pure function
     * @param m
     */
    public static void log(String m) {
        System.out.println(m);
    }
}
