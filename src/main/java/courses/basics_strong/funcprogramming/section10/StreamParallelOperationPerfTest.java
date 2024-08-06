package courses.basics_strong.funcprogramming.section10;

import courses.basics_strong.funcprogramming.section10.data.DummyPerson;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class StreamParallelOperationPerfTest {
    public static void main(String[] args) {
        List<Integer> testDataLengths = List.of(1000, 5000, 10000, 50000, 100000, 500000, 1000000, 5000000);
        List<TestType> tests = new ArrayList<>(testDataLengths.size() * 10);
        System.out.println("Will get executed test with these data length: "+testDataLengths);

        for(int dataLen:testDataLengths) {
            System.out.println("\nInitializing "+dataLen+" Dummy Person Data...");
            List<DummyPerson> hugeComplexData = init(dataLen);
            System.out.println(dataLen+" Dummy Person Data generated!");

            testFilter(dataLen, hugeComplexData, tests);
            testMap(dataLen, hugeComplexData, tests);
            testGrouping(dataLen, hugeComplexData, tests);
        }

        tests.stream()
                .collect(Collectors.groupingBy(e -> e.name, Collectors.groupingBy(e -> e.dataLen, Collectors.groupingBy(e -> e.sequential))))
                .entrySet()
                .stream()
                .forEach( l1 -> {
                    System.out.println("Operation: "+l1.getKey());
                    l1.getValue()
                            .entrySet()
                            .stream()
                            .sorted( (a,b) -> a.getKey().compareTo(b.getKey()))
                            .forEach( l2 -> {
                                System.out.println("\tData Length: "+l2.getKey());
                                l2.getValue()
                                        .entrySet()
                                        .stream()
                                        .forEach( l3 -> {
                                            System.out.println("\t\t" + (l3.getKey() ? "Sequential" : "Parallel") );
                                            l3.getValue().stream()
                                                    .map(t -> t.result)
                                                    .forEach( r -> System.out.println("\t\t\tResult = "+r));
                                        });
                            });
                });
    }

    private static void testFilter(int dataLen, List<DummyPerson> hugeComplexData, List<TestType> tests) {
        TestResult<Long> resSeq = execute( () -> {
             return hugeComplexData.stream()
                    .filter( p -> p.age() >= 25 && p.age() <= 50)
                    .count();
        });
        tests.add(new TestType("FILTER", true, dataLen, resSeq));

        TestResult<Long> resPar = execute( () -> {
            return hugeComplexData.stream().parallel()
                    .filter( p -> p.age() >= 25 && p.age() <= 50)
                    .count();
        });
        tests.add(new TestType("FILTER", false, dataLen, resPar));
    }

    private static void testMap(int dataLen, List<DummyPerson> hugeComplexData, List<TestType> tests) {
        TestResult<Long> resSeq = execute( () -> {
            return hugeComplexData.stream()
                    .map( p -> p.toString())
                    .count();
        });
        tests.add(new TestType("MAP", true, dataLen, resSeq));

        TestResult<Long> resPar = execute( () -> {
            return hugeComplexData.stream().parallel()
                    .map( p -> p.age() + "")
                    .count();
        });
        tests.add(new TestType("MAP", false, dataLen, resPar));
    }

    private static void testGrouping(int dataLen, List<DummyPerson> hugeComplexData, List<TestType> tests) {
        TestResult<Long> resSeq = execute( () -> {
            return (long) hugeComplexData.stream()
                    .collect(Collectors.groupingBy(DummyPerson::gender, Collectors.averagingInt(DummyPerson::age)))
                    .size();
        });
        tests.add(new TestType("GROUPING", true, dataLen, resSeq));

        TestResult<Long> resPar = execute( () -> {
            return (long) hugeComplexData.stream().parallel()
                    .collect(Collectors.groupingBy(DummyPerson::gender, Collectors.averagingInt(DummyPerson::age)))
                    .size();
        });
        tests.add(new TestType("GROUPING", false, dataLen, resPar));
    }

    static <T> TestResult<T> execute(Supplier<T> supplier) {
        long start = System.currentTimeMillis();
        T t = supplier.get();
        return new TestResult<>(t, System.currentTimeMillis()- start);
    }

    static List<DummyPerson> init(int dataLen) {
        Faker faker = new Faker();

        return faker.collection(
                        () -> new DummyPerson(
                                faker.name().firstName(),
                                faker.name().lastName(),
                                faker.gender().binaryTypes(),
                                faker.number().numberBetween(20, 80))
                )
                .len(dataLen)
                .generate();
    }

    record TestResult<T>(T result, long timeElapsed) {}

    record TestType(String name, boolean sequential, int dataLen, TestResult<?> result) {}
}