package org.openstack.burrow.backend.memory;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Math;

/**
 * A Hash table which maintains an order of insertion list of its contents.
 *
 * When using an iterator over this collection in a multithreaded environment, the life of the iterator must be
 * wrapped in a <pre>synchronized (hashListInstance) {..}</pre> block in order to be threadsafe.
 *
 * This class is used instead of LinkedHashMap due to LinkedHashMap's lack of an iterator starting at an arbitrary
 * position, and because inserting a value with an existing key does not alter the position in the insertion-order
 * list.  The members of LinkedHashMap which would need to be altered to change this behaviour are all package or
 * private access, and the license under which it is distributed does not allow direct copying and modification.
 *
 * @param <K> Key type for hash lookups
 * @param <V> Type of values to be contained.
 */
public class HashedList<K, V> {
    PrivEntry[] table;
    PrivEntry front, back;
    int population;

    /**
     * Construct a new HashedList object
     */
    public HashedList() {
        table = (PrivEntry[]) Array.newInstance(PrivEntry.class, 967);
        front = null;
        back = null;
    }

    /**
     * Utility method for fetching table entries
     *
     * @param key Key to fetch entry for
     * @return The internal table entry associated with key, or null if the key does not exists in the map.
     */
    protected PrivEntry getEntry(K key) {
        if (key == null) throw new IllegalArgumentException();
        int ind = Math.abs(key.hashCode()) % table.length;
        PrivEntry curr = table[ind];

        while (curr != null) {
            if (key.equals(curr.key)) return curr;
        }

        return null;
    }


    /**
     * Utility method to add an entry to a hash chain
     *
     * @param e The entry to be added
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
     * Utility method to add an entry to the insertion order list.
     *
     * @param e The entry to be added.
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
     * Utility method to remove an entry from a hash chain.
     *
     * @param e The entry to be removed.
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
     * Utility method to remove an entry from the insertion order list
     *
     * @param e The entry to be removed
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
     * Retrieve a value from the list given the associated key.
     *
     * If it is important to differentiate between a nonexistent and null entry,
     * containsKey(K key) may be used.
     *
     * @param key The key to look up
     * @return The associated value, or null if it does not exist.
     */
    public synchronized V get(K key) {
        PrivEntry e = getEntry(key);
        return e == null ? null : e.value;
    }

    /**
     * Insert a key/value pair into the map, will overwrite any entry currently
     * associated with the given key.
     *
     * @param key The lookup key to associate with the given value.
     * @param value The value to be inserted into the map.
     */
    public synchronized void put(K key, V value) {
        if ((key == null) || (value == null)) throw new IllegalArgumentException();
		int ind = Math.abs(key.hashCode()) % table.length;
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
     * Update the value associated with a given key, or insert the pair into the map
     * if the key does not exist.  update() varies from put() in that update() will not
     * affect the position of the pair in the entry list.
     *
     * @param key The lookup key of the value to be updated.
     * @param value The new value to be associated with the given key.
     */
    public synchronized void update(K key, V value) {
        PrivEntry e = getEntry(key);

        if (e == null) {
            put(key, value);
        } else {
            e.value = value;
        }
    }

    /**
     * Remove the key and its associated value from the map.
     *
     * @param key the key to be removed
     * @return The removed value, or null if the key did not exist.
     */
    public synchronized V remove(K key) {
        PrivEntry e = getEntry(key);

        if (e == null) return null;

        removeEntry(e);

        return e.value;
    }

    /**
     * Utility method to remove an entry from the map
     *
     * @param e The entry to be removed
     */
    private void removeEntry(PrivEntry e) {
        listRemove(e);
        chainRemove(e);

        population--;
    }

    /**
     * Test if there are any entries in the map.
     *
     * @return True if there no entries in the map, false otherwise.
     */
    public synchronized boolean isEmpty() {
        return population == 0;
    }

    /**
     * Test whether a key exists in the map.  Useful for discriminating between
     * keys with null values and nonexistent keys.
     *
     * @param key The key to check for existence
     * @return True if the key exists in the map, false otherwise.
     */
    public synchronized boolean containsKey(K key) {
        return getEntry(key) != null;
    }


    /**
     * Node class for both map and list.
     */
    protected class PrivEntry extends Entry<K, V> {
        int ind;
        PrivEntry chainNext, chainPrev;
        PrivEntry listNext, listPrev;

        /**
         * Contruct a new entry
         *
         * @param key The key to be stored in the entry
         * @param value The value to be stored in the entry
         * @param ind The index which key maps to in the containing table
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

    /**
     * Iterator class to walking the entries in the list.
     */
    protected class IteratorFrom implements Iterator<Entry<K, V>> {
        PrivEntry nextEntry, curr;
        boolean removed;
        final HashedList outer;

        /**
         * Construct an iterator starting at the entry following the given key.
         *
         * @param key The key preceding the desired start position
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
         * Construct a iterator starting at the beginning of the list
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
         * Fetch the next entry in the list
         *
         * @return The next entry in the list
         */
        public Entry<K, V> next() {
			Entry<K, V> ret;
            synchronized (outer) {
                if (nextEntry == null) throw new NoSuchElementException();
				if (curr != null && nextEntry != curr.listNext) throw new ConcurrentModificationException();
                ret = nextEntry;
                curr = nextEntry;
                removed = false;
                nextEntry = nextEntry.listNext;                
            }
			return ret;
        }

        /**
         * Check if there is another item in the list
         *
         * @return True if there are more entries in the list, false otherwise
         */
        public boolean hasNext() {
            return nextEntry != null;
        }

        /**
         * Remove the current item from the map, it is an error to call remove() before next(),
         * or more than once per call to next().
         */
        public void remove() {
            if ((curr == null) || removed) throw new IllegalStateException();
            removed = true;
			synchronized (outer) {
                removeEntry(curr);
            }
			curr = null;
        }
    }

    /**
     * Get an iterator starting at the beginning of the list.
     *
     * @return The iterator
     */
    public Iterator<Entry<K, V>> newIterator() {
        return new IteratorFrom();
    }

    /**
     * Get an iterator starting at the position following the given key.
     *
     * @param key The key preceding the desired start position.
     * @return The iterator
     */
    public Iterator<Entry<K, V>> newIteratorFrom(K key) {
        return new IteratorFrom(key);
    }
}

class Entry<L, W> {
    protected L key;
    protected W value;

    /**
     * Get the value stored in this entry.
     *
     * @return The value stored in this entry.
     */
    public W getValue() {
        return value;
    }

    /**
     * Get the key stored in this entry.
     *
     * @return The key stored in this entry.
     */
    public L getKey() {
        return key;
    }
}