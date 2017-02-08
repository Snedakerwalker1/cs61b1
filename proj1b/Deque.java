/**
 * Created by wsnedaker on 2/6/2017.
 */
public interface Deque<Item> {
    void addFirst(Item item);
    void addLast(Item item);
    boolean isEmpty();
    int size();
    void printDeque();
    Item removeFirst();
    Item removeLast();
    Item get(int index);
}
