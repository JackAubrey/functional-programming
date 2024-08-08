package courses.basics_strong.funcprogramming.section8.techniques.model;

public class Square {
    private int area;

    public Square() {
    }

    public Square(int area) {
        setArea(area);
    }

    public int getArea() {
        return area;
    }

    public Square setArea(int area) {
        this.area = area;
        return this;
    }

    @Override
    public String toString() {
        return "Square{" +
                "area=" + area +
                '}';
    }
}
