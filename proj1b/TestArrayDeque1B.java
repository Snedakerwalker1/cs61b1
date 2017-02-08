/**
 * Created by wsnedaker on 2/6/2017.
 */

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDeque1B {
    @Test
    public void arraytest() {
        StudentArrayDeque<Integer> studarr = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> soluarr = new ArrayDequeSolution<>();
        String messege = " ";
        // main idea taken from StudentArrayDeque launcher
        for (int i = 0; i < 200; i += 1) {
            double randomint = StdRandom.uniform();
            if (randomint < .25) {
                studarr.addFirst(i);
                soluarr.addFirst(i);
                messege = messege + "addFirst(" + i + ")\n";
            } else if (randomint < .5 && randomint > .25) {
                studarr.addLast(i);
                soluarr.addLast(i);
                messege = messege + "addLast(" + i + ")\n";
            } else if (randomint > .5 && randomint < .75) {
                messege = messege + "removeFirst()\n";
                assertEquals(messege, soluarr.removeFirst(), studarr.removeFirst());
            } else {
                messege = messege + "removeLast()\n";
                assertEquals(messege, soluarr.removeLast(), studarr.removeLast());
            }

        }
    }

}
