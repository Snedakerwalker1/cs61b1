package synthesizer;
import java.util.Iterator;

/**
 * Created by wsnedaker on 2/19/2017.
 */
public interface BoundedQueue<T> extends Iterable<T> {
    int capacity(); //Returns the size of the buffer
    int fillCount(); //Returns the number of items in the buffer
    void enqueue(T x); //adds an item x to the end
    T dequeue(); // delete and return an item from the front
    T peek(); // retrn but does not delete
    Iterator<T> iterator();
    default boolean isEmpty() {
        return this.fillCount() == 0;
    }
    default boolean isFull() {
        return this.fillCount() == this.capacity();
    }
}
