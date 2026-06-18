package kawaidb;



import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
public class StorageManager {

    private static final String DATA_DIR = "data";

    static {
        File dir = new File(DATA_DIR);

        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static void createTableFile(String tableName) {

        try {

            File file =
                    new File(DATA_DIR + "/" +
                            tableName + ".tbl");

            file.createNewFile();

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
}