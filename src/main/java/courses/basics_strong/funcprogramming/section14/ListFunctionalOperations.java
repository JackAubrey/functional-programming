package courses.basics_strong.funcprogramming.section14;

import courses.basics_strong.funcprogramming.section14.model.Movie;
import courses.basics_strong.funcprogramming.section14.model.MovieComparator;

import java.util.Arrays;
import java.util.List;

public class ListFunctionalOperations {
    public static void main(String[] args) {
        List<Movie> movies = Arrays.asList(
                new Movie("Spotlight",2015,"Hollywood"),
                new Movie("Avengers: Infinity War",2018,"Hollywood"),
                new Movie("Inception",2010,"Hollywood"),
                new Movie("Forest Gump",1994,"Hollywood"),
                new Movie("3 Idiots",2009,"Bollywood"),
                new Movie("Beauty and the beast",2017,"Hollywood"),
                new Movie("Slumdog Millionaire",2008,"Bollywood")
        );

        // Traversal Operation
        // This operation is equivalent to the classic "for" loop we are used to use before Java 1.8
        log("## Traversal Operation: iterate a list like a classic for-loop");
        printMovies(movies);

        // Sorting Operation
        log("## Sorting Operation: by industry asc");
        movies.sort(MovieComparator.byIndustry(MovieComparator.Order.ASC));
        printMovies(movies);
        log("## Sorting Operation: by industry desc");
        movies.sort(MovieComparator.byIndustry(MovieComparator.Order.DESC));
        printMovies(movies);
        log("## Sorting Operation: by name asc");
        movies.sort(MovieComparator.byName(MovieComparator.Order.ASC));
        printMovies(movies);
        log("## Sorting Operation: by name desc");
        movies.sort(MovieComparator.byName(MovieComparator.Order.DESC));
        printMovies(movies);
        log("## Sorting Operation: by release year asc");
        movies.sort(MovieComparator.byReleaseYear(MovieComparator.Order.ASC));
        printMovies(movies);
        log("## Sorting Operation: by release year desc");
        movies.sort(MovieComparator.byReleaseYear(MovieComparator.Order.DESC));
        printMovies(movies);

        // Filtering Operation
        log("## Filtering Operation");
        movies.stream()
                .filter(movie -> movie.getIndustry().equalsIgnoreCase("bollywood"))
                .forEach(System.out::println);

        // Mapping Operation
        log("## Mapping Operation");
        movies.stream()
                .map(Movie::getName)
                .forEach(System.out::println);

        // Mapping Operation
        log("## Reducing Operation");
        String names = movies.stream()
                .map(Movie::getName)
                .reduce((n1, n2) -> n1 + "," + n2)
                .orElseGet(() -> "No Data");
        System.out.println(names);
    }

    private static void printMovies(List<Movie> list) {
        list.forEach(System.out::println);
    }

    private static void log(String text) {
        System.out.println("\n"+text);
    }
}
