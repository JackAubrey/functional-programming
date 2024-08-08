package courses.basics_strong.funcprogramming.section12;

import courses.basics_strong.funcprogramming.section11.CustomSpliterator;
import courses.basics_strong.funcprogramming.section12.model.Employee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectorsInAction {
    public static void main(String[] args) {
        final String fileToStream = "EmployeeData.txt";

        final File file = getFileFromResources(fileToStream);

        // we can obtain a stream of employ in several ways
        // Remember: we can not reuse a stream! So we need read two times the source file.

        // 1. using the Spliterator
        try ( Stream<String> fileStream = Files.lines(file.toPath()) ) {
            Stream<Employee> stream = streamUsingSpliterator(fileStream);
            print("Data retrieved using Spliterator", stream);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        // 2. transforming data using the map operator
        try ( Stream<String> fileStream = Files.lines(file.toPath()) ) {
            Stream<Employee> stream = streamUsingStreamMap(fileStream);
            print("Data retrieved using Stream Map operator", stream);
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        // Now! Let's see collectors in action
        // The easiest: collect data to a new List. Also, this list will be used to store data and prevent to recreate the streaim from scratch every time.
        List<Employee> employees = new ArrayList<>();
        try ( Stream<String> fileStream = Files.lines(file.toPath()) ) {
            Stream<Employee> stream = streamUsingStreamMap(fileStream);
            employees = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        // We want collect the employee name to a single list
        List<String> employeeNames = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        print("Employee Names", employeeNames.stream());

        // Now we want to know all kind designations
        Set<String> employeeDesignations = employees.stream()
                .map(Employee::getDesignation)
                .collect(Collectors.toSet());
        print("Distinct Employee Designations", employeeDesignations.stream());

        // suppose we're not sure whether the data in the file we are reading from is sorted according to the employee IDs
        // but, we want to have all the employees in a structure where we get employees sorted according to their ids
        //
        // the "toSet" can't help because it internally returns a hash set where we don't have sorting of data
        // basically we want a tree set to get the sorted data
        //
        // NOTE: This example is able to works because the Employee beans implements Comparable interface
        // Its Comparable implementation compare the IDs
        Set<Employee> sortedEmployeeById = employees.stream()
                .collect(Collectors.toCollection(TreeSet::new));
        print("Sorted Employee by ID", sortedEmployeeById.stream());

        // now we want a map where the key is the employee id and value is the employee object
        Map<Integer, Employee> mappedEmployee = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Function.identity()));
        print("Mapped Employee by ID", mappedEmployee.entrySet().stream());

        // similar to previous, but now we want a map where the key is the employee id and value is the employee name
        Map<Integer, String> mapIdName = employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));
        print("Mapped Employee Name by ID", mapIdName.entrySet().stream());

        // now we are going to see some method to partition data
        // we want partition employee by gender
        //
        // In this case we are using the collectors partition api
        Map<Boolean, List<Employee>> partitionByGender = employees.stream()
                .collect(
                        Collectors.partitioningBy(employee -> 'M' == employee.getGender())
                );
        print("Employee partitioned by gender", partitionByGender.entrySet().stream());
        // but we can also use the grouping Function
        Map<Character, List<Employee>> groupedByGender = employees.stream()
                .collect(
                        Collectors.groupingBy(Employee::getGender)
                );
        print("Employee grouped by gender", groupedByGender.entrySet().stream());
        // using grouping function we can also create a sub partition
        Map<Character, Map<String, List<Employee>>> groupedByGenderAndDesignation = employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::getGender,
                                Collectors.groupingBy(Employee::getDesignation))
                );
        print("Employee grouped by gender and designation", groupedByGenderAndDesignation.entrySet().stream());
        // this example starts from the previous but, also we want map the final list taking only the employ name
        Map<Character, Map<String, List<String>>> groupedByGenderAndDesignationNameOnly = employees.stream()
                .collect(
                        Collectors.groupingBy(
                                Employee::getGender,
                                Collectors.groupingBy(
                                        Employee::getDesignation,
                                        Collectors.mapping(e -> e.getName(), Collectors.toList())
                                )
                        )
                );
        print("Employee NameOnly grouped by gender and designation", groupedByGenderAndDesignationNameOnly.entrySet().stream());
    }

    static void print(String title, Stream<?> stream) {
        System.out.println("#####################################################");
        System.out.println("## "+title);
        System.out.println("#####################################################");
        stream.forEach(System.out::println);
    }

    static File getFileFromResources(String fileName) {
        String file = CustomSpliterator.class.getClassLoader()
                .getResource(fileName)
                .getFile();
        return new File(file);
    }

    static Stream<Employee> streamUsingSpliterator(Stream<String> fileStream) {
        EmployeeSpliterator employeeSpliterator = new EmployeeSpliterator(fileStream.spliterator());
        return StreamSupport.stream(employeeSpliterator, false);
    }

    static Stream<Employee> streamUsingStreamMap(Stream<String> fileStream) {
        return fileStream.map(s -> s.split(","))
                .map(EmployeeMapper::mapFromArray);
    }
}

class EmployeeMapper {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    static Employee mapFromArray(String [] lineParts) {
        int id = Integer.valueOf(lineParts[0]);
        String name = lineParts[1];
        char gender = lineParts[2].charAt(0);
        Date dob = null;
        try {
            dob = dateFormat.parse(lineParts[3]);
        } catch (ParseException e) {}
        String city = lineParts[4];
        String designation = lineParts[5];
        Date joiningDate = null;
        try {
            joiningDate = dateFormat.parse(lineParts[6]);
        } catch (ParseException e) {}
        double salary = Double.valueOf(lineParts[7]);

        return new Employee(id, name, gender, dob, city, designation, joiningDate, salary);
    }
}

class EmployeeSpliterator implements Spliterator<Employee> {

    private final Spliterator<String> spliterator;
    private Employee employee = null;

    public EmployeeSpliterator(Spliterator<String> spliterator) {

        this.spliterator = spliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Employee> action) {
        boolean success = spliterator.tryAdvance(line -> employee = EmployeeMapper.mapFromArray(line.split(",")));

        if(success) {
            action.accept(employee);
        }

        return success;
    }

    @Override
    public Spliterator<Employee> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return 10;
    }

    @Override
    public int characteristics() {
        return spliterator.characteristics();
    }
}
