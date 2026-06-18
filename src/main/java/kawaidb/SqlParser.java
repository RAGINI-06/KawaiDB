package kawaidb;
//
//public class SqlParser {
//}
import java.util.Arrays;
import java.util.List;

public class SqlParser {

    public static void execute(String sql, Database db) {

        sql = sql.trim();

        if (sql.toUpperCase().startsWith("CREATE TABLE")) {

            String tableName =
                    sql.split(" ")[2];

            String columnPart =
                    sql.substring(
                            sql.indexOf("(") + 1,
                            sql.indexOf(")")
                    );

            List<String> columns =
                    Arrays.asList(columnPart.split(","));

            db.createTable(tableName, columns);
        }
        else if (sql.toUpperCase().startsWith("INSERT INTO")) {

            String tableName =
                    sql.split(" ")[2];

            String valuesPart =
                    sql.substring(
                            sql.indexOf("(") + 1,
                            sql.lastIndexOf(")")
                    );

            List<String> values =
                    Arrays.asList(valuesPart.split(","));

            db.insertRow(tableName, values);
        }
        else {
            System.out.println("Unknown command.");
        }
    }
}