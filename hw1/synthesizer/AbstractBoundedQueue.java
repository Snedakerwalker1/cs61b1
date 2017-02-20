package synthesizer;

/**
 * Created by wsnedaker on 2/19/2017.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    public int capacity() {
        return this.capacity;
    }
    public int fillCount() {
        return this.fillCount;
    }
}
