package kawaidb;

//public class Database {
//}
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.List;
//
//public class Database {
//
//    private Map<String, Table> tables = new HashMap<>();
//
//    public void createTable(String tableName, List<String> columns) {
//
//        if (tables.containsKey(tableName)) {
//            System.out.println("Table already exists.");
//            return;
//        }
//
//        tables.put(tableName,
//                new Table(tableName, columns));
//        StorageManager.createTableFile(
//                tableName,
//                columns
//        );
//
//        System.out.println("Table '" + tableName + "' created.");
//    }
//
//    public Table getTable(String tableName) {
//        return tables.get(tableName);
//    }
//    public void insertRow(String tableName, List<String> values) {
//
//        Table table = tables.get(tableName);
//
//        if (table == null) {
//            System.out.println("Table not found.");
//            return;
//        }
//
//        table.insertRow(values);
//        StorageManager.appendRow(
//                tableName,
//                String.join(",", values)
//        );
//        System.out.println("1 row inserted.");
//    }
//    public void printTableInfo(String tableName) {
//
//        Table table = tables.get(tableName);
//
//        System.out.println(table.getRows());
//    }
//    public void selectAll(String tableName) {
//
//        Table table = tables.get(tableName);
//
//        if (table == null) {
//            System.out.println("Table not found.");
//            return;
//        }
//
//        // Print columns
//        for (String column : table.getColumns()) {
//            System.out.print(column + "\t");
//        }
//
//        System.out.println();
//
//        // Print rows
//        for (var row : table.getRows()) {
//
//            for (String value : row) {
//                System.out.print(value + "\t");
//            }
//
//            System.out.println();
//        }
//    }
//        public void selectWhere(String tableName,
//                String column,
//                String value) {
//
//            Table table = tables.get(tableName);
//
//            if (table == null) {
//                System.out.println("Table not found.");
//                return;
//            }
//
//            int columnIndex =
//                    table.getColumns().indexOf(column);
//
//            if (columnIndex == -1) {
//                System.out.println("Column not found.");
//                return;
//            }
//
//            // print headers
//            for (String col : table.getColumns()) {
//                System.out.print(col + "\t");
//            }
//
//            System.out.println();
//
//            // filter rows
//            for (var row : table.getRows()) {
//
//                if (row.get(columnIndex).equals(value)) {
//
//                    for (String cell : row) {
//                        System.out.print(cell + "\t");
//                    }
//
//                    System.out.println();
//                }
//            }
//        }
//    }


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private Map<String, Table> tables = new HashMap<>();

    public void createTable(String tableName, List<String> columns) {

        if (tables.containsKey(tableName)) {
            System.out.println("Table already exists.");
            return;
        }

        Schema schema = new Schema();

        for (String column : columns) {
            schema.addColumn(
                    column,
                    DataType.STRING
            );
        }

        Table table =
                new Table(
                        tableName,
                        schema
                );

        tables.put(
                tableName,
                table
        );

        StorageManager.createTableFile(
                tableName,
                columns
        );

        System.out.println(
                "Table '" +
                        tableName +
                        "' created."
        );
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void registerTable(String tableName, Table table) {

        if (table != null) {
            tables.put(tableName, table);
        }
    }

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
        List<Column> cols =
                table.getSchema()
                        .getColumns();

        for (int i = 0; i < cols.size(); i++) {

            Column col = cols.get(i);

            if (!isValidType(
                    values.get(i),
                    col.getType())) {

                System.out.println(
                        "Invalid "
                                + col.getType()
                                + " value for column "
                                + col.getName()
                );

                return;
            }
        }

        Row row = new Row();

        for (int i = 0; i < table.getColumns().size(); i++) {
            row.setValue(
                    table.getColumns().get(i),
                    values.get(i)
            );
        }

        table.insertRow(row);

        StorageManager.appendRow(
                tableName,
                String.join(",", values)
        );

        System.out.println("1 row inserted.");
    }

    public void printTableInfo(String tableName) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        System.out.println(table.getRows());
    }

    public void selectAll(String tableName) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        // Print column headers
        for (String column : table.getColumns()) {
            System.out.print(column + "\t");
        }

        System.out.println();

        // Print rows
        for (Row row : table.getRows()) {

            for (String column : table.getColumns()) {
                System.out.print(
                        row.getValue(column)
                                + "\t"
                );
            }

            System.out.println();
        }
    }

    public void selectWhere(
            String tableName,
            String column,
            String value) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }
        if (!table.getColumns().contains(column)) {
            System.out.println("Column not found.");
            return;
        }

        // Print headers
        for (String col : table.getColumns()) {
            System.out.print(col + "\t");
        }

        System.out.println();

        // Filter rows
        for (Row row : table.getRows()) {

            if (row.getValue(column).equals(value)) {

                for (String col : table.getColumns()) {
                    System.out.print(
                            row.getValue(col)
                                    + "\t"
                    );
                }

                System.out.println();
            }
        }
    }
        public void loadAllTables() {

            List<String> tableNames =
                    StorageManager.getTableNames();

            for (String tableName : tableNames) {

                Table table =
                        StorageManager.loadTable(
                                tableName
                        );

                registerTable(
                        tableName,
                        table
                );
            }

            System.out.println(
                    tableNames.size()
                            + " table(s) loaded."
            );
        }
    private boolean isValidType(
            String value,
            DataType type) {

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
