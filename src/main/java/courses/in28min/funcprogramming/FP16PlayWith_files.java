package courses.in28min.funcprogramming;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import courses.in28min.funcprogramming.data.FakePerson;
import courses.in28min.funcprogramming.data.FakePersonAgg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FP16PlayWith_files {
    static final String FAKE_DATA = "FakePersons_data.json";
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<FakePerson> persons = mapper.readValue(Paths.get(FAKE_DATA).toFile(), new TypeReference<List<FakePerson>>() {});

            long count = persons.stream().count();
            System.out.println("Num items: "+count);

            // a simple collect in order to group by gender and aggregate them for average age.
            persons.stream()
                    .collect(Collectors.groupingBy(FakePerson::gender, Collectors.counting()))
                    .entrySet()
                    .forEach(e -> System.out.println("Num of ["+e.getKey()+"] = ["+e.getValue()+"]"));

            // an advanced collect in order to group by gender and aggregate them for:
            //      - average age
            //      - min age
            //      - max age
            //      - num of person
            persons.stream()
                    .collect(Collectors.groupingBy(FakePerson::gender))
                    .values().stream()
                    .map(p -> {
                        double avgAge = p.stream().mapToInt(FakePerson::age).average().orElse(-1);
                        int minAge = p.stream().mapToInt(FakePerson::age).min().orElse(-1);
                        int maxAge = p.stream().mapToInt(FakePerson::age).max().orElse(-1);
                        long genderNum = p.stream().map(FakePerson::gender).count();
                        String gender = p.get(0).gender();

                        return new FakePersonAgg(gender, genderNum, minAge, maxAge, avgAge);
                    })
                    .forEach(System.out::println);

            // Now take a look to course lesson
            //
            // We are going to use Files class to read a simple text file line by line as a stream
            //      - we want to find unique words ignoring case in this file
            //      - group by occurrences
            //      - sort by occurrence desc and word asc
            Files.lines(Paths.get("lesson_data.txt"))
                    // first of all we split the line into array of words
                    .map(l -> l.split(" "))
                    // convert this list of arrays to list of string
                    .flatMap(Arrays::stream)
                    // collect grouping by word ignore case and count num occurrences
                    .collect(Collectors.groupingBy(s -> s.toLowerCase(), Collectors.counting()))
                    // going to iterate the map obtained by grouping
                    .entrySet().stream()
                    // sort the entries
                    .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(e -> e.getValue()).reversed().thenComparing(e -> e.getKey()))
                    .forEach(System.out::println);

            // We can also stream for example the directories
            Files.list(Paths.get("."))
                    .filter(Files::isDirectory)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
