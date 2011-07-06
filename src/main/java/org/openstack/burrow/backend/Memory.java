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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;

  public class Memory implements Backend {
	private LinkedHashMap<String, QueueMap> accountMap;

	public Memory() {
		accountMap = new LinkedHashMap<String, QueueMap>();
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
    public void createMessage(String account, String queue, String messageId, String body, Long ttl, Long hide) {
        QueueMap acctQueues;

        synchronized(this) {
            if (!accountMap.containsKey(account)) {
                accountMap.put(account, new QueueMap());
            }

            acctQueues = accountMap.get(account);
        }

        acctQueues.put(queue, messageId, body, ttl, hide);
    }

      /**
       * Delete accounts, including the associated queues and messages.
       *
       * @param marker Optional. Only accounts with a name after this marker will be
       *               deleted.
       * @param limit  Optional. Delete at most this many accounts.
       * @param detail Optional. Return the names of the accounts deleted.
       * @return A list of Account instances deleted, with the requested level of
       *         detail.
       */
      public List<Account> deleteAccounts(String marker, Long limit, String detail) {
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
    public List<Message> deleteMessages(String account, String queue, String marker, Long limit, Boolean matchHidden, String detail, Long wait) {
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
    public List<Queue> deleteQueues(String account, String marker, Long limit, String detail) {
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
    public List<Account> getAccounts(String marker, Long limit) {
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
    public List<Message> getMessages(String account, String queue, String marker, Long limit, Boolean matchHidden, String detail, Long wait) {
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
    public List<Queue> getQueues(String account, String marker, Long limit) {
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
    public Message updateMessage(String account, String queue, String messageId, Long ttl, Long hide, String detail) {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
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
    public List<Message> updateMessages(String account, String queue, String marker, Long limit, Boolean matchHidden, Long ttl, Long hide, String detail, Long wait) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

class QueueMap {
	private LinkedHashMap<String, MemoryQueue> queueMap;

	QueueMap() {
		queueMap = new LinkedHashMap<String, MemoryQueue>();
	}

	void put(String queue, String messageId, String body, long ttl, long hide) {
        MemoryQueue q;

        synchronized (this) {
            if (!queueMap.containsKey(queue)) {
                queueMap.put(queue, new MemoryQueue());
            }

            q = queueMap.get(queue);
        }

        q.put(messageId, body, ttl, hide);
    }
}

class MemoryQueue {
    private class MessageRecord {
        long ttl, createdAt;
        long hide, hiddenAt;
        Message msg;

        private MessageRecord(long ttl, long hide, Message msg) {
            this.ttl = ttl;
            this.hide = hide;
            this.msg = msg;

            createdAt = System.currentTimeMillis();
            if (hide != 0) hiddenAt = System.currentTimeMillis();
        }
    }

	private LinkedHashMap<String, MessageRecord> queue;

	MemoryQueue() {
		queue = new LinkedHashMap<String, MessageRecord>();
	}

	synchronized void put(String messageId, String body, long ttl, long hide) {
		clean();
		queue.put(messageId, new MessageRecord(ttl, hide, null)); //TODO: Actually insert a message once Message def'd
	}

	synchronized Message get(String messageId, Long hide) {
		clean();
        MessageRecord message = queue.get(messageId);

        if (message == null) throw new RuntimeException(); //TODO: Decide on the exception we want to throw

        if (hide != 0) {
            message.hide = hide;
            message.hiddenAt = System.currentTimeMillis();
        }

        return message.msg;
	}

	synchronized List<Message> get(String marker, int n, long hide) {
		clean();
		Iterator<Map.Entry<String, MessageRecord>> iter = queue.entrySet().iterator();
		ArrayList<Message> results = new ArrayList<Message>();

		while ((marker != null) && (iter.hasNext())) {
			if (iter.next().getKey().equals(marker)) break;
		}

		while (iter.hasNext() && (n > 0)) {
			MessageRecord m = iter.next().getValue();
			if (m.hide == 0) {
				results.add(m.msg);
				n--;
			}
		}
		return results;
	}

    synchronized Message get(String id) {
		clean();
		MessageRecord m = queue.get(id);

        if (m != null) {
			return m.msg;
		}

		return null;
	}

	synchronized Message remove(String id) {
		MessageRecord m = queue.remove(id);
		clean();

		if (m != null) {
			return m.msg;
		}

		return null;
	}

	synchronized void clean() {
		Iterator<MessageRecord> iter = queue.values().iterator();
		long now = System.currentTimeMillis();

		while (iter.hasNext()) {
			MessageRecord msg = iter.next();

			if ((msg.hide != 0) && (msg.hide + msg.hiddenAt < now)) {
				msg.hide = 0;
			}

			if ((msg.ttl + msg.createdAt < now)) {
				iter.remove();
			}
		}
	}
}
