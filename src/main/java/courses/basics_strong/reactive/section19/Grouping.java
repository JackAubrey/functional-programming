package courses.basics_strong.reactive.section19;

import courses.basics_strong.reactive.section19.model.CompanyEmployee;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import net.datafaker.Faker;
import net.datafaker.providers.base.Gender;
import net.datafaker.providers.base.Name;
import net.datafaker.providers.base.Number;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Grouping {

    public static final String FEMALE = "Female";
    public static final String MALE = "Male";

    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        List<CompanyEmployee> employeeList = buildStaticList(); //generate(100);
        Observable<CompanyEmployee> employeeSource = Observable.fromIterable(employeeList);

        // group employees by its rating value
        log("## Grouped by rating value");
        disposables.add(
                employeeSource.groupBy(e -> e.getRating())
                        .flatMapSingle( g -> g.toMultimap(key -> g.getKey(), emp -> emp.getName()))
                        // the flatMap upon is equivalent of
                        // .flatMap( g -> Observable.fromSingle(g.toMap(key -> g.getKey(), emp -> emp.getName())) )
                .subscribe(Grouping::log)
        );

        log("\n## Grouped by rating threshold");
        Function<CompanyEmployee, Double> groupByRatingThreshold = e -> (double)Double.valueOf(e.getRating()).intValue();

        disposables.add(
                employeeSource.groupBy(groupByRatingThreshold)
                        .flatMapSingle( g -> g.toMultimap(key -> g.getKey(), emp -> emp.getName()))
                        // the flatMap upon is equivalent of
                        // .flatMap( g -> Observable.fromSingle(g.toMap(key -> g.getKey(), emp -> emp.getName())) )
                        .subscribe(Grouping::log)
        );

        disposables.dispose();
    }

    private static List<CompanyEmployee> buildStaticList() {
        return List.of(
                new CompanyEmployee(101, "Alexa", FEMALE,60000, 4.0),
                new CompanyEmployee(123, "Ashwani", FEMALE,94000, 4.7),
                new CompanyEmployee(236, "Mike", MALE, 65000, 4.0),
                new CompanyEmployee(155, "Ella", FEMALE,85000, 4.4),
                new CompanyEmployee(443, "George", MALE,50000, 3.6),
                new CompanyEmployee(127, "Shreya", FEMALE,85000, 4.5),
                new CompanyEmployee(509, "Daniel", MALE,60000, 4.0),
                new CompanyEmployee(344, "Lucy", FEMALE,94000, 4.7),
                new CompanyEmployee(509, "Harry", MALE,75000, 4.3),
                new CompanyEmployee(344, "Emma", FEMALE,55000, 3.7)
        );
    }


    private static List<CompanyEmployee> generate(int count) {
        Faker faker = new Faker();
        Name fakerName = faker.name();
        Gender fakerGender = faker.gender();
        Number fakerNumber = faker.number();
        AtomicInteger idGenerator = new AtomicInteger(10000);

        Supplier<CompanyEmployee> supplier = () -> {
            Integer id = idGenerator.getAndIncrement();
            String name = fakerName.fullName();
            String gender = fakerGender.binaryTypes();
            double salary = fakerNumber.randomDouble(2, 50000, 95000);
            double rating = fakerNumber.randomDouble(2, 3, 5);
            return new CompanyEmployee(id, name, gender, salary, rating);
        };

        return Stream.generate(supplier).limit(count).toList();
    }

    private static void log(@NonNull Object e) {
        System.out.println(e);
    }

    private static void sleep(int period) {
        try {
            TimeUnit.SECONDS.sleep(period);
        } catch (InterruptedException e) {
            log(e.toString());
            Thread.currentThread().interrupt();
        }
    }
}
