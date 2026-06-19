package kawaidb;


import java.util.HashMap;
import java.util.Map;

public class Row {

    private Map<String, String> data;

    public Row() {
        data = new HashMap<>();
    }

    public void setValue(
            String column,
            String value) {

        data.put(column, value);
    }

    public String getValue(
            String column) {

        return data.get(column);
    }

    public Map<String, String> getData() {
        return data;
    }
}