package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.factory;

public class FactoryAndFactoryMethodDesignPattern {
    public static void main(String[] args) {
        Database db = null;

        db = DatabaseFactory.getDatabase(DatabaseFactory.DatabaseType.RDBMS);
        System.out.println("DB-Type 1: " + db.getClass().getSimpleName());
        System.out.println(db.connect());

        db = DatabaseFactory.getDatabase(DatabaseFactory.DatabaseType.DBMS);
        System.out.println("DB-Type 2: " + db.getClass().getSimpleName());
        System.out.println(db.connect());
    }
}
