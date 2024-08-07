package courses.basics_strong.funcprogramming.section11.model;

public class BookModelBuilder {
    private String title;
    private String author;
    private String genre;
    private double price;
    private double rating;

    public BookModelBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookModelBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public BookModelBuilder setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public BookModelBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public BookModelBuilder setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public BookModel createBook() {
        return new BookModel(title, author, genre, price, rating);
    }
}
