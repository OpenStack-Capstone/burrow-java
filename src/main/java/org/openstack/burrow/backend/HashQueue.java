import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashQueue<K, V> {
	Entry[] table;
	Entry front, back;
	int population;
	
	public HashQueue() {
		table = (Entry[]) new Object[967];
		front = null;
		back = null;
	}

	protected Entry getEntry(K key) {
		int ind = key.hashCode() % table.length;
		Entry curr = table[ind];

		while (curr != null) {
			if (key.equals(curr.key)) return curr;
		}

		return null;
	}

	protected void chainAdd(Entry e) {
		if (table[e.ind] == null) {
			table[e.ind] = e;
		} else {
			table[e.ind].chainPrev = e;
			e.chainNext = table[e.ind];
			table[e.ind] = e;
		}
	}

	protected void listAdd(Entry e) {
		if (back == null) {
			front = e;
			back = e;
		} else { 
			back.listNext = e;
			e.listPrev = back;
			back = e;
		}
	}

	protected void chainRemove(Entry e) {
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

	protected void listRemove(Entry e) {
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

	public V get(K key) {
		Entry e = getEntry(key);
		return e == null ? null : e.value;
	}

	public void put(K key, V value) {
		int ind = key.hashCode() % table.length;
		Entry curr = table[ind];
		
		while (curr != null) {
			if (curr.key.equals(key)) {
				curr.value = value;
				listRemove(curr);
				listAdd(curr);
				return;
			}
		}

		Entry newEntry = new Entry(key, value, ind);
		chainAdd(newEntry);		
		listAdd(newEntry);

		population++;
	}

	public V remove(K key) {
		Entry e = getEntry(key);

		if (e == null) return null;

		listRemove(e);
		chainRemove(e);

		population--;
		
		return e.value;
	}

	public V dequeue() {
		if (front == null) return null;
		

		Entry e = front;
		
		listRemove(e);
		chainRemove(e);

		population--;

		return e.value;
	}

	public void enqueue(K key, V value) {
		put(key, value);
	}

	public V peek() {
		if (front == null) return null;
		return front.value;
	}
	
	public boolean isEmpty() {
		return population == 0;
	}

	protected class Entry {
		K key;
		V value;
		int ind;
		Entry chainNext, chainPrev;
		Entry listNext, listPrev;
		   
		Entry(K key, V value, int ind) {
			this.key = key;
			this.value = value;
			this.ind = ind;
			this.chainNext = null;
			this.chainPrev = null;
			this.listNext = null;
			this.listPrev = null;
			
		}
	}

	protected class IteratorFrom implements Iterator<V> {
		Entry nextEntry;
		
		public IteratorFrom(K key) {
			Entry e = getEntry(key);
			if (e == null) nextEntry = null;
			else nextEntry = e.listNext;
		} 

		public V next() {
			if (nextEntry == null) throw new NoSuchElementException();
			V ret = nextEntry.value;
			nextEntry = nextEntry.listNext;
			return ret;
		}

		public boolean hasNext() {
			return nextEntry != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public Iterator<V> newIteratorFrom(K key) {
		return new IteratorFrom(key);
	}
}