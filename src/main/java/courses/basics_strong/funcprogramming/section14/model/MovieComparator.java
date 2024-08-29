package courses.basics_strong.funcprogramming.section14.model;

import java.util.Comparator;

public interface MovieComparator {
    enum Order {
        ASC, DESC
    }

    static Comparator<Movie> byName(Order order) {
        if (order == Order.ASC) {
            return (m1, m2) -> m1.getName().compareTo(m2.getName());
        } else {
            return (m1, m2) -> m2.getName().compareTo(m1.getName());
        }
    }

    static Comparator<Movie> byIndustry(Order order) {
        if (order == Order.ASC) {
            return (m1, m2) -> m1.getIndustry().compareTo(m2.getIndustry());
        } else {
            return (m1, m2) -> m2.getIndustry().compareTo(m1.getIndustry());
        }
    }

    static Comparator<Movie> byReleaseYear(Order order) {
        if (order == Order.ASC) {
            return (m1, m2) -> Integer.compare(m1.getReleaseYear(), m2.getReleaseYear());
        } else {
            return (m1, m2) -> Integer.compare(m2.getReleaseYear(), m1.getReleaseYear());
        }
    }
}
