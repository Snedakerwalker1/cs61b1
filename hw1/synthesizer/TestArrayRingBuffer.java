package synthesizer;
import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
    }
    @Test
    public void test(){
        ArrayRingBuffer<Integer> intarr = new ArrayRingBuffer<>(4);
        int index = 0;
        while (index < 3){
            intarr.enqueue(index);
            index += 1;
        }
        while (index < 5) {
            intarr.dequeue();
            index += 1;
        }
        while (index < 7) {
            intarr.enqueue(index);
            index += 1;
        }
        while (index < 11) {
            intarr.dequeue();
            index += 1;
        }
    }


    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
}

