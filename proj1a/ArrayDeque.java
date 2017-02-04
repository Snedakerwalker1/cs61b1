/**
 * Created by wsnedaker on 1/29/2017.
 */
public class ArrayDeque<Item> {
    private int size;
    private int first;
    private int last;
    private Item[] items;
    // since the array starts at size 8 we know that
    // So we have 4 would be the first, and 3 the second

    /*
    Initializes the empty array
     */
    public ArrayDeque() {
        items = (Item []) new Object[8];
        size = 0;
        first = 3;
        last = 4;

    }

    /*
    Resizes the array
     */
    private void resize(int length) {
        Item[] newer = (Item []) new Object[length];
        if (first == items.length - 1) {
            System.arraycopy(items, 0, newer, length / 4, size);
            items = newer;
            first = length / 4 - 1;
            last = first + size + 1;
        } else if (last <= first) {
            System.arraycopy(items, first + 1, newer, length / 4, size - first);
            System.arraycopy(items, 0, newer, length / 4 + size - first, first);
            items = newer;
            first = length / 4 - 1;
            last = length / 4 + size;
        } else {
            System.arraycopy(items, first + 1, newer, length / 4, size);
            items = newer;
            first = length / 4 - 1;
            last = length / 4 + size - 1;
        }
    }
    /*
    Adds an iem at the front of the list
     */
    public void addFirst(Item item) {
        if (size + 1 == items.length) {
            // update the size of t he list
            resize((1 + size) * 2);
            items[first] = item;
            if (first == 0) {
                first = items.length - 1;
            } else {
                first -= 1;
            }
            size += 1;
        } else if (first == 0) {
            // Since we are now at the beginning of the list we
            // must wrap the pointer around to the other side
            items[first] = item;
            size += 1;
            first = items.length - 1;
        } else {
            items[first] = item;
            size += 1;
            first -= 1;
        }
    }
    /*
    Adds an item at the end of the list
     */
    public void addLast(Item item) {
        if (size + 1 == items.length) {
            // update the size of the list then add
            resize(2 * (size + 1));
            items[last] = item;
            if (last == items.length - 1) {
                last = 0;
            } else {
                last += 1;
            }
            size += 1;
        } else if (last == items.length - 1) {
            // Since we are now at the end of the list we
            // must wrap the pointer around to the other side
            items[last] = item;
            size += 1;
            last = 0;
        } else {
            items[last] = item;
            size += 1;
            last += 1;
        }
    }
    /*
    Returns true if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    /*
    Returns the cashed size of the list
     */
    public int size() {
        return size;
    }
    /*
    Prssints the items in the list separated by a space
     */
    public void printDeque() {
        int count = 0;
        if (last <= first || size == items.length) {
            int index = 0;
            while (count < items.length - first - 1) {
                System.out.print(items[first + 1 + count] + " ");
                count += 1;
            }
            while (index < last) {
                System.out.print(items[index] + " ");
                index += 1;
            }
        } else {
            while (count < size) {
                System.out.print(items[first + 1 + count] + " ");
                count += 1;
            }
        }
    }
    /*
    Removes and returns the first item
     */
    public Item removeFirst() {
        if (first == items.length - 1) {
            first = 0;
            Item item = items[first];
            items[first] = null;
            size -= 1;
            if (items.length > 8 && size * 4 < items.length) {
                resize(items.length / 2);
            }
            return item;
        } else {
            first += 1;
            Item item = items[first];
            items[first] = null;
            size -= 1;
            if (items.length > 8 && size * 4 < items.length) {
                resize(items.length / 2);
            }
            return item;
        }
    }
    /*
    Removes and returns the last item
     */
    public Item removeLast() {
        if (last == 0) {
            last = items.length - 1;
            Item item = items[last];
            items[last] = null;
            size -= 1;
            if (items.length > 8 && size * 4 < items.length) {
                resize(items.length / 2);
            }
            return item;
        } else {
            last -= 1;
            Item item = items[last];
            items[last] = null;
            if (items.length > 8 && size * 4 < items.length) {
                resize(items.length / 2);
            }
            size -= 1;
            return item;
        }
    }
    /*
    Returns the item from the specified index,
    if no such item exists this returns null
     */
    public Item get(int index) {
        int count = 0;
        int ind = 0;
        while (first + 1 + count < items.length) {
            if (count == index) {
                return items[first + 1 + count];
            }
            count += 1;
        }
        while (ind < last) {
            if (ind + count == index) {
                return items[ind];
            }
            ind += 1;
        }
        return null;
    }
}
