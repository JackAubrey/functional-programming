package courses.basics_strong.reactive.section19.model;

public class CompanyEmployee {
    private Integer id;
    private String name;
    private String gender;
    private double salary;
    private double rating;

    public CompanyEmployee(Integer id, String name, String gender, double salary, double rating) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "CompanyEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", rating=" + rating +
                '}';
    }
}
