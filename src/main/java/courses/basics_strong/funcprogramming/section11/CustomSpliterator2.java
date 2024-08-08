package courses.basics_strong.funcprogramming.section11;

import courses.basics_strong.funcprogramming.section11.model.BookModel;
import courses.basics_strong.funcprogramming.section11.model.BookModelBuilder;

import java.util.Scanner;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliterator2 {
    public static void main(String[] args) {
        // suppose we have a datasource that provide needed data not altogether.
        // for example data taken from a queue, aggregated in order to provide a single data
        //
        // in this example we are going to take these data from the console input.
        // we will use the jdk Scanner in order to take the book data

        ScannerSpliterator scannerSpliterator = new ScannerSpliterator();
        Stream<BookModel> streamBooksByScanner = StreamSupport.stream(scannerSpliterator, false);

        System.out.println("Type !q to exit");
        streamBooksByScanner
                .limit(5) // we want to limit to 5 book
                .forEach(System.out::println);
    }
}

class ScannerSpliterator implements Spliterator<BookModel> {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public boolean tryAdvance(Consumer<? super BookModel> action) {
        BookModelBuilder bookBuilder = new BookModelBuilder();

        // Like we did in "CustomSpliterator" example
        // we are going to read data via our "next" method that works similar to the already seen in that example: "spliteratorBase.tryAdvance"
        // this method wants a "fieldName" that will print and a Consumer.
        // the java Scanner will read a line from the console and its value will pass to the consumer
        //
        // unlike we did on "CustomSpliterator" example, we use the "if" statement because we want immediately break the spliterator whe the user write "!q"
        if( next("Title", title -> bookBuilder.setTitle(title)) &&
            next("Author", author -> bookBuilder.setAuthor(author)) &&
            next("Genre", genre -> bookBuilder.setGenre(genre)) &&
            next("Rating", rating -> bookBuilder.setRating(Double.valueOf(rating)) ) ) {
                action.accept(bookBuilder.createBook());
                return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<BookModel> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return Spliterator.ORDERED;
    }

    private boolean next(String field, Consumer<String> action) {
        boolean success = true;

        try {
            System.out.print(field+" = ");
            String nextValue = scanner.nextLine();

            if("!q".equalsIgnoreCase(nextValue)) {
                success = false;
            } else {
                action.accept(nextValue);
            }
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        return success;
    }
}
