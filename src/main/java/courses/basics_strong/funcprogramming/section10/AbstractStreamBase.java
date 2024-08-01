package courses.basics_strong.funcprogramming.section10;

import courses.basics_strong.funcprogramming.section10.data.Book;
import courses.basics_strong.funcprogramming.section10.data.BookBuilder;
import net.datafaker.Faker;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public abstract class AbstractStreamBase {

    // this method create random data. sometimes you should run this code in order to see a result
    static List<Book> createBookList(){
        Faker faker = new Faker();
        var bookFaker = faker.book();
        return IntStream.rangeClosed(1, 100)
                .mapToObj( i -> toBook(bookFaker))
                .toList();
    }

    static Book toBook(net.datafaker.providers.base.Book bookFaker) {
        return new BookBuilder()
                .setAuthor(bookFaker.author())
                .setGenre(bookFaker.genre())
                .setPrice(new Random().nextDouble(5, 35))
                .setRating( (short) new Random().nextInt(1, 5))
                .setTitle(bookFaker.title())
                .createBook();
    }
}
