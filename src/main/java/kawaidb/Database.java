package kawaidb;

import java.util.*;

public class Database {

    private Map<String, Table> tables = new HashMap<>();

    // table_column → (value → row)
    private Map<String, Map<String, Row>> indexes = new HashMap<>();

    // ---------------- CREATE TABLE ----------------
    public void createTable(String tableName, List<String> columns) {

        if (tables.containsKey(tableName)) {
            System.out.println("Table already exists.");
            return;
        }

        Schema schema = new Schema();

        for (String column : columns) {
            schema.addColumn(column, DataType.STRING);
        }

        Table table = new Table(tableName, schema);

        tables.put(tableName, table);

        StorageManager.createTableFile(tableName, columns);

        System.out.println("Table '" + tableName + "' created.");
    }

    // ---------------- GET TABLE ----------------
    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void registerTable(String tableName, Table table) {
        if (table != null) {
            tables.put(tableName, table);
        }
    }

    // ---------------- INSERT ----------------
    public void insertRow(String tableName, List<String> values) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        if (values.size() != table.getColumns().size()) {
            System.out.println("Column count mismatch.");
            return;
        }

        List<Column> cols = table.getSchema().getColumns();

        // validate first
        for (int i = 0; i < cols.size(); i++) {

            Column col = cols.get(i);

            if (!isValidType(values.get(i), col.getType())) {
                System.out.println(
                        "Invalid " + col.getType() +
                                " value for column " + col.getName()
                );
                return;
            }
        }

        // create row
        Row row = new Row();

        for (int i = 0; i < cols.size(); i++) {
            row.setValue(cols.get(i).getName(), values.get(i));
        }

        table.insertRow(row);

        StorageManager.appendRow(
                tableName,
                String.join(",", values)
        );

        // ---------------- INDEX UPDATE ----------------
        for (Map.Entry<String, Map<String, Row>> entry : indexes.entrySet()) {

            String indexKey = entry.getKey(); // table_column
            String[] parts = indexKey.split("_");

            if (!parts[0].equals(tableName)) continue;

            String column = parts[1];

            Map<String, Row> index = entry.getValue();

            index.put(row.getValue(column), row);
        }

        System.out.println("1 row inserted.");
    }

    // ---------------- SELECT ALL ----------------
    public void selectAll(String tableName) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        for (String column : table.getColumns()) {
            System.out.print(column + "\t");
        }
        System.out.println();

        for (Row row : table.getRows()) {

            for (String column : table.getColumns()) {
                System.out.print(row.getValue(column) + "\t");
            }
            System.out.println();
        }
    }

    // ---------------- SELECT WHERE ----------------
    public void selectWhere(String tableName, String column, String value) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        String indexKey = tableName + "_" + column;

        if (indexes.containsKey(indexKey)) {

            Row row = indexes.get(indexKey).get(value);

            if (row != null) {
                System.out.println(row);
            }
            return;
        }

        for (Row row : table.getRows()) {

            if (row.getValue(column).equals(value)) {
                System.out.println(row);
            }
        }
    }

    // ---------------- DELETE ----------------
    public void deleteWhere(String tableName, String column, String value) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        boolean deleted = table.deleteRow(column, value);

        if (deleted) {

            StorageManager.rewriteTable(table);

            System.out.println("1 row deleted.");
        } else {
            System.out.println("No matching row.");
        }
    }

    // ---------------- UPDATE ----------------
    public void updateWhere(
            String tableName,
            String whereColumn,
            String whereValue,
            String targetColumn,
            String newValue) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        boolean updated = table.updateRow(
                whereColumn,
                whereValue,
                targetColumn,
                newValue
        );

        if (updated) {
            StorageManager.rewriteTable(table);
            System.out.println("1 row updated.");
        } else {
            System.out.println("No matching row.");
        }
    }

    // ---------------- CREATE INDEX ----------------
    public void createIndex(String tableName, String column) {

        Table table = tables.get(tableName);

        if (table == null) return;

        Map<String, Row> index = new HashMap<>();

        for (Row row : table.getRows()) {
            index.put(row.getValue(column), row);
        }

        indexes.put(tableName + "_" + column, index);

        System.out.println("Index created on " + column);
    }

    // ---------------- LOAD TABLES ----------------
    public void loadAllTables() {

        List<String> tableNames = StorageManager.getTableNames();

        for (String tableName : tableNames) {

            Table table = StorageManager.loadTable(tableName);

            registerTable(tableName, table);
        }

        System.out.println(tableNames.size() + " table(s) loaded.");
    }

    // ---------------- TYPE VALIDATION ----------------
    private boolean isValidType(String value, DataType type) {

        switch (type) {

            case INT:
                try {
                    Integer.parseInt(value);
                    return true;
                } catch (Exception e) {
                    return false;
                }

            case STRING:
                return true;

            default:
                return false;
        }
    }
}