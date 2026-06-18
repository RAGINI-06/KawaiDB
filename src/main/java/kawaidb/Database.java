package kawaidb;

//public class Database {
//}

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Database {

    private Map<String, Table> tables = new HashMap<>();

    public void createTable(String tableName, List<String> columns) {

        if (tables.containsKey(tableName)) {
            System.out.println("Table already exists.");
            return;
        }

        tables.put(tableName,
                new Table(tableName, columns));
        StorageManager.createTableFile(tableName);

        System.out.println("Table '" + tableName + "' created.");
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
    public void insertRow(String tableName, List<String> values) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        table.insertRow(values);
        StorageManager.appendRow(
                tableName,
                String.join(",", values)
        );
        System.out.println("1 row inserted.");
    }
    public void printTableInfo(String tableName) {

        Table table = tables.get(tableName);

        System.out.println(table.getRows());
    }
    public void selectAll(String tableName) {

        Table table = tables.get(tableName);

        if (table == null) {
            System.out.println("Table not found.");
            return;
        }

        // Print columns
        for (String column : table.getColumns()) {
            System.out.print(column + "\t");
        }

        System.out.println();

        // Print rows
        for (var row : table.getRows()) {

            for (String value : row) {
                System.out.print(value + "\t");
            }

            System.out.println();
        }
    }
        public void selectWhere(String tableName,
                String column,
                String value) {

            Table table = tables.get(tableName);

            if (table == null) {
                System.out.println("Table not found.");
                return;
            }

            int columnIndex =
                    table.getColumns().indexOf(column);

            if (columnIndex == -1) {
                System.out.println("Column not found.");
                return;
            }

            // print headers
            for (String col : table.getColumns()) {
                System.out.print(col + "\t");
            }

            System.out.println();

            // filter rows
            for (var row : table.getRows()) {

                if (row.get(columnIndex).equals(value)) {

                    for (String cell : row) {
                        System.out.print(cell + "\t");
                    }

                    System.out.println();
                }
            }
        }
    }
