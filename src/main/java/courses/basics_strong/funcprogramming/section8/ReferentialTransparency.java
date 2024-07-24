package courses.basics_strong.funcprogramming.section8;

public class ReferentialTransparency {

    public static void main(String[] args) {
        int result1 = add(2, multiply(2, 4));
        // we substitute multiply(2, 4) with its result = 8
        int result2 = add(2, 8);
        // we substitute multiply(2, 4) with another multiply in order to take the same result = 8
        int result3 = add(2, multiply(2, multiply(2, 2)));
        // we now invert the order
        int result4 = add(multiply(2, 4), 2);
        // and try another substitution
        int result5 = add(8, 2);

        // the results are all the same.
        // the referential transparency is respected
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);


        // Now if we try to use the multiply2 the simply print a log
        //
        // this will print two logs: "mul(2, 2)" and "mul(2, 4)"
        int resultKo1 = add(2, multiply2(2, multiply2(2, 2)));
        // if substitute multiply2(2, multiply2(2, 2)) with multiply2(2, 4), the log "mul(2, 2)" will never get printed.
        int resultKo3 = add(2, multiply2(2, 4));
        // worst in this case, no logs will never get printed.
        int resultKo2 = add(2, 8);
        // the referential transparency is NOT respected
        // as you can figure, the referential transparency is strictly coupled with "pure function" and "side effects" concepts.

        System.out.println(resultKo1);
        System.out.println(resultKo2);
        System.out.println(resultKo3);
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int multiply2(int a, int b) {
        System.out.println("mul("+a+", "+b+")");
        return a * b;
    }
}
