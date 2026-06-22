package kawaidb;



import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StorageManager {

    private static final String DATA_DIR = "data";

    static {
        File dir = new File(DATA_DIR);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void createTableFile(
            String tableName,
            List<String> columns) {

        try {

            FileWriter writer =
                    new FileWriter(
                            DATA_DIR + "/" +
                                    tableName + ".tbl");

            writer.write(
                    String.join(",", columns)
                            + "\n");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendRow(
            String tableName,
            String rowData) {

        try {

            FileWriter writer =
                    new FileWriter(
                            DATA_DIR + "/" +
                                    tableName + ".tbl",
                            true);

            writer.write(rowData + "\n");

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<String> getTableNames() {

        List<String> tableNames = new java.util.ArrayList<>();

        File folder = new File(DATA_DIR);

        File[] files = folder.listFiles();

        if (files == null) {
            return tableNames;
        }

        for (File file : files) {

            String fileName = file.getName();

            if (fileName.endsWith(".tbl")) {

                String tableName =
                        fileName.substring(
                                0,
                                fileName.length() - 4
                        );

                tableNames.add(tableName);
            }
        }

        return tableNames;
    }

    public static List<String> readAllLines(
            String tableName) {

        try {

            return Files.readAllLines(
                    Paths.get(
                            DATA_DIR + "/" +
                                    tableName + ".tbl"
                    ));

        } catch (IOException e) {

            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    public static Table loadTable(
            String tableName) {

        List<String> lines =
                readAllLines(tableName);

        if (lines.isEmpty()) {
            return null;
        }

        List<String> columns =
                Arrays.asList(
                        lines.get(0).split(",")
                );

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

        for (int i = 1; i < lines.size(); i++) {

            String[] values =
                    lines.get(i).split(",");

            Row row = new Row();

            for (int j = 0; j < columns.size(); j++) {

                row.setValue(
                        columns.get(j),
                        values[j]
                );
            }

            table.insertRow(row);
        }

        return table;
    }
    public static void rewriteTable(Table table) {

        try {

            FileWriter writer =
                    new FileWriter(
                            DATA_DIR + "/" +
                                    table.getName() +
                                    ".tbl"
                    );

            writer.write(
                    String.join(
                            ",",
                            table.getColumns()
                    ) + "\n"
            );

            for (Row row : table.getRows()) {

                List<String> values =
                        new ArrayList<>();

                for (String column :
                        table.getColumns()) {

                    values.add(
                            row.getValue(column)
                    );
                }

                writer.write(
                        String.join(",", values)
                                + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}