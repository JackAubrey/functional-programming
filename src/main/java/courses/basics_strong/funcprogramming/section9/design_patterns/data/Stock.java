package courses.basics_strong.funcprogramming.section9.design_patterns.data;

public class Stock {
    private String symbol;
    private double price;
    private double units;

    public Stock(String symbol, double price, double units) {
        this.symbol = symbol;
        this.price = price;
        this.units = units;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getUnits() {
        return units;
    }

    public void setUnits(double units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", units=" + units +
                '}';
    }
}
