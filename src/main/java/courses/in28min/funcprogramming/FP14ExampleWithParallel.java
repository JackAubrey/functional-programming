package courses.in28min.funcprogramming;

import net.datafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;

public class FP14ExampleWithParallel {
    public static void main(String[] args) {
        // Create a huge complex data
        int LOW_VALUE = 1000000;
        int HIGH_VALUE = 30000000;
        int dataLen = HIGH_VALUE;
        System.out.println("\nInitializing "+dataLen+" Fake Data...");
        List<FakePerson> hugeComplexData = init(dataLen);
        System.out.println(dataLen+" Fake Data generated!");

        // SEQUENTIALLY
        System.out.println("");
        long startSeq = System.currentTimeMillis();
        Double avgAgeSeq = hugeComplexData.stream()
                .collect(Collectors.groupingBy(FakePerson::gender, Collectors.averagingInt(FakePerson::age)))
                .entrySet()
                .stream()
                .mapToDouble(e -> e.getValue())
                .average().orElse(-1);

        System.out.println("Sequential AVG total age: "+avgAgeSeq);
        System.out.println("Performed in "+(System.currentTimeMillis()-startSeq)+" millis");

        // IN PARALLEL
        System.out.println("");
        long startPar = System.currentTimeMillis();
        Double avgAgePar = hugeComplexData.stream().parallel()
                .collect(Collectors.groupingBy(FakePerson::gender, Collectors.averagingInt(FakePerson::age)))
                .entrySet()
                .stream()
                .mapToDouble(e -> e.getValue())
                .average().orElse(-1);

        System.out.println("Parallel AVG total age: "+avgAgePar);
        System.out.println("Performed in "+(System.currentTimeMillis()-startPar)+" millis");
    }

    static List<FakePerson> init(int dataLen) {
        Faker faker = new Faker();

        return faker.collection(
                    () -> new FakePerson(
                            faker.name().firstName(),
                            faker.name().lastName(),
                            faker.gender().binaryTypes(),
                            faker.number().numberBetween(20, 80))
                )
                .len(dataLen)
                .generate();
    }

    record FakePerson(String firstName, String lastName, String gender, int age) {
    }
}
