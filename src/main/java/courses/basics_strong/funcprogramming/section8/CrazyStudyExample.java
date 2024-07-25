package courses.basics_strong.funcprogramming.section8;

import java.util.function.BiFunction;

public class CrazyStudyExample {
    public static void main(String[] args) {
        BiFunction<String, String, String> testMe = CrazyStudyExample.shuffle('*', "None Value", true);
        String r1 = testMe.apply("Java Functional Programming", "Basic Strong");
        String r2 = testMe.apply(null, "Basic Strong");
        String r3 = testMe.apply(null, " ");
        String r4 = testMe.apply("Java Functional Programming", " ");
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
        System.out.println(r4);
    }

    static BiFunction<String, String, String> shuffle(char padding, String defValue, boolean beforeLeft) {
        return (s1, s2) -> {
            String m1 = (s1 == null || s1.trim().isEmpty()) ? new String(defValue) : new String(s1);
            String m2 = (s2 == null || s2.trim().isEmpty()) ? new String(defValue) : new String(s2);
            StringBuffer result = new StringBuffer();


            if(m1.length() < m2.length()) {
                m1 += String.format("%"+(m2.length()-m1.length())+"s", "").replace(' ' , padding);
            } else if(m1.length() > m2.length()) {
                m2 += String.format("%"+(m1.length()-m2.length())+"s", "").replace(' ' , padding);
            }

            for(int i=0; i<m1.length(); i++) {
                if(beforeLeft) {
                    result.append(m1.charAt(i));
                    result.append(m2.charAt(i));
                } else {
                    result.append(m2.charAt(i));
                    result.append(m1.charAt(i));
                }
            }

            return result.toString();
        };
    }
}
