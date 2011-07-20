package org.openstack.burrow.backend;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashedList<K, V> {
	PrivEntry[] table;
	PrivEntry front, back;
	int population;
	
	public HashedList() {
		table = (PrivEntry[]) new Object[967];
		front = null;
		back = null;
	}

	protected PrivEntry getEntry(K key) {
		int ind = key.hashCode() % table.length;
		PrivEntry curr = table[ind];

		while (curr != null) {
			if (key.equals(curr.key)) return curr;
		}

		return null;
	}

	protected void chainAdd(PrivEntry e) {
		if (table[e.ind] == null) {
			table[e.ind] = e;
		} else {
			table[e.ind].chainPrev = e;
			e.chainNext = table[e.ind];
			table[e.ind] = e;
		}
	}

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

	public  V get(K key) {
		PrivEntry e = getEntry(key);
		return e == null ? null : e.value;
	}

	public  void put(K key, V value) {
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

    public  void update(K key, V value) {
        PrivEntry e = getEntry(key);

        if (e == null) {
            put(key, value);
        } else {
            e.value = value;
        }
    }

	public  V remove(K key) {
		PrivEntry e = getEntry(key);

		if (e == null) return null;

        removeEntry(e);
		
		return e.value;
	}

    private  void removeEntry(PrivEntry e) {
        listRemove(e);
		chainRemove(e);

		population--;
    }

	public  V dequeue() {
		if (front == null) return null;

		PrivEntry e = front;
		
		listRemove(e);
		chainRemove(e);

		population--;

		return e.value;
	}

	public  void enqueue(K key, V value) {
		put(key, value);
	}

	public V peek() {
		if (front == null) return null;
		return front.value;
	}
	
	public  boolean isEmpty() {
		return population == 0;
	}

    public  boolean contains(V value) {
        Iterator<Entry<K,V>> iter = newIterator();

        while (iter.hasNext())
            if (iter.next().equals(value))
                return true;

            return false;
    }

    public  boolean containsKey(K key) {
        return getEntry(key) != null;
    }

	protected class PrivEntry extends Entry<K,V> {
		int ind;
		PrivEntry chainNext, chainPrev;
		PrivEntry listNext, listPrev;
		   
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
		
		public IteratorFrom(K key) {
			PrivEntry e = getEntry(key);
			if (e == null) nextEntry = null;
			else nextEntry = e.listNext;
		    curr = null;
            removed = false;
        }

        public IteratorFrom() {
          nextEntry = front;
          curr = null;
          removed = false;
        }

		public Entry next() {
			if (nextEntry == null) throw new NoSuchElementException();
			Entry ret = nextEntry;
            curr = nextEntry;
            removed = false;
			nextEntry = nextEntry.listNext;
			return ret;
		}

		public boolean hasNext() {
			return nextEntry != null;
		}

		public void remove() {
            if ((curr == null) || removed) throw new IllegalStateException();
            removed = true;
            removeEntry(curr);
		}
	}

    public Iterator<Entry<K,V>> newIterator() {
        return new IteratorFrom();
    }

	public Iterator<Entry<K,V>> newIteratorFrom(K key) {
		return new IteratorFrom(key);
	}
}

class Entry<L, W> {
        protected L key;
        protected W value;

        public W getValue() {
            return value;
        }

        public L getKey() {
            return key;
        }
    }