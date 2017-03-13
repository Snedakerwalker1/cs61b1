package hw2;

/**
 * Created by wsnedaker on 3/12/2017.
 */
public class Test {
    @org.junit.Test
    public void Test1() {
        Percolation perc = new Percolation(1);
        if (!perc.isOpen(0,0)) {
             perc.open(0,0);
             System.out.println(perc.percolates());
        }
    }

}
