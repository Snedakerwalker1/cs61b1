import sun.awt.image.ImageWatched;
import sun.font.TrueTypeFont;

import java.awt.event.ItemEvent;

/**
 * Created by wsnedaker on 1/29/2017.
 */
public class LinkedListDeque<Item> {
    private class LinkedList {
        public Item first;
        public LinkedList next;
        public LinkedList prev;

        public LinkedList(Item item){
            this.first = item;
        }
        public LinkedList(LinkedList prev, Item item, LinkedList next) {
            this.next = next;
            this.first = item;
            this.prev = prev;
        }
        public Item getLinked(int index) {
            if (index == 0) {
                return this.first;
            }
            return this.next.getLinked(index-1);
        }
    }
    private int size;
    private LinkedList sentinal;
    private boolean empty = true;

    public LinkedListDeque() {
        size = 0;
        sentinal = new LinkedList(null);
    }
    /*
    Adds an item to the front of the List
    */
    public void addFirst(Item item) {
        empty = false;
        if (sentinal.next == null || sentinal.next.first == null){
            sentinal.next = new LinkedList(sentinal, item, sentinal);
            sentinal.prev = sentinal.next;
            size += 1;
        } else {
            sentinal.next = new LinkedList(sentinal, item, sentinal.next);
            sentinal.next.next.prev = sentinal.next;
            size += 1;
        }
    }
    /*
    Adds an item to the end of the list
    */
    public void addLast(Item item) {
        empty = false;
        if (sentinal.prev == null ) {
            sentinal.prev = new LinkedList(sentinal, item, sentinal);
            sentinal.next = sentinal.prev;
            size += 1;
        } else {
            sentinal.prev.next = new LinkedList(sentinal.prev, item, sentinal);
            sentinal.prev = sentinal.prev.next;
            size += 1;
        }
    }
    /*
    Checks to see if the list is empty
    */
    public boolean isEmpty() {
        if (empty) {
            return empty;
        }else {
            return sentinal.next.first == null;
        }
    }
    /*
    Returns the size of the list
     */
    public int size() {
        return size;
    }
    /*
    Prints the items in the list separated by a space
    */
    public void printDeque() {
        if (empty == false) {
            LinkedList pntr = sentinal.next;
            while (pntr.first != null) {
                System.out.print(pntr.first + " ");
                pntr = pntr.next;
            }
        }
    }
    /*
    Removes the first item from the list.
    */
    public Item removeFirst(){
        if (isEmpty()){
            return null;
        }
        LinkedList pntr = sentinal.next;
        Item first = pntr.first;
        pntr.next.prev = pntr.prev;
        pntr.prev.next = pntr.next;
        pntr.next = pntr.prev = null;
        size -= 1;
        return first;
    }
    /*
    Removes the last item in the list and returns it.
     */
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        LinkedList pntr = sentinal.prev;
        Item first = pntr.first;
        pntr.next.prev = pntr.prev;
        pntr.prev.next = pntr.next;
        pntr.next = pntr.prev = null;
        size -= 1;
        return first;
    }
    /*
    Returns the item from the defined index, \
    and if no such item exists returns null
     */
    public Item get(int index) {
        if (index > size) {
            return null;
        }
        if (index < 0) {
            return null;
        }else {
            LinkedList pntr = sentinal.next;
            int count = 0;
            while (count != index) {
                pntr = pntr.next;
                count += 1;
            }
            return pntr.first;
        }

    }
    /*
    Uses recursion not iteration to get an item at some index,
    and if said index doesn't exist retunrs null.
     */
    public Item getRecursive(int index) {
        LinkedList pntr = sentinal.next;
        if (index >= size){
            return null;
        }
        return pntr.getLinked(index);
    }

}
