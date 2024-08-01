package courses.basics_strong.funcprogramming.section10.data;

public class BookBuilder {
    private String title;
    private String author;
    private String genre;
    private double price;
    private short rating;

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BookBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public BookBuilder setRating(short rating) {
        this.rating = rating;
        return this;
    }

    public Book createBook() {
        return new Book(title, author, genre, price, rating);
    }
}