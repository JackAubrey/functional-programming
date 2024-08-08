package courses.basics_strong.funcprogramming.section10.model;

public class Book {
    private String title;
    private String author;
    private String genre;
    private double price;
    private short rating;

    public Book(String title, String author, String genre, double price, short rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public short getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }
}
