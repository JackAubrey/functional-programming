package courses.basics_strong.funcprogramming.section9.design_patterns.functionalWay;

import courses.basics_strong.funcprogramming.section9.design_patterns.data.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StockFiltersFunctionalWay {
    public static List<Stock> filter(List<Stock> list, Predicate<Stock> predicate) {
        List<Stock> filteredData = new ArrayList<>();

        for(Stock s : list) {
            if(predicate.test(s)) {
                filteredData.add(s);
            }
        }

        return filteredData;
    }

    public static Predicate<Stock> byPriceRange(double from, double to) {
        return stock -> stock.getPrice() >= from && stock.getPrice() <= to;
    }

    public static Predicate<Stock> bySymbol(String symbol) {
        return stock -> stock.getSymbol().equalsIgnoreCase(symbol);
    }
}
