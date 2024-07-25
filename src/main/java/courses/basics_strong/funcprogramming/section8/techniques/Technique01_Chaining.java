package courses.basics_strong.funcprogramming.section8.techniques;

import courses.basics_strong.funcprogramming.section8.techniques.fi.ConsumerWithChain;
import courses.basics_strong.funcprogramming.section8.techniques.fi.ConsumerBasic;
import courses.basics_strong.funcprogramming.section8.techniques.fi.ConsumerWithChainAndFailFast;
import courses.basics_strong.funcprogramming.section8.techniques.fi.MyStrangeFunction;

public class Technique01_Chaining {
    public static void main(String[] args) {
        // these are typical Functional Interface
        ConsumerBasic<String> consBasic1 = (s) -> System.out.println(s);
        ConsumerBasic<String> consBasic2 = (s) -> System.out.println(s);

        // and their typical usage
        consBasic1.accept("C1 says Hello");
        consBasic2.accept("C2 says Hello");

        // now we want that our consumer is able to use both consBasic1 and consBasic2 in a row
        // Ok it works, but it is noa a chaining.
        ConsumerBasic<String> consBasic3 = (s) -> {
            consBasic1.accept(s);
            consBasic2.accept(s);
        };

        consBasic3.accept("C3 says Hello");

        // what we would like to be able to achieve is something like this
        //
        // consChain3 = consChain1.andThenAccept(consChain2);
        //
        // in order to do this we need to introduce a default method in our Functional Interface.
        // open ConsumerWithChain and see the code.
        //
        // and now we are able to perform this
        ConsumerWithChain<String> consChain1 = (s) -> System.out.println(s);
        ConsumerWithChain<String> consChain2 = (s) -> System.out.println(s);
        ConsumerWithChain<String> consChain3 = consChain1.andThen(consChain2)
                .andThen(s -> System.out.println("Last one "+s))
                .andThen(s -> consBasic1.accept("I'm Basic: "+s));
        consChain3.accept("C4 says Hello");

        // >> Fail Fast <<
        //
        // What happens if I wrote this ?
        System.out.println("This ConsumerWithChain fails if I pass a null as \"andThen\" method input parameter ?");
        ConsumerWithChain<String> consChainNotFailFast = consChain1.andThen(null);
        System.out.println("No! An this means our Functional Interface \"ConsumerWithChain\" not failing fast.");

        // Everything is working fine but nothing happens because the "consChain1.andThen(null)" is Lazy.
        // Its body will be executed only when we'll invoke our Functional Interface abstract method (in our case: "accept").
        // Let's try
        try {
            consChainNotFailFast.accept("Never used because we aspect it produce a NullPointerException");
        } catch(NullPointerException e) {
            System.err.println("Yes! Now the Null Pointer Exception is risen. But it's clear that our ConsumerWithChain in not failing fast");
        }

        // we improve our ConsumerWithChain creating a new one named "ConsumerWithChainAndFailFast" that fix it
        //
        // ConsumerWithChainAndFailFast simply add a test if the input parameter is not null
        //
        // try it
        try {
            ConsumerWithChainAndFailFast<String> consChainFailFast1 = (s) -> System.out.println(s);
            ConsumerWithChainAndFailFast<String> consChainFailFastResult = consChainFailFast1.andThen(null);

        } catch(NullPointerException e) {
            System.err.println("Yes! Now the Null Pointer Exception is risen IMMEDIATELY. ConsumerWithChainAndFailFast is fail fast");
        }


        // This is another sample case used to show the chaining of functions that returns something
        MyStrangeFunction<String, String, String, Integer> myFun1 = (s1, s2, s3) -> (s1+s2+s3).length();
        MyStrangeFunction<String, String, Integer, Integer> myFun2 = (s1, s2, i) -> (s1+s2).length()+i;
        MyStrangeFunction<String, String, String, Integer> myFun = myFun1.andThen(myFun2);

        // myFun1 > ( "abc" + "def" + "ghi" ).length = 9
        // myFun2 > ( "abc" + "def" ).length + 9 = 15
        int result = myFun.apply("abc", "def", "ghi");
        System.out.println(result);
    }
}
