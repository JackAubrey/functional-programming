package courses.basics_strong.funcprogramming.section11;

import courses.basics_strong.funcprogramming.section11.model.BookModel;
import courses.basics_strong.funcprogramming.section11.model.BookModelBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomSpliterator {
    public static void main(String[] args) {
        // this file contains books data
        // but each data in the book has been  written on a single line
        // This an example of one book
        //
        //      Gulliver's Travels
        //      Jonathan Swift
        //      Fantasy Fiction
        //      4
        //
        // Now we would map it to our BookModel bean and produce a stream of Books
        // In order to do this, we have to create a spliterator that can build a stream of book objects.
        final String fileToStream = "Books.txt";

        // first of all take a reference to the file
        final File file = getFileFromResources(fileToStream);

        // opening a stream to a file reading each line one by one
        try ( Stream<String> fileStream = Files.lines(file.toPath()) ) {
            // now we are going to take the "fileStream" spliterator
            Spliterator<String> spliteratorBase = fileStream.spliterator();

            // in order to use it as base for our custom Spliterator
            Spliterator<BookModel> spliteratorBook = new BookSpliterator(spliteratorBase);

            // now we can use our custom spliterator to works with stream of books
            // In order to do it we need to use the "StreamSupport" class
            //
            // here we are going to use its "stream(Spliterator<T> spliterator, boolean parallel)" method
            Stream<BookModel> streamBooks = StreamSupport.stream(spliteratorBook, false);

            // now we can use the stream.
            streamBooks.forEach(System.out::println);

        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }

    static File getFileFromResources(String fileName) {
        String file = CustomSpliterator.class.getClassLoader()
                .getResource(fileName)
                .getFile();
        return new File(file);
    }
}

class BookSpliterator implements Spliterator<BookModel> {

    private final Spliterator<String> spliteratorBase;

    public BookSpliterator(Spliterator<String> spliteratorBase) {
        this.spliteratorBase = spliteratorBase;
    }

    @Override
    public boolean tryAdvance(Consumer<? super BookModel> action) {
        // there are three things that we need to perform in this method.
        BookModelBuilder bookBuilder = new BookModelBuilder();
        boolean success = true;

        // first
        // invoke the "tryAdvance" method on our spliteratorBase
        // since the book data provide 4 values
        // we need to call this method 4 times
        //
        // second
        // every each "tryAdvance" invocation returns a boolean that represents if it is succeeded
        // so! this means we need to test it 4 times
        // in our example we use the &= operator to do this.
        // we could put all these 4 call in an "if" statement also, using the && operator to test all the results
        success &= spliteratorBase.tryAdvance(title -> bookBuilder.setTitle(title));
        success &= spliteratorBase.tryAdvance(author -> bookBuilder.setAuthor(author));
        success &= spliteratorBase.tryAdvance(genre -> bookBuilder.setGenre(genre));
        success &= spliteratorBase.tryAdvance(rating -> bookBuilder.setRating(Double.valueOf(rating)));

        // third
        // if we have a success, we have to invoke the "accept" method to the provided Consumer
        if(success) {
            action.accept(bookBuilder.createBook());
        }

        // fourth
        // return if success or not
        return success;
    }

    @Override
    public Spliterator<BookModel> trySplit() {
        // If we don't need to provide a Spliterator in parallel. So we simply leave null as return.
        return null;
    }

    @Override
    public long estimateSize() {
        // our Book.txt file contains 24 lines
        // this means invoking "spliteratorBase.estimateSize()" we should obtains 24
        long baseEstimated = spliteratorBase.estimateSize();
        System.out.println("Estimated Base Spliterator size = "+baseEstimated);
        // each book is represented by 4 data
        // this means that our estimated stream of Book should be composed by 24/4 = 6 books
        long estimatedSize = baseEstimated / 4;
        System.out.println("Estimated Stream<Book> size = "+estimatedSize);
        return estimatedSize;
    }

    @Override
    public int characteristics() {
        // we have no particular characteristics to returns
        // so simply returns our spliteratorBase characteristics
        return spliteratorBase.characteristics();
    }
}
