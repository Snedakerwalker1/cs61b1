/**
 * Created by wsnedaker on 2/3/2017.
 */
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntListTest {

    /**
     * Tests the reverse function
     */
    @Test
    public void reverseTest() {
        IntList ogglst = IntList.list(0, 1, 2, 3);
        IntList noglst = ogglst;
        IntList newlst = IntList.list(3, 2, 1, 0);
        IntList nul = null;
        ogglst = ogglst.reverse(ogglst);
        nul = nul.reverse(nul);
        assertEquals(null, nul.reverse(nul));
        assertEquals(newlst, ogglst);
        assertNotEquals(noglst, ogglst);

    }
    public static void main(String... args) {
        jh61b.junit.TestRunner.runTests("all", IntListTest.class);
    }
}
