package kawaidb;
//
//public class Table {
//}


import java.util.ArrayList;
import java.util.List;

public class Table {

    private String name;
    private Schema schema;
    private List<Row> rows;

    public Table(String name, Schema schema) {
        this.name = name;
        this.schema = schema;
        this.rows = new ArrayList<>();
    }

    public void insertRow(Row row) {
        rows.add(row);
    }

    public String getName() {
        return name;
    }

    public Schema getSchema() {
        return schema;
    }

    public List<Row> getRows() {
        return rows;
    }

    // Compatibility method
    public List<String> getColumns() {

        List<String> names = new ArrayList<>();

        for (Column column : schema.getColumns()) {
            names.add(column.getName());
        }

        return names;
    }
}