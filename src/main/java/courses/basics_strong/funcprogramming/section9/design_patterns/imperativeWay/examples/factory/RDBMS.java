package courses.basics_strong.funcprogramming.section9.design_patterns.imperativeWay.examples.factory;

public class RDBMS implements Database {
    private final DatabaseProperties dbProperties;

    public RDBMS(DatabaseProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    @Override
    public String connect() {
        return "Connection Successful with RDBMS with properties: "+dbProperties;
    }
}
