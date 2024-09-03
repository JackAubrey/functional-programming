package courses.basics_strong.reactive.section16;

import courses.basics_strong.reactive.section16.model.Book;
import courses.basics_strong.reactive.section16.model.EndUser;

public class ObserverDesignPatternDemo {
    public static void main(String[] args) {
        Book book = new Book("Goosebumps", "Horror", "Xyz", 200, "SoldOut");
        EndUser user1 = new EndUser("Bob", book);
        EndUser user2 = new EndUser("Rob", book);

        System.out.println("Current state " + book.getInStock());
        book.setInStock("In Stock");
    }
}
