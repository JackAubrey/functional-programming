package courses.basics_strong.funcprogramming.section15;

import courses.basics_strong.funcprogramming.section15.datastructures.MapFun;

public class MapDemo {

    public static void main(String[] args) {

        MapFun<Integer, String> map = new MapFun<>(5);
        map.put(1, "Nikhil");
        map.put(2, "Sourabh");
        map.put(3, "Alex");
        map.put(5, "Shawn");
        map.put(100, "Justin");

        map.display();

    }

}
