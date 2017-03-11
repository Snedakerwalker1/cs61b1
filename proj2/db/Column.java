package db;
import java.util.ArrayList;
import java.util.List;

public class Column {
    public List column;
    public int size;
    public String name;
    public String type;

    public Column(String name, String type) {
        this.column = new ArrayList();
        this.size = 0;
        this.name = name;
        this.type = type;
    }

    public void addObject(Object x) {
        column.add(x);
        size += 1;
    }
}
