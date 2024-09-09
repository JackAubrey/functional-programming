package courses.basics_strong.reactive.section16;

import courses.basics_strong.reactive.BasicExampleClass;
import courses.basics_strong.reactive.section16.model.Book;
import courses.basics_strong.reactive.section16.model.EndUser;

public class ObserverDesignPatternDemo extends BasicExampleClass {
    public static void main(String[] args) {
        Book book = new Book("Goosebumps", "Horror", "Xyz", 200, "SoldOut");

        new EndUser("Bob", book);
        new EndUser("Rob", book);

        log("Current state " + book.getInStock());
        book.setInStock("In Stock");
    }
}
