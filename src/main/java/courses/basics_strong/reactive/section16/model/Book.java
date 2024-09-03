package courses.basics_strong.reactive.section16.model;

import courses.basics_strong.reactive.section16.interfaces.Observable;
import courses.basics_strong.reactive.section16.interfaces.SubjectLibrary;

import java.util.ArrayList;
import java.util.List;

public class Book implements SubjectLibrary {
    private String name;
    private String type;
    private String author;
    private double price;
    private String inStock;
    private final List<Observable> obsList = new ArrayList<>();

    public Book(String name, String type, String author, double price, String inStock) {
        this.name = name;
        this.type = type;
        this.author = author;
        this.price = price;
        this.inStock = inStock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
        System.out.println("Availability changed from Sold out to Back in stock \n");
        notifyObserver();
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", inStock='" + inStock + '\'' +
                '}';
    }

    @Override
    public void subscribeObservable(Observable observable) {
        obsList.add(observable);
    }

    @Override
    public void unsubscribeObservable(Observable observable) {
        obsList.remove(observable);
    }

    @Override
    public void notifyObserver() {
        final String msg = "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", inStock='" + inStock + ". So, please notify all users.\n";
        System.out.println(msg);

        obsList.forEach( o -> o.update(this.inStock) );
    }
}
