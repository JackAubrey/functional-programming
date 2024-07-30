package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay;

import courses.basics_strong.funcprogramming.section9.design_patterns.data.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockFiltersImperativeWay {
    public static List<Stock> bySymbol(List<Stock> list, String symbol) {
        List<Stock> filteredData = new ArrayList<>();

        for(Stock s : list) {
            if(s.getSymbol().equalsIgnoreCase(symbol)) {
                filteredData.add(s);
            }
        }

        return filteredData;
    }

    public static List<Stock> byPrice(List<Stock> list, double from, double to) {
        List<Stock> filteredData = new ArrayList<>();

        for(Stock s : list) {
            if(s.getPrice() >= from && s.getPrice() <= to) {
                filteredData.add(s);
            }
        }

        return filteredData;
    }
}
