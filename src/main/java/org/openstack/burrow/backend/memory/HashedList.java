package org.openstack.burrow.backend.memory;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 *
 * @param <K> Key type for hash lookups
 * @param <V> Type of values to be contained.
 */
public class HashedList<K, V> {
    PrivEntry[] table;
    PrivEntry front, back;
    int population;

    /**
     *
     */
    public HashedList() {
        table = (PrivEntry[]) new Object[967];
        front = null;
        back = null;
    }

    /**
     * @param key
     * @return
     */
    protected PrivEntry getEntry(K key) {
        int ind = key.hashCode() % table.length;
        PrivEntry curr = table[ind];

        while (curr != null) {
            if (key.equals(curr.key)) return curr;
        }

        return null;
    }


    /**
     * @param e
     */
    protected void chainAdd(PrivEntry e) {
        if (table[e.ind] == null) {
            table[e.ind] = e;
        } else {
            table[e.ind].chainPrev = e;
            e.chainNext = table[e.ind];
            table[e.ind] = e;
        }
    }

    /**
     * @param e
     */
    protected void listAdd(PrivEntry e) {
        if (back == null) {
            front = e;
            back = e;
        } else {
            back.listNext = e;
            e.listPrev = back;
            back = e;
        }
    }

    /**
     * @param e
     */
    protected void chainRemove(PrivEntry e) {
        if (table[e.ind] == e) {
            table[e.ind] = e.chainNext;
            if (table[e.ind] != null) table[e.ind].chainPrev = null;
        } else {
            e.chainPrev.chainNext = e.chainNext;
            if (e.chainNext != null) {
                e.chainNext.chainPrev = e.chainPrev;
            }
        }

        e.chainNext = null;
        e.chainPrev = null;
    }

    /**
     * @param e
     */
    protected void listRemove(PrivEntry e) {
        if (e == front) {
            if (back == front) {
                back = null;
                front = null;
            } else {
                front = front.listNext;
                front.listPrev = null;
            }
        } else if (e == back) {
            back = back.listPrev;
            back.listNext = null;
        } else {
            e.listPrev.listNext = e.listNext;
            e.listNext.listPrev = e.listPrev;
        }

        e.listNext = null;
        e.listPrev = null;
    }

    /**
     * @param key
     * @return
     */
    public V get(K key) {
        PrivEntry e = getEntry(key);
        return e == null ? null : e.value;
    }

    /**
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        int ind = key.hashCode() % table.length;
        PrivEntry curr = table[ind];

        while (curr != null) {
            if (curr.key.equals(key)) {
                curr.value = value;
                listRemove(curr);
                listAdd(curr);
                return;
            }
        }

        PrivEntry newEntry = new PrivEntry(key, value, ind);
        chainAdd(newEntry);
        listAdd(newEntry);

        population++;
    }

    /**
     * @param key
     * @param value
     */
    public void update(K key, V value) {
        PrivEntry e = getEntry(key);

        if (e == null) {
            put(key, value);
        } else {
            e.value = value;
        }
    }

    /**
     * @param key
     * @return
     */
    public V remove(K key) {
        PrivEntry e = getEntry(key);

        if (e == null) return null;

        removeEntry(e);

        return e.value;
    }

    /**
     * @param e
     */
    private void removeEntry(PrivEntry e) {
        listRemove(e);
        chainRemove(e);

        population--;
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return population == 0;
    }

    /**
     * @param key
     * @return
     */
    public boolean containsKey(K key) {
        return getEntry(key) != null;
    }

    protected class PrivEntry extends Entry<K, V> {
        int ind;
        PrivEntry chainNext, chainPrev;
        PrivEntry listNext, listPrev;

        /**
         * @param key
         * @param value
         * @param ind
         */
        PrivEntry(K key, V value, int ind) {
            this.key = key;
            this.value = value;
            this.ind = ind;
            this.chainNext = null;
            this.chainPrev = null;
            this.listNext = null;
            this.listPrev = null;
        }
    }

    protected class IteratorFrom implements Iterator<Entry<K, V>> {
        PrivEntry nextEntry, curr;
        boolean removed;
        HashedList outer;
        /**
         * @param key
         */
        public IteratorFrom(K key) {
            outer = HashedList.this;

            synchronized (outer) {
              PrivEntry e = getEntry(key);
              if (e == null) nextEntry = null;
              else nextEntry = e.listNext;
            }
                curr = null;
            removed = false;
        }

        /**
         *
         */
        public IteratorFrom() {
            outer = HashedList.this;
            synchronized (outer) {
                nextEntry = front;
            }
            curr = null;
            removed = false;
        }

        /**
         * @return
         */
        public Entry<K, V> next() {
            synchronized (outer) {
                if (nextEntry == null) throw new NoSuchElementException();
                Entry<K, V> ret = nextEntry;
                curr = nextEntry;
                removed = false;
                nextEntry = nextEntry.listNext;
                return ret;
            }
        }

        /**
         * @return
         */
        public boolean hasNext() {
            return nextEntry != null;
        }

        /**
         *
         */
        public void remove() {
            if ((curr == null) || removed) throw new IllegalStateException();
            removed = true;
            removeEntry(curr);
        }
    }

    /**
     * @return
     */
    public Iterator<Entry<K, V>> newIterator() {
        return new IteratorFrom();
    }

    /**
     * @param key
     * @return
     */
    public Iterator<Entry<K, V>> newIteratorFrom(K key) {
        return new IteratorFrom(key);
    }
}

class Entry<L, W> {
    protected L key;
    protected W value;

    /**
     * @return
     */
    public W getValue() {
        return value;
    }

    /**
     * @return
     */
    public L getKey() {
        return key;
    }
}