package courses.in28min.funcprogramming;

import com.fasterxml.jackson.databind.ObjectMapper;
import courses.in28min.funcprogramming.data.FakePerson;
import net.datafaker.Faker;

import java.io.FileWriter;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class FP14PerformanceParallelization {
    static final String DATA_FILE_NAME = "FakePersons_data.json";

    public static void main(String[] args) {
        initFile();

        // sequential big operation
        execute( () -> {
            Long sumValue = LongStream.range(0, 1000000000).sum();
            System.out.println("The SEQUENTIAL sum is "+sumValue);
            // The result is: 499999999500000000
            return true;
        });

        // the same big operation with same data but performed in parallel.
        System.out.println("");
        execute( () -> {
            Long sumValue = LongStream.range(0, 1000000000).parallel().sum();
            System.out.println("The PARALLEL sum is "+sumValue);
            // The result is: 499999999500000000
            return true;
        });

        // However! Since java has its optimization's strategy in order to recycle primitive data
        // there are a big difference if you execute the sum of sequential stream as first or last.
        // Try to swap the order of the two executions upon!

        // Create a huge complex data
        int LOW_VALUE = 1000000;
        int HIGH_VALUE = 30000000;
        int dataLen = LOW_VALUE;
        System.out.println("\nInitializing "+dataLen+" Fake Data...");
        List<FakePerson> hugeComplexData = init(dataLen);
        System.out.println(dataLen+" Fake Data generated!");

        // now we are going to:
        //  - grouping by gender and calculate the average data.
        //  - map single grouped item to a double
        //  - calc the total avg
        // SEQUENTIALLY
        System.out.println("");
        execute( () -> {
            Double avgAge = hugeComplexData.stream()
                    .collect(Collectors.groupingBy(FakePerson::gender, Collectors.averagingInt(FakePerson::age)))
                    .entrySet()
                    .stream()
                    .peek(System.out::println)
                    .mapToDouble(e -> e.getValue())
                    .average().orElse(-1);
            // The result is: 499999999500000000
            System.out.println("Sequential AVG total age: "+avgAge);
            return true;
        });

        // now we are going to:
        //  - grouping by gender and calculate the average data.
        //  - map single grouped item to a double
        //  - calc the total avg
        // IN PARALLEL
        System.out.println("");
        execute( () -> {
            Double avgAge = hugeComplexData.stream().parallel()
                    .collect(Collectors.groupingBy(FakePerson::gender, Collectors.averagingInt(FakePerson::age)))
                    .entrySet()
                    .stream()
                    .peek(System.out::println)
                    .mapToDouble(e -> e.getValue())
                    .average().orElse(-1);
            // The result is: 499999999500000000
            System.out.println("Parallel AVG total age: "+avgAge);
            return true;
        });
    }

    static void execute(Supplier<Boolean> supplier) {
        long start = System.currentTimeMillis();
        supplier.get();
        System.out.println("Executed in: " + (System.currentTimeMillis()- start) + " millis");
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

    static void initFile() {
        Faker faker = new Faker();
        ObjectMapper mapper = new ObjectMapper();
        try(FileWriter writer = new FileWriter(DATA_FILE_NAME)) {
            List<FakePerson> list = faker.collection(
                            () -> new FakePerson(
                                    faker.name().firstName(),
                                    faker.name().lastName(),
                                    faker.gender().binaryTypes(),
                                    faker.number().numberBetween(20, 80))
                    )
                    .len(10000)
                    .generate();

            mapper.writerWithDefaultPrettyPrinter().writeValue(writer, list);


            System.out.println("Data Initialization is Done....");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
