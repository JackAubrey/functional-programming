package courses.basics_strong.funcprogramming.section10;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamFlatMap {
    public static void main(String[] args) {
        Stream<String> a = Stream.of("Hello ", "There! ");
        Stream<String> b = Stream.of("Learning ", "Java? ");

        Stream<String> c = Stream.of(a, b)
                // e = mapper, -> e = identity function
                //
                // an identity function is a function that takes an element and return the same
                // we can use also the Function "identity" method
                //      .flatMap(Function.identity())
                .flatMap(e -> e);

        System.out.println("Stream FlatMap Result of two Stream");
        c.forEach(System.out::println);

        List<String> l1 = List.of("Hello ", "There! ");
        List<String> l2 = List.of("Learning ", "Java? ");

        Stream<String> s3 = List.of(l1, l2)
                .stream()
                // e = mapper, -> e = identity function
                //
                // an identity function is a function that takes an element and return the same
                .flatMap(l -> l.stream());

        System.out.println("Stream FlatMap Result of two List");
        s3.forEach(System.out::println);

        List<String> l3 = List.of(l1, l2)
                .stream()
                // e = mapper, -> e = identity function
                //
                // an identity function is a function that takes an element and return the same
                .flatMap(l -> l.stream())
                .toList();

        System.out.println("List FlatMap Result of two List");
        l3.forEach(System.out::println);

        final String fileToStream = "text_to_flatmap.txt";
        final File file = getFileFromResources(fileToStream);
        final Function<String, Stream<String>> mapper = s -> Arrays.stream(s.split(" "));

        try ( Stream<String> fileStream = Files.lines(file.toPath()) ) {
            // we use the "mapper" to split a string in a stream of single words
            // and given the flatmap produce a single stream of singular words.
            Map<String, Long> wordFrequency = fileStream.flatMap(mapper)
                    // reduce the string in to lowercase
                    .map(String::toLowerCase)
                    // remove all non letters
                    .map(s -> s.replaceAll("[^a-zA-Z]", ""))
                    // remove all empty string
                    .filter(w -> !w.isEmpty())
                    // collecting any single word grouping with its frequency
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            // print all words sorted by their frequency
            wordFrequency.entrySet()
                    // take the stream of map entries
                    .stream()
                    // sorting by frequency reversing order
                    // and then by alphabetical value in case of some entry has same frequency
                    .sorted( Comparator.<Map.Entry<String, Long>>comparingLong(e -> e.getValue()).reversed().thenComparing(e -> e.getKey()))
                    .forEach(e -> System.out.println("[" + e.getKey() + "] = "+e.getValue()));

            // print all words grouped by their frequency
            wordFrequency.entrySet()
                    // take the stream of map entries
                    .stream()
                    // grouping by frequency and then for alphabetical order
                    .collect( Collectors.groupingBy(e -> e.getValue(), Collectors.mapping(e -> e.getKey(), Collectors.toList())) )
                    // given the obtained sorted map of words frequencies, I take the new entry-set
                    .entrySet()
                    // open the stream on it
                    .stream()
                    // sort the new entry-set by frequency
                    .sorted( Comparator.<Map.Entry<Long, List<String>>>comparingLong(e -> e.getKey()).reversed())
                    .forEach( entry -> System.out.println(entry.getKey() + " = " +entry.getValue()));
        } catch (Exception e) {
            System.err.println("An error occurred during stream file [" + fileToStream + "]"+e.getLocalizedMessage());
        }
    }

    static File getFileFromResources(String fileName) {
        String file = StreamFlatMap.class.getClassLoader()
                .getResource(fileName)
                .getFile();
        return new File(file);
    }
}
