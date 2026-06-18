package kawaidb;
//
//public class Table {
//}

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private List<String> columns;
    private List<List<String>> rows;

    public Table(String name, List<String> columns) {
        this.name = name;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    public void insertRow(List<String> values) {
        rows.add(values);
    }

    public String getName() {
        return name;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<List<String>> getRows() {
        return rows;
    }
}
