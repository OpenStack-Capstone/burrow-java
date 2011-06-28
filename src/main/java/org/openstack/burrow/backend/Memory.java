import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Memory {
	private Hashtable<String, AccountMap> versions;
	
	Memory() {
		versions = new Hashtable<String, AccountMap>();
	}

	void put(Message m, String version, String acct, String queue) {
		synchronized {
			if (!versions.containsKey(version)) {
				versions.put(version, new AccountMap());
			}
		}

		version.get(acct).put(m, queue);
	}
}

class AccountMap {
	private Hashtable<String, QueueMap> accounts;

	AccountMap() {
		accounts = new Hashtable<String, QueueMap>();
	}

	void put(Message m, String acct, String queue) {
		synchronized {
			if (!accounts.containsKey(acct)) {
				accounts.put(acct, new QueueMap());
			}
		}

		accounts.get(acct).put(m, queue);
	}
}

class QueueMap {
	private Hashtable<String, Queue> queues;

	QueueMap() {
		queues = new Hashtable<String, Queue>();
	}

	void put(Message m, String queue) {
		synchronized {
			if (!queues.containsKey(queue)) {
				queues.put(queue, new Queue());
			}
		}

		queues.get(queue).enqueue(m);
	}
}

synchronized class Queue {
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
		long now = System.nanoTime();

		while (iter.hasNext()) {
			Message msg = iter.next();

			if ((msg.hide != 0) && (msg.hide + msg.hiddenAt < now)) {
				msg.hide = 0;
			}

			if ((msg.ttl + msg.createdAt < now)) {
				iter.remove();
			}
		}
	}
}
