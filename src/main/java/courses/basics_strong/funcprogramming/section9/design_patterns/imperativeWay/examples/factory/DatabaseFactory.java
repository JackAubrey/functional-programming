package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.factory;

public class DatabaseFactory {
    enum DatabaseType {
        RDBMS, DBMS
    }
    public static Database getDatabase(DatabaseType dbType) {
        if(DatabaseType.RDBMS == dbType) {
            return new RDBMS(DatabaseProperties.getRDBMS());
        } else {
            return new DBMS(DatabaseProperties.getDBMS());
        }
    }
}
