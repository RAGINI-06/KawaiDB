package kawaidb;
//
//public class SqlParser {
//}
import java.util.Arrays;
import java.util.List;

public class SqlParser {

    public static void execute(String sql, Database db) {

        sql = sql.trim();
        String[] tokens = sql.split(" ");
        String command = tokens[0].toUpperCase();

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
        else if (sql.toUpperCase().contains("WHERE")) {

            String tableName =
                    sql.substring(
                            sql.toUpperCase().indexOf("FROM") + 4,
                            sql.toUpperCase().indexOf("WHERE")
                    ).trim();

            String condition =
                    sql.substring(
                            sql.toUpperCase().indexOf("WHERE") + 5
                    ).trim();

            String[] parts = condition.split("=");

            String column = parts[0].trim();
            String value = parts[1].trim();

            db.selectWhere(tableName, column, value);
        }
        else if (sql.toUpperCase().startsWith("SELECT")) {

            String tableName =
                    sql.substring(
                            sql.toUpperCase().indexOf("FROM") + 4
                    ).trim();

            db.selectAll(tableName);
        }
        else if (command.equals("DELETE")) {

            if (tokens.length < 4) {
                System.out.println("Usage: DELETE tableName column value");
                return;
            }

            String tableName = tokens[1];
            String column = tokens[2];
            String value = tokens[3];

            db.deleteWhere(
                    tableName,
                    column,
                    value
            );
        }
        else if (command.equals("UPDATE")) {

            String tableName = tokens[1];
            String targetColumn = tokens[2];
            String newValue = tokens[3];
            String whereColumn = tokens[4];
            String whereValue = tokens[5];

            db.updateWhere(
                    tableName,
                    whereColumn,
                    whereValue,
                    targetColumn,
                    newValue
            );
        }
        else {
            System.out.println("Unknown command.");
        }
    }
}