package courses.basics_strong.funcprogramming.section11;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

public class FirstLookCharacteristics {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(7);
        list.add(9);
        list.add(0);
        list.add(1);

        Stream<Integer> stream = list.stream();
        Spliterator<Integer> spliterator = stream.spliterator();
        int bits = spliterator.characteristics();

        // bitCount give us the number of bit set
        System.out.println(Integer.bitCount(bits));

        // Spliterator.ORDERED = 0x00000010

        // OR
        // Using the OR bitwise operator we can check if a bit already set.
        // IF the characteristic is already present, it not changes the bit-count
        System.out.println("OR-BITWISE | Is ORDERED already included: " + Integer.bitCount(bits | Spliterator.ORDERED) + " expected: " + bits );
        System.out.println("OR-BITWISE | Is IMMUTABLE already included: " + Integer.bitCount(bits | Spliterator.IMMUTABLE) + " expected: " + bits );

        // AND
        // Using the AND bitwise operator we can check if a bit already set.
        // IF the characteristic is already present, it returns 1 otherwise 0
        System.out.println("AND-BITWISE | Is ORDERED already included: " + Integer.bitCount(bits & Spliterator.ORDERED) + " expected: 1 " );
        System.out.println("AND-BITWISE | Is IMMUTABLE already included: " + Integer.bitCount(bits & Spliterator.IMMUTABLE)  + " expected: 1 " );

        // We can also go straight and use the "hasCharacteristics" provided by the "Spliterator"
        System.out.println("HasCharacteristics | Is ORDERED already included: " + spliterator.hasCharacteristics(Spliterator.ORDERED));
        System.out.println("HasCharacteristics | Is IMMUTABLE already included: " + spliterator.hasCharacteristics(Spliterator.IMMUTABLE));

        long count = list.stream().filter(x -> {
            System.out.println("mapping");
            return x == x * 2;
        }).count();

        System.out.println(count);


//		//e.g
//		Stream.of(1,3,2,4,9)//ORDERED, SIZED
//		.filter(i->i%2==0) // ORDERED
//		.sorted() // ORDERED, SORTED
//		.map(i->i+1) // ORDERED
//		.unordered(); //
    }
}
