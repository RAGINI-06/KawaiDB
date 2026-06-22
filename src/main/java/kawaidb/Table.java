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

    public boolean deleteRow(
            String column,
            String value) {

        int columnIndex =
                getColumns().indexOf(column);

        if (columnIndex == -1) {
            return false;
        }

        for (int i = 0; i < rows.size(); i++) {

            Row row = rows.get(i);

            if (row.getValue(column)
                    .equals(value)) {

                rows.remove(i);
                return true;
            }
        }

        return false;
    }

    public boolean updateRow(
            String whereColumn,
            String whereValue,
            String targetColumn,
            String newValue) {

        for (Row row : rows) {

            if (row.getValue(whereColumn).equals(whereValue)) {

                row.setValue(targetColumn, newValue);
                return true;
            }
        }

        return false;
    }
}