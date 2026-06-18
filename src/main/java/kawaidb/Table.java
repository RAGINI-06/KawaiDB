package kawaidb;
//
//public class Table {
//}
import java.util.List;

public class Table {

    private String name;
    private List<String> columns;

    public Table(String name, List<String> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public List<String> getColumns() {
        return columns;
    }
}
