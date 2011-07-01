  /*
 * Copyright (C) 2011 OpenStack LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.openstack.burrow.backend;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Memory implements Backend {
	private Hashtable<String, QueueMap> accounts;


	public Memory() {
		accounts = new Hashtable<String, QueueMap>();
	}

    /**
     * Create a message with a given id.
     *
     * @param account   Create a message in this account.
     * @param queue     Create a message in this queue.
     * @param messageId Create a message with this id.
     * @param body      Create a message with this body.
     * @param ttl       Optional. Create a message that will remain in the queue for up
     *                  to this many seconds.
     * @param hide      Optional. Create a message that is hidden for this many
     *                  seconds.
     */
    public void createMessage(String account, String queue, String messageId, String body, Integer ttl, Integer hide) {
        synchronized(this) {
            if (!accounts.containsKey(account)) {
                accounts.put(account, new QueueMap());
            }
        }

        accounts.get(account).put(m, queue);
    }

    /**
     * Delete accounts, including the associated queues and messages.
     *
     * @param marker Optional. Only accounts with a name after this marker will be
     *               deleted.
     * @param limit  Optional. Delete at most this many accounts.
     * @param detail Optional. Return the names of the accounts deleted.
     * @return A list of account names deleted, or null if not detail=True.
     */
    public List<String> deleteAccounts(String marker, Integer limit, Boolean detail) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete a message with a known id.
     *
     * @param account   Delete a message in this account.
     * @param queue     Delete a message in this queue.
     * @param messageId Delete a message with this id.
     * @param detail    Optional. Return this level of detail about the deleted
     *                  message.
     * @return A Message instance with the requested level of detail, or null if
     *         detail='none'.
     */
    public Message deleteMessage(String account, String queue, String messageId, Boolean matchHidden, String detail) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete messages in a queue.
     *
     * @param account     Delete messages in this account.
     * @param queue       Delete messages in this queue.
     * @param marker      Optional. Delete messages with ids after this marker.
     * @param limit       Optional. Delete at most this many messages.
     * @param matchHidden Optional. Delete messages that are hidden.
     * @param detail      Optional. Return this level of detail about the deleted
     *                    messages.
     * @param wait        Optional. Wait up to this many seconds to delete a message if
     *                    none would otherwise be deleted.
     * @return A list of Message instances with the requested level of detail, or
     *         null if detail='none'.
     */
    public List<Message> deleteMessages(String account, String queue, String marker, Integer limit, Boolean matchHidden, String detail, Integer wait) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete queues, including associated messages.
     *
     * @param account Delete queues in this account.
     * @param marker  Optional. Only queues with a name after this marker will be
     *                deleted.
     * @param limit   Optional. At most this many queues will be deleted.
     * @param detail  Optional. If true, return the names of the queues deleted.
     * @return A list of queue names deleted, or null if not detail=True.
     */
    public List<String> deleteQueues(String account, String marker, Integer limit, Boolean detail) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * List accounts.
     *
     * @param marker Optional. Only accounts with a name after this marker will be
     *               returned.
     * @param limit  Optional. Return at most this many accounts.
     * @return A list of account names.
     */
    public List<String> getAccounts(String marker, Integer limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a message with a known id.
     *
     * @param account   Get a message from this account.
     * @param queue     Get a message from this queue.
     * @param messageId Get a message with this id.
     * @param detail    Return this level of detail about the message.
     * @return A Message instance with the requested level of detail, or null if
     *         detail='none'.
     */
    public Message getMessage(String account, String queue, String messageId, String detail) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get messages from a queue.
     *
     * @param account     Get messages in this account.
     * @param queue       Get messages in this queue.
     * @param marker      Optional. Get messages with ids after this marker.
     * @param limit       Optional. Get at most this many messages.
     * @param matchHidden Optional. Get messages that are hidden.
     * @param detail      Optional. Return this level of detail for the messages.
     * @param wait        Optional. Wait up to this many seconds to get a message if none
     *                    would otherwise be returned.
     * @return A list of Message instances with the requested level of detail.
     */
    public List<Message> getMessages(String account, String queue, String marker, Integer limit, Boolean matchHidden, String detail, Integer wait) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * List queues in an account.
     *
     * @param account List queues in this account.
     * @param marker  Optional. Only queues with a name after this marker will be
     *                listed.
     * @param limit   Optional. At most this many queues will be listed.
     * @return A list of queue names.
     */
    public List<String> getQueues(String account, String marker, Integer limit) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update a message with a known id.
     *
     * @param account   Update a message in this account.
     * @param queue     Update a message in this queue.
     * @param messageId Update a message with this id.
     * @param ttl       Optional. Update the message to remain in the queue for up to
     *                  this many seconds.
     * @param hide      Optional. Update the message to be hidden for this many
     *                  seconds.
     */
    public void updateMessage(String account, String queue, String messageId, Integer ttl, Integer hide) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Update messages in a queue.
     *
     * @param account     Update messages in this account.
     * @param queue       Update messages in this queue.
     * @param marker      Optional. Update messages with ids after this marker.
     * @param limit       Optional. Update at most this many messages.
     * @param matchHidden Optional. Update messages that are hidden.
     * @param ttl         Optional. Update messages to remain in the queue for up to this
     *                    many seconds.
     * @param hide        Optional. Update messages to be hidden this many seconds.
     * @param detail      Optional. Return this level of detail for the updated
     *                    messages.
     * @param wait        Optional. Wait up to this many seconds to update a message if
     *                    none would otherwise be updated.
     * @return A list of updated Message instances with the requested level of
     *         detail, or null if detail='none'.
     */
    public List<Message> updateMessages(String account, String queue, String marker, Integer limit, Boolean matchHidden, Integer ttl, Integer hide, String detail, Integer wait) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

class QueueMap {
	private Hashtable<String, Queue> queues;

	QueueMap() {
		queues = new Hashtable<String, Queue>();
	}

	void put(Message m, String queue) {
		synchronized(this) {
			if (!queues.containsKey(queue)) {
				queues.put(queue, new Queue());
			}
		}

		queues.get(queue).enqueue(m);
	}
}

class Queue {
	private LinkedHashMap<String, Message> queue;

	Queue() {
		//Use default size/load factor, with insertion-order iteration
		queue = new LinkedHashMap<String, Message>(16, 0.75f, false);
	}

	synchronized void enqueue(Message msg) {
		clean();
		queue.put(msg.id, msg);
	}

	synchronized Message dequeue(int hide) {
		clean();
		Iterator<Message> iter = queue.values().iterator();
	}

	synchronized List<Message> dequeue(int n, int hide) {
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

	synchronized boolean remove(String id) {
		Message m = queue.remove(id);
		clean();

		if (m != null) {
			return true;
		}

		return false;
	}

	synchronized Message get(String id) {
		clean();
		return queue.get(id);
	}

	synchronized void clean() {
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
