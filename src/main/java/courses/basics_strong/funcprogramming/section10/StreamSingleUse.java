package courses.basics_strong.funcprogramming.section10;

import courses.basics_strong.funcprogramming.section10.data.Book;

import java.util.List;
import java.util.stream.Stream;

public class StreamSingleUse extends AbstractStreamBase{
    public static void main(String[] args) {
        // NOTE
        // the "createBookList" create random data. Sometimes you should run this code in order to see a result
        List<Book> books = createBookList();
        System.out.println("Generated "+books.size()+" random books");

        // A STREAM CAN NOT BE REUSED.
        // Take a look to this example

        // We are going to create a stream from books and perform some operation.
        List<Book> popularFablesBooks = books.stream()
                .filter( (book) -> book.getGenre().equalsIgnoreCase("Fable"))
                .filter( (book) -> book.getRating() >= 3)
                .toList(); // Terminal Operation

        System.out.println("Popular Fables Book");
        popularFablesBooks.forEach(System.out::println);

        // We are going to do the same but using a different filter
        List<Book> popularHorrorBooks = books.stream()
                .filter( (book) -> book.getGenre().equalsIgnoreCase("Horror"))
                .filter( (book) -> book.getRating() >= 3)
                .toList(); // Terminal Operation

        System.out.println("Popular Horror Book");
        popularHorrorBooks.forEach(System.out::println);

        // Now we are authorized to think: ok a can take a reference to  stream and reuse it.
        // Try:
        Stream<Book> stream = books.stream();

        // this wil works because is the first one
        popularFablesBooks = stream.filter( (book) -> book.getGenre().equalsIgnoreCase("Fable"))
                .filter( (book) -> book.getRating() >= 3)
                .toList();

        System.out.println("Popular Fables Book");
        popularFablesBooks.forEach(System.out::println);

        try {
            // this wil won't because the stream is already consumed
            popularHorrorBooks = stream.filter((book) -> book.getGenre().equalsIgnoreCase("Horror"))
                    .filter((book) -> book.getRating() >= 3)
                    .toList();

            System.out.println("Popular Horror Book");
            popularHorrorBooks.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("You can not reuse a stream");
        }
    }
}
