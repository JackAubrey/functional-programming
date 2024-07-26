package courses.basics_strong.funcprogramming.section8.techniques.fi;

@FunctionalInterface
public interface IFunction<T, R> {
    R apply(T t);

    // This function has been taught to study the Technique Composition
    //
    // Given this snippet taken from that code:
    //
    //      IFunction<Square, Integer> funcGetArea = Square::getArea;
    //      IFunction<Integer, Double> funcCalcSquare = area -> Math.sqrt(area);
    //
    // Understand how to set up the Generics.
    // Start from this example without the Generics:
    //
    //      default IFunction compose(IFunction before)
    //
    // Which function will be invoked firs?
    // Which kind of types must have the returned IFunction?
    // Which kind of types must have the input IFunction?
    // Taking a look to the body of function returned by the "compose" method we see:
    //
    //          this.apply(before.apply(???))
    //
    // The "before" IFunction is?
    // And "this" IFunction is?
    // Take a look to the usage:
    //
    //          funcCalcSquare.compose(funcGetArea).apply(new Square(62))
    //
    // "before" is "<Square, Integer> funcGetArea"
    //      - need a Square as input
    //      - and return an Integer
    //
    // "this" is "<Integer, Double> funcCalcSquare"
    //
    // But we are starting from an instance of "funcCalcSquare" that will be composed with "funcGetArea"
    //  - "funcCalcSquare" is the which one from starting the composition and the LAST ONE to be executed
    //  - "funcGetArea" is the function used to compose with and WILL BE EXECUTED AS FIRST.
    //
    // This means:
    //      - the input of the first function "<Integer, Double>  funcCalcSquare" must be the output of the function called "before".
    //      - its output must be the final output
    // The input type of the root composition function "funcCalcSquare" is IFunction<T = Integer, R = Double>
    // In other words:
    //      - the returning function must return the same type of the root function used to compose
    //          - root is <T = Integer, R =Double>  funcCalcSquare
    //          - the returning function must returns R
    //      - the input function since will be executed before of the root composition function, must use as return type the given type of root
    //          - root is <T = Integer, R =Double>  funcCalcSquare
    //            it input type will be used as return type for the function "before"
    //
    //      default IFunction<?, R> compose(IFunction<?, T> before)
    //
    // Since we have no idea about the rest of type, we will introduce a new one.
    // and this is the final result
    default <U> IFunction<U, R> compose(IFunction<U, T> before) {
        return (U u) -> apply(before.apply(u));
    }
}
