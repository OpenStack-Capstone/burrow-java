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

package org.openstack.burrow.backend.memory;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Memory implements Backend {
    private HashedList<String, MemoryAccount> accountMap;

    public Memory() {
        accountMap = new HashedList<String, MemoryAccount>();
    }

    private MemoryAccount createIfAbsent(String account) {
        MemoryAccount ma;

        if (!accountMap.containsKey(account)) {
            ma = new MemoryAccount();
            accountMap.put(account, ma);
        } else {
            ma = accountMap.get(account);
        }

        return ma;
	}

    private MemoryQueue createIfAbsent(String account, String queue) {
        MemoryAccount ma = createIfAbsent(account);
        MemoryQueue mq;

        if (!ma.containsKey(queue)) {
            mq = new MemoryQueue();
            ma.put(queue, mq);
        } else {
            mq = ma.get(queue);
        }

        return mq;
	}

    private void ensurePresent(String account, String queue) {
        if (account == null || queue == null)
            throw new IllegalArgumentException("Neither account nor queue identifiers may be null.");

        if (!accountMap.containsKey(account))
            throw new NoSuchAccountException();

        if (!accountMap.get(account).containsKey(queue))
            throw new NoSuchQueueException();
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
    public synchronized void createMessage(String account, String queue, String messageId, String body, Long ttl, Long hide) {
        MemoryQueue mq = createIfAbsent(account, queue);
        mq.put(messageId, body, ttl, hide);
	}

    /**
     * Delete accounts, including the associated queues and messages.
     *
     * @param marker Optional. Only accounts with a name after this marker will be
     *               deleted.
     * @param limit  Optional. Delete at most this many accounts. A limit of less than 0
     *               is equivalent to unlimited.
     * @param detail Optional. Return the names of the accounts deleted.
     * @return A list of Account instances deleted, with the requested level of
     *         detail.
     */
    public synchronized List<Account> deleteAccounts(String marker, Long limit, String detail) {
        List<Account> deleted = new ArrayList<Account>();

        Iterator<Entry<String, MemoryAccount>> iter;
        if (marker != null) iter = accountMap.newIteratorFrom(marker);
        else iter = accountMap.newIterator();

        if (limit == null) limit = -1l;

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryAccount> e = iter.next();
            deleted.add(new Account(this, e.getKey()));
            iter.remove();
            limit--;
        }

        return deleted;
    }

    /**
     * Delete a message with a known id.
     *
     * @param account   Delete a message in this account.
     * @param queue     Delete a message in this queue.
     * @param messageId Delete a message with this id.
     */
    public synchronized void deleteMessage(String account, String queue, String messageId) {
        ensurePresent(account, queue);
        accountMap.get(account).get(queue).remove(messageId);
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
    public synchronized List<Message> deleteMessages(String account, String queue, String marker, Long limit, Boolean matchHidden, String detail, Long wait) {
        ensurePresent(account, queue);
        return accountMap.get(account).get(queue).remove(marker, limit, matchHidden, wait);
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
    public synchronized List<Queue> deleteQueues(String account, String marker, Long limit, String detail) {
        if (!accountMap.containsKey(account)) throw new NoSuchAccountException();

        List<Queue> deleted = new ArrayList<Queue>();

        Iterator<Entry<String, MemoryQueue>> iter;
        if (marker != null) iter = accountMap.get(account).newIteratorFrom(marker);
        else iter = accountMap.get(account).newIterator();

        if (limit == null) limit = -1l;

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryQueue> e = iter.next();
            deleted.add(new Queue(this, account, e.getKey()));
            iter.remove();
            limit--;
        }

        return deleted;
    }

    /**
     * List accounts.
     *
     * @param marker Optional. Only accounts with a name after this marker will be
     *               returned.
     * @param limit  Optional. Return at most this many accounts.
     * @return A list of account names.
     */
    public synchronized List<Account> getAccounts(String marker, Long limit) {
        List<Account> accts = new ArrayList<Account>();

        Iterator<Entry<String, MemoryAccount>> iter;
        if (marker != null) iter = accountMap.newIteratorFrom(marker);
        else iter = accountMap.newIterator();

        if (limit == null) limit = -1l;

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryAccount> e = iter.next();
            accts.add(new Account(this, e.getKey()));
            limit--;
        }

        return accts;
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
    public synchronized Message getMessage(String account, String queue, String messageId, String detail) {
        ensurePresent(account, queue);
        Message msg = accountMap.get(account).get(queue).get(messageId);
        if (msg == null) throw new NoSuchMessageException();

        return msg;
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
    public synchronized List<Message> getMessages(String account, String queue, String marker, Long limit, Boolean matchHidden, String detail, Long wait) {
        ensurePresent(account, queue);
        MemoryAccount ma = accountMap.get(account);
        MemoryQueue mq = ma.get(queue);
        if (mq == null) throw new NoSuchQueueException();
        return mq.get(marker, limit, matchHidden, wait);
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
    public synchronized List<Queue> getQueues(String account, String marker, Long limit) {
        List<Queue> queues = new ArrayList<Queue>();
        if (!accountMap.containsKey(account)) throw new NoSuchAccountException();

        Iterator<Entry<String, MemoryQueue>> iter;
        if (marker != null) iter = accountMap.get(account).newIteratorFrom(marker);
        else iter = accountMap.get(account).newIterator();

        if (limit == null) limit = -1l;

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryQueue> e = iter.next();
            queues.add(new Queue(this, account, e.getKey()));
            limit--;
        }

        return queues;
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
    public synchronized Message updateMessage(String account, String queue, String messageId, Long ttl, Long hide, String detail) {
        ensurePresent(account, queue);
        return accountMap.get(account).get(queue).update(messageId, ttl, hide);
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
    public synchronized List<Message> updateMessages(String account, String queue, String marker, Long limit, Boolean matchHidden, Long ttl, Long hide, String detail, Long wait) {
        ensurePresent(account, queue);
        return accountMap.get(account).get(queue).update(marker, limit, matchHidden, ttl, hide, wait);
    }
}