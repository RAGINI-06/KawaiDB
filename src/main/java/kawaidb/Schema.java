package kawaidb;



import java.util.ArrayList;
import java.util.List;

public class Schema {

    private List<Column> columns;

    public Schema() {
        columns = new ArrayList<>();
    }

    public void addColumn(
            String name,
            DataType type){

        columns.add(
                new Column(name, type)
        );
    }

    public List<Column> getColumns() {
        return columns;
    }
}
