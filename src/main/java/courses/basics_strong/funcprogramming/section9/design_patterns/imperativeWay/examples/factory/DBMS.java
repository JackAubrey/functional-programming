package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.factory;

public class DBMS implements Database {
    private final DatabaseProperties dbProperties;

    public DBMS(DatabaseProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public String connect() {
        return "Connection Successful with DBMS with properties: "+dbProperties;
    }
}
