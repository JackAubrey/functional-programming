package courses.basics_strong.funcprogramming.section14.model;

public class Movie {
    private String name;
    private int releaseYear;
    private String industry;

    public Movie(String name, int releaseYear, String industry) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", industry='" + industry + '\'' +
                '}';
    }
}
