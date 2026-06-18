package kawaidb;

public class Predicate {

    private String column;
    private String value;

    public Predicate(String column, String value) {
        this.column = column;
        this.value = value;
    }

    public String getColumn() {
        return column;
    }

    public String getValue() {
        return value;
    }
}

