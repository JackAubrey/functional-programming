package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.factory;

import java.util.List;

public class DatabaseProperties {
    private List<String> properties;

    private static final List<String> RDBMS_PROPERTIES = List.of("url:rdbms_url", "port:rdbms_port", "user:rdbms_user", "password:rdbms_password", "other:rdbms_other");
    private static final List<String> DBMS_PROPERTIES = List.of("url:dbms_url", "port:dbms_port", "user:dbms_user", "password:dbms_password");

    private DatabaseProperties(List<String> properties) {
        this.properties = properties;
    }

    public static DatabaseProperties getRDBMS() {
        return new DatabaseProperties(RDBMS_PROPERTIES);
    }

    public static DatabaseProperties getDBMS() {
        return new DatabaseProperties(DBMS_PROPERTIES);
    }

    @Override
    public String toString() {
        return "DatabaseProperties{" +
                "properties=" + properties +
                '}';
    }
}
