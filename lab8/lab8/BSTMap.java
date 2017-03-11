package lab8;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wsnedaker on 3/10/2017.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    int size;
    private BST bstree;

    private class BST {
        K key;
        V value;
        BST left;
        BST right;

        BST(K k, V v, BST l, BST r) {
            key = k;
            value = v;
            left = l;
            right = r;
        }

        BST get(K k) {
            if (k != null && k.equals(key)) {
                return this;
            }
            if (k.compareTo(key) > 0) {
                if (right == null) {
                    return null;
                }
                return right.get(k);
            } else {
                if (left == null) {
                    return null;
                }
                return left.get(k);
            }
        }
        int put(K k, V v) {
            if (k != null && k.equals(key)) {
                this.value = v;
                return 0;
            } else if (k.compareTo(key) > 0) {
                if (right == null) {
                    this.right = new BST(k, v, null, null);
                    return 1;
                } else {
                    return right.put(k, v);
                }
            } else {
                if (left == null) {
                    this.left = new BST(k, v, null, null);
                    return 1;
                } else {
                    return left.put(k, v);
                }
            }
        }
    }
    @Override
    public void clear() {
        size = 0;
        bstree = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        if (bstree == null) {
            return false;
        }
        return bstree.get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (bstree == null) {
            return null;
        }
        BST placement = bstree.get(key);
        if (placement == null) {
            return null;
        }
        return placement.value;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;

    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (bstree != null) {
            size += bstree.put(key, value);
        } else {
            bstree = new BST(key, value, null, null);
            size += 1;
        }

    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
