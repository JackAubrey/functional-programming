package courses.basics_strong.reactive.section19;

import courses.basics_strong.reactive.BasicExampleClass;
import courses.basics_strong.reactive.section19.model.Employee;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

import java.util.Comparator;
import java.util.List;

public class OperatorsInAction extends BasicExampleClass {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        Observable<Employee> source = Observable.just(
                new Employee(101, "Alexa", 60000, 4.0),
                new Employee(123, "Ashwani", 94000, 4.7),
                new Employee(236, "Mike", 65000, 4.0),
                new Employee(155, "Ella", 85000, 4.4),
                new Employee(443, "George", 50000, 3.6),
                new Employee(127, "Shreya", 85000, 4.5),
                new Employee(509, "Daniel", 60000, 4.0),
                new Employee(344, "Lucy", 94000, 4.7),
                new Employee(509, "Harry", 75000, 4.3),
                new Employee(344, "Emma", 55000, 3.7)
        );

        // given a source with employees
        log("## Top Employees");
        Disposable employeesDisposable = source
                // we want filter employees with rating greater than 4.0
                .filter(topEmployees())
                // we want sort the result in accordion their salary
                .sorted(sortEmployByRating())
                // now we don't need whole Employee object but only its name and rating
                .map(toNameAndRating())
                // also we want limit the result to the first 4
                .take(4)
                //convert the result into a Single<List<String>>
                .toList()
                .subscribe(OperatorsInAction::log);
        disposables.add(employeesDisposable);

        // given a list of expenses by a person in each month of a certain year
        List<Integer> expenses = List.of(200, 500, 300, 340, 129, 234, 999, 1030, 3400, 890, 996, 789);

        // we need to know the total expense up to a certain month
        // suppose if january expenses was 100$ and february is 200$ then up to february the total expense was 300$
        // so this data we need corresponding to each month
        log("\n## Expenses Monthly Report");
        Disposable monthlyReportDisposable = Observable.fromIterable(expenses)
                .scan(Integer::sum)
                .subscribe(OperatorsInAction::log);
        disposables.add(monthlyReportDisposable);

        // if we want to know only the total
        log("\n## Expenses Total");
        Disposable totalExpensesDisposable = Observable.fromIterable(expenses)
                .reduce(Integer::sum)
                .subscribe(OperatorsInAction::log);
        disposables.add(totalExpensesDisposable);

        log("\nAre all Observers disposed ? "+disposables.isDisposed());

    }

    /**
     * @return a predicate
     */
    private static Predicate<Employee> topEmployees() {
        return e -> e.getRating() > 4.0;
    }

    private static Comparator<Employee> sortEmployByRating() {
        return (e1, e2) -> Double.compare(e2.getRating(), e1.getRating());
    }

    private static Function<Employee, String> toNameAndRating() {
        return e -> String.format("%-10s | %s", e.getName(), e.getRating());
    }
}
