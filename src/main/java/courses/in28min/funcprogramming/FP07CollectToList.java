package courses.in28min.funcprogramming;

import java.util.List;

public class FP07CollectToList {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(12, 9, 13, 4, 6, 2, 4, 12, 15);

        System.out.println("Calculate square of numbers and collect to other list");
        List<Integer> squaredNumbers = calcSquare(numbers);


        System.out.println("Original | Squared");
        int cnt = 0;
        for(Integer num:numbers) {
            System.out.println(String.format("%-9s|%4s", num, squaredNumbers.get(cnt++)));
        }
    }

    private static List<Integer> calcSquare(List<Integer> numbers) {
        return numbers.stream()
                .map( n -> n*n)
                .toList();

    }

}
