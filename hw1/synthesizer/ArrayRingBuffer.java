// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.capacity() == this.fillCount()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        this.rb[last] = x;
        this.last += 1;
        this.fillCount += 1;
        if (this.last == this.capacity()) {
            this.last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (0 == this.fillCount()) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T item = this.rb[this.first];
        this.first += 1;
        this.fillCount -= 1;
        if (this.first == this.capacity()) {
            this.first = 0;
        }
        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return this.rb[this.first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    private class Buffer implements Iterator<T> {
        private int index;

        public Buffer() {
            index = 0;
        }

        public boolean hasNext() {
            return (index < capacity);
        }

        public T next() {
            T current = (T) rb[this.index];
            index += 1;
            return current;
        }
    }

    public Iterator<T> iterator() {
        return new Buffer();
    }
}
