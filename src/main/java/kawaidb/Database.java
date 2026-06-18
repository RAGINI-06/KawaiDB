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

        System.out.println("Table '" + tableName + "' created.");
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
}