package courses.basics_strong.funcprogramming.section10;

import courses.basics_strong.funcprogramming.section10.model.Book;
import courses.basics_strong.funcprogramming.section10.model.BookBuilder;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public abstract class AbstractStreamBase {
    private static Random random = new Random();

    // this method create random data. sometimes you should run this code in order to see a result
    static List<Book> createBookList(){
        Faker faker = new Faker();
        var bookFaker = faker.book();
        return Stream.generate( () -> toBook(bookFaker))
                .limit(200)
                .toList();
    }

    static Book toBook(net.datafaker.providers.base.Book bookFaker) {
        return new BookBuilder()
                .setAuthor(bookFaker.author())
                .setGenre(bookFaker.genre())
                .setPrice(randomPrice(5, 35))
                .setRating( (short) random.nextInt(1, 5))
                .setTitle(bookFaker.title())
                .createBook();
    }

    static double randomPrice(int min, int max) {
        return new BigDecimal(random.nextDouble(min, max))
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
