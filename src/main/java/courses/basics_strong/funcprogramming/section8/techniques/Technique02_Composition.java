package courses.basics_strong.funcprogramming.section8.techniques;

import com.ibm.icu.text.RuleBasedNumberFormat;
import courses.basics_strong.funcprogramming.section8.techniques.beans.Square;
import courses.basics_strong.funcprogramming.section8.techniques.fi.IFunction;

import java.math.BigDecimal;
import java.util.Locale;

public class Technique02_Composition {
    public static void main(String[] args) {
        // suppose we have squares and we know the area of squares
        IFunction<Square, Integer> funcGetArea = Square::getArea;
        IFunction<Integer, Double> funcCalcSquare = area -> Math.sqrt(area);

        Double square = funcCalcSquare.compose(funcGetArea).apply(new Square(62));
        System.out.println(square);

        // another example that will show you a bit more complex composition
        IFunction<String, String> fnFinal = (d) -> "[Final Function] \n\t"+d;
        IFunction<String, String> fnSecondFirst = (d) -> "[Second Function Second Executed] \n\t"+d;
        IFunction<String, String> fnSecondSecond = (d) -> "[Second Function First Executed] \n\t"+d;
        IFunction<String, String> fnFirst = (d) -> "[First Execute] \n\t"+d;

        String result = fnFinal.compose(fnSecondSecond.compose(fnSecondFirst)).compose(fnFirst).apply(" - My String");
        System.out.println("Result = "+result);


        RuleBasedNumberFormat rule = new RuleBasedNumberFormat(Locale.ENGLISH, RuleBasedNumberFormat.SPELLOUT);
        IFunction<String, String> fnFirst_StripesVocals = (s) -> s.replaceAll("[aeiou]", "");
        IFunction<String, Integer> fnSecondFirst_StringLength = (s) -> s.length();
        IFunction<Integer, Double> fnSecondSecond_LengthPow = (i) -> Math.pow(i, 2);
        IFunction<Double, String> fnFinal_DoubleToWord = (d) -> rule.format(new BigDecimal(d));

        String numberInWord = fnFinal_DoubleToWord
                .compose(
                        fnSecondSecond_LengthPow
                                .compose(fnSecondFirst_StringLength)
                )
                .compose(fnFirst_StripesVocals)
                .apply("Basic Strong");
        System.out.println(numberInWord);
        /*
         *  fnFirst_StripesVocals: "Basic Strong" -> "Bsc Strng"
         *      fnSecondFirst_StringLength: "Bsc Strng" -> 9
         *          fnSecondSecond_LengthPow: 9 -> 81
         *  fnFinal_DoubleToWord: 81 -> "eighty-one"
         */

    }
}
