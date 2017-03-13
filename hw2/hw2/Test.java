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

    @org.junit.Test
    public void test2() {
        PercolationStats percstat = new PercolationStats(2, 100000);
        System.out.print(percstat.mean());
    }

    @org.junit.Test
    public void test3() {
        PercolationStats percstat1 = new PercolationStats(50 , 10 );
        System.out.println(percstat1.mean());
        PercolationStats percstat2 = new PercolationStats(50 , 5 );
        System.out.println(percstat2.mean());
        System.out.println(percstat1.stddev());
        System.out.println(percstat2.stddev());
    }
    @org.junit.Test
    public void test4() {
        PercolationStats percstat = new PercolationStats(20, 10);
        System.out.print(percstat.stddev());
    }
}
