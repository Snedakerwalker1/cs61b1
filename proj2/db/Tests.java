package db;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

public class Tests {
    @Test
    public void visualTestJoinSameTable() {
        List<String> columnNames1 = new ArrayList<>();
        List<String> types1 = new ArrayList<>();
        columnNames1.add("a");
        columnNames1.add("b");
        types1.add("int");
        types1.add("int");

        Table T1 = new Table(columnNames1, types1);

        Row row11 = new Row();
        row11.addObject(2);
        row11.addObject(5);
        T1.addRow(row11);

        Row row12 = new Row();
        row12.addObject(8);
        row12.addObject(3);
        T1.addRow(row12);

        Row row13 = new Row();
        row13.addObject(13);
        row13.addObject(7);
        T1.addRow(row13);
        /********************************/
        List<String> columnNames2 = new ArrayList<>();
        List<String> types2 = new ArrayList<>();
        columnNames2.add("a");
        columnNames2.add("b");
        types2.add("int");
        types2.add("int");

        Table T2 = new Table(columnNames2, types2);

        Row row21 = new Row();
        row21.addObject(2);
        row21.addObject(5);
        T2.addRow(row21);

        Row row22 = new Row();
        row22.addObject(8);
        row22.addObject(3);
        T2.addRow(row22);

        Row row23 = new Row();
        row23.addObject(13);
        row23.addObject(7);
        T2.addRow(row23);

        Table T4 = T1.join(T2);

        System.out.println("Left table: \n");
        T1.printTable();
        System.out.println("Right table: \n");
        T2.printTable();
        System.out.println("Joined table: \n");
        T4.printTable();
    }

    @Test
    public void visualTestJoinDifferentTable() {
        List<String> columnNames1 = new ArrayList<>();
        List<String> types1 = new ArrayList<>();
        columnNames1.add("a");
        columnNames1.add("b");
        types1.add("int");
        types1.add("int");

        Table T1 = new Table(columnNames1, types1);

        Row row11 = new Row();
        row11.addObject(3);
        row11.addObject(4);
        T1.addRow(row11);

        Row row12 = new Row();
        row12.addObject(6);
        row12.addObject(3);
        T1.addRow(row12);

        Row row13 = new Row();
        row13.addObject(13);
        row13.addObject(7);
        T1.addRow(row13);
        /********************************/
        List<String> columnNames2 = new ArrayList<>();
        List<String> types2 = new ArrayList<>();
        columnNames2.add("a");
        columnNames2.add("b");
        types2.add("int");
        types2.add("int");

        Table T2 = new Table(columnNames2, types2);

        Row row21 = new Row();
        row21.addObject(2);
        row21.addObject(5);
        T2.addRow(row21);

        Row row22 = new Row();
        row22.addObject(8);
        row22.addObject(3);
        T2.addRow(row22);

        Row row23 = new Row();
        row23.addObject(13);
        row23.addObject(7);
        T2.addRow(row23);

        Table T4 = T1.join(T2);

        System.out.println("Left table: \n");
        T1.printTable();
        System.out.println("Right table: \n");
        T2.printTable();
        System.out.println("Joined table: \n");
        T4.printTable();
    }

    @Test
    public void visualTestJoinSameColumn() {
        List<String> columnNames1 = new ArrayList<>();
        List<String> types1 = new ArrayList<>();
        columnNames1.add("a");
        columnNames1.add("b");
        types1.add("int");
        types1.add("int");

        Table T1 = new Table(columnNames1, types1);

        Row row11 = new Row();
        row11.addObject(3);
        row11.addObject(4);
        T1.addRow(row11);

        Row row12 = new Row();
        row12.addObject(6);
        row12.addObject(3);
        T1.addRow(row12);

        Row row13 = new Row();
        row13.addObject(13);
        row13.addObject(7);
        T1.addRow(row13);
        /********************************/
        List<String> columnNames2 = new ArrayList<>();
        List<String> types2 = new ArrayList<>();
        columnNames2.add("a");
        columnNames2.add("c");
        types2.add("int");
        types2.add("int");

        Table T2 = new Table(columnNames2, types2);

        Row row21 = new Row();
        row21.addObject(2);
        row21.addObject(5);
        T2.addRow(row21);

        Row row22 = new Row();
        row22.addObject(6);
        row22.addObject(4);
        T2.addRow(row22);

        Row row23 = new Row();
        row23.addObject(13);
        row23.addObject(7);
        T2.addRow(row23);

        Table T4 = T1.join(T2);

        System.out.println("Left table: \n");
        T1.printTable();
        System.out.println("Right table: \n");
        T2.printTable();
        System.out.println("Joined table: \n");
        T4.printTable();
    }
}

