package courses.basics_strong.funcprogramming.section9.design_patterns;

import courses.basics_strong.funcprogramming.section9.design_patterns.data.Stock;
import courses.basics_strong.funcprogramming.section9.design_patterns.functionalWay.StockFiltersFunctionalWay;
import courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.StockFiltersImperativeWay;

import java.util.ArrayList;
import java.util.List;

public class PatternStrategy {
    public static void main(String[] args) {
        // Suppose we have history of purchased stocks by a person which include the stock symbol.
        // And what we're gonna do with this data, we are going to filter the data on various criteria
        List<Stock> stockList = new ArrayList<>();

        stockList.add(new Stock("AAPL", 318.65, 10));
        stockList.add(new Stock("MSFT", 166.86, 45));
        stockList.add(new Stock("Google", 99, 12.5));
        stockList.add(new Stock("AMZ", 1866.74, 45));
        stockList.add(new Stock("GOOGL", 1480.20, 3.5));
        stockList.add(new Stock("AAPL", 318.65, 8));
        stockList.add(new Stock("AMZ", 1866.74, 9));

        System.out.println("ImperativeWay | Filtered by SYMBOL");
        StockFiltersImperativeWay.bySymbol(stockList, "AMZ").forEach(System.out::println);
        System.out.println("ImperativeWay | Filtered by PRICE range");
        StockFiltersImperativeWay.byPrice(stockList, 1400.0, 2000.0).forEach(System.out::println);

        System.out.println("FunctionalWay | Filtered by SYMBOL");
        StockFiltersFunctionalWay.filter(stockList, (stock) -> stock.getSymbol().equalsIgnoreCase("AMZ")).forEach(System.out::println);
        System.out.println("FunctionalWay | Filtered by PRICE range");
        StockFiltersFunctionalWay.filter(stockList, (stock) -> stock.getPrice() >= 1400.0 && stock.getPrice() <= 2000.0).forEach(System.out::println);
        System.out.println("FunctionalWay | Filtered by PRICE range using MethodRef");
        StockFiltersFunctionalWay.filter(stockList, StockFiltersFunctionalWay.byPriceRange(50, 500)).forEach(System.out::println);
        System.out.println("FunctionalWay | Filtered by PRICE range using MethodRef");
        StockFiltersFunctionalWay.filter(stockList, StockFiltersFunctionalWay.byPriceRange(300, 1500)).forEach(System.out::println);
        System.out.println("FunctionalWay | Filtered by PRICE range and SYMBOL using MethodRef");

        // look how in this case it's so easy chains filters in order to apply different strategies.
        StockFiltersFunctionalWay.filter(stockList,
                StockFiltersFunctionalWay.byPriceRange(300, 1500)
                        .and(StockFiltersFunctionalWay.bySymbol("GOOGL")))
                .forEach(System.out::println);
    }
}