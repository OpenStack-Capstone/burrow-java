import java.util.HashTable;
import java.util.LinkedHashMap;
public class Memory {
	private HashTable<String, AccountMap> versions;
}

private class AccountMap {
	private HashTable<String, QueueMap> accounts;
}

private class QueueMap {
	private HashTable<String, Queue> queues;
}

private synchronized class Queue {
	private LinkedHashMap<String, Message> queue;

	Queue() {
		//Use default size/load factor, with insertion-order iteration
		queue = new LinkedHashMap<String, Message>(16, 0.75, false);
	}

	void enqueue(Message msg) {
		clean();
		queue.put(msg.id, msg);
	}

	Message dequeue(int hide) {
		clean();
		Iterator<Message> iter = queue.values().iterator();
	}

	List<Message> dequeue(int n, int hide) {
		clean();
		Iterator<Message> iter = queue.values().iterator();
		ArrayList<Message> results = new ArrayList<Message>();
		
		while (iter.hasNext() && (n > 0)) {
			Message m = iter.next();
			if (m.hide == 0) {
				results.add(m);
				n--;
			}
		}
		return results;
	}

	boolean remove(String id) {
		Message m = queue.remove(id);
		clean();

		if (m != null) {
			return true;
		}

		return false;
	}

	Message get(String id) {
		clean();
		return queue.get(id);
	}

	void clean() {
		Iterator<Message> iter = queue.values().iterator();
		long now = System.nanotime();

		while (iter.hasNext()) {
			Message m = iter.next();

			if ((m.hide != 0) && (m.hide + m.hiddenAt < now)) {
				m.hide = 0;
			}

			if ((m.ttl + m.createdAt < now)) {
				iter.remove();
			}
		}
	}
}
