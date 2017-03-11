package db;
import java.util.ArrayList;
import java.util.List;

public class Row {
    public List row;
    public int size;

    public Row() {
        this.row = new ArrayList();
        this.size = 0;
    }

    public void addObject(Object x) {
        row.add(x);
        size += 1;
    }

    public void concatenate(Row r) {
        for (int i = 0; i < r.size; i += 1) {
            this.addObject(r.row.get(i));
        }
    }
}
