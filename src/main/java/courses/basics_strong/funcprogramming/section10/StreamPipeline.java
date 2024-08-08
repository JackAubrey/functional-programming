package courses.basics_strong.funcprogramming.section10;

import courses.basics_strong.funcprogramming.section10.model.Book;

import java.util.List;
import java.util.stream.Stream;

public class StreamPipeline extends AbstractStreamBase {
    public static void main(String[] args) {
        // NOTE
        // the "createBookList" create random data. Sometimes you should run this code in order to see a result
        List<Book> books = createBookList();
        System.out.println("Generated "+books.size()+" random books");

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
}
