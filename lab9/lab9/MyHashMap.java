package lab9;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * Created by wsnedaker on 3/16/2017.
 */
public class MyHashMap<K, V> implements Map61B<K, V> {
    private int size;
    private double loadFactor = 1.25;
    private Set<K> keySet;
    private hash hash;


    public MyHashMap() {
        this.size = 0;
        keySet = new HashSet<K>();

    }
    public MyHashMap(int initialSize) {
        this.size = 0;
        keySet = new HashSet<K>(initialSize);
    }
    public MyHashMap(int initialSize, double loadFactor) {
        this.size = 0;
        keySet = new HashSet<K>(initialSize);
        this.loadFactor = loadFactor;
    }

    public void clear() {
        this.size = 0;
        this.keySet = new HashSet<K>();
        this.loadFactor = 1.25;
    }
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }
    public V get(K key) {
        if (this.containsKey(key)) {
            return (V) this.hash.get(key).entry;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }
    @Override
    public void put(K key, V value) {
        if (keySet.contains(key)) {
            hash.get(key).entry = value;
        }
        else {
            keySet.add(key);
            size += 1;
            this.hash = new hash(key, value, hash);
        }
    }
    @Override
    public Set<K> keySet() {
        return this.keySet;
    }
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Does not support remove");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Does not support remove");
    }

    public Iterator<K> iterator() {
        return new MyHashIterator();
    }
    private class MyHashIterator implements Iterator<K> {
        public MyHashIterator() {
            cur = hash;
        }
        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public K next() {
            K ret = (K) cur.key;
            cur = cur.next;
            return ret;
        }
        private hash cur;
    }

    private class hash<K, V> {
        private K key;
        private V entry;
        private hash next;

        public hash(K key, V entry, hash n) {
            this.key = key;
            this.entry = entry;
            this.next = n;
        }
        public hash get(K key) {
            if (key != null && key.equals(key)) {
                return this;
            }
            if (next == null) {
                return null;
            }
            return next.get(key);
        }

    }

}

