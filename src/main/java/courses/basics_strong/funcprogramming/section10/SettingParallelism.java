package courses.basics_strong.funcprogramming.section10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Supplier;

public class SettingParallelism {
    public static void main(String[] args) {
        // We are always able to know how many processors our machine has.
        System.out.println("The Machine has "+Runtime.getRuntime().availableProcessors()+" processors");
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");

        // After common parallelism property set
        // we are able to read the Fork-Join setting.
        // NOTE! After this operation, the Fork-Join factory has been created, and you are no longer able to change it again.
        System.out.println("The Fork-Join could use "+ ForkJoinPool.getCommonPoolParallelism() +" processors");

        int parallelism = 4;
        ForkJoinPool pool = new ForkJoinPool(parallelism);
        List<Employee> list = createList();
        Callable<Long> callableTask = () -> list.parallelStream()
                .filter( e -> e.getSalary() > 1000)
                .count();

        try {
            long count = pool.submit(callableTask).get();
            System.out.println("Count = "+count);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }

    static List<Employee> createList() {
        List<Employee> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            list.add(new Employee("John", 20000));
            list.add(new Employee("Rohn", 3000));
            list.add(new Employee("Tom", 15000));
            list.add(new Employee("Bheem", 8000));
            list.add(new Employee("Shiva", 200));
            list.add(new Employee("Krishna", 50000));
        }

        return list;
    }
}
