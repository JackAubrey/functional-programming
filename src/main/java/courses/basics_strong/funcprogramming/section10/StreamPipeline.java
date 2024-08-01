package courses.basics_strong.funcprogramming.section10;

import courses.basics_strong.funcprogramming.section10.data.Book;
import courses.basics_strong.funcprogramming.section10.data.BookBuilder;
import net.datafaker.Faker;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPipeline {
    public static void main(String[] args) {
        List<Book> books = createBookList();

        // This is a Stream Pipeline
        List<Book> popularBooks = books.stream() // Source
                .filter( (book) -> book.getGenre().equalsIgnoreCase("Fable")) // Intermediate Operation
                .filter( (book) -> book.getRating() > 3) // Intermediate Operation
                .toList(); // Terminal Operation

        System.out.println("Popular Fable Books = "+popularBooks);

        // Decompose the pipeline

        // 1. Step 1 - we are obtaining a stream from a list
        // This is our Source.
        Stream<Book> booksStream = books.stream();
        System.out.println("The Book Stream Object: "+booksStream);

        // 2. Step 2 - Pipeline Operation 1 - FILTER - Intermediate Operation
        // Given the stream with ALL data we are going to apply the "filter" operation.
        // The "filter" returns a new stream with filtered data.
        Stream<Book> genreStream = booksStream.filter( (book) -> book.getGenre().equalsIgnoreCase("Fable"));
        System.out.println("The Book Stream Object returned by Genre Filter: "+genreStream);

        // 3. Step 3 - Pipeline Operation 2 - FILTER - Intermediate Operation
        // Given the stream with FILTERED BY GENRE data we are going to apply another "filter" operation.
        // The "filter" returns a new stream with filtered data.
        Stream<Book> ratingStream = genreStream.filter( (book) -> book.getRating() > 3);
        System.out.println("The Book Stream Object returned by Rating Filter: "+ratingStream);

        // 4. Step 4 - Pipeline Operation 3 - COLLECT THE RESULT - Terminal Operation
        // Given the stream with FILTERED BY RATING data we are going to collect the final result into another List.
        List<Book> finalResult = ratingStream.toList(); // or .collect(Collectors.toList());
        System.out.println("Final Result: "+finalResult);
    }

    // this method create random data. sometimes you should run this code in order to see a result
    private static List<Book> createBookList(){
        Faker faker = new Faker();
        var bookFaker = faker.book();
        return IntStream.rangeClosed(1, 100)
                .mapToObj( i -> toBook(bookFaker))
                .toList();
    }

    private static Book toBook(net.datafaker.providers.base.Book bookFaker) {
        return new BookBuilder()
                .setAuthor(bookFaker.author())
                .setGenre(bookFaker.genre())
                .setPrice(new Random().nextDouble(5, 35))
                .setRating( (short) new Random().nextInt(1, 5))
                .setTitle(bookFaker.title())
                .createBook();
    }
}
