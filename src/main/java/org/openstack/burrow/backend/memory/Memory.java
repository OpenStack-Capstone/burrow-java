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
import org.openstack.burrow.client.methods.*;

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

    private MemoryQueue ensurePresent(String account, String queue) {
        if (account == null || queue == null)
            throw new IllegalArgumentException("Neither account nor queue identifiers may be null.");

        if (!accountMap.containsKey(account))
            throw new NoSuchAccountException();

        if (!accountMap.get(account).containsKey(queue))
            throw new NoSuchQueueException();

        return accountMap.get(account).get(queue);
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
            deleted.add(new Queue(new Account(this, account), e.getKey()));
            iter.remove();
            limit--;
        }

        return deleted;
    }

    /**
     * Execute a CreateMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the created message, or null if the queue did not
     *         return any information.
     */
    public Message execute(CreateMessage request) {
        if (request == null)
            throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = createIfAbsent(account, queue);

        return mq.put(request.getId(), request.getBody(), request.getTtl(), request.getHide());
    }

    /**
     * Execute a DeleteMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the deleted message, or null if the queue did not
     *         return any information.
     */
    public Message execute(DeleteMessage request) {
        if (request == null)
                    throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.remove(request.getId());
    }

    /**
     * Execute a DeleteMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the deleted messages, or null if the queue did
     *         not return any information.
     */
    public List<Message> execute(DeleteMessages request) {
        if (request == null)
                    throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.remove(request.getMarker(), request.getLimit(), request.getMatchHidden(), request.getWait());
    }

    /**
     * Execute a GetMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the message, or null if the queue did not return any
     *         information.
     */
    public Message execute(GetMessage request) {
        if (request == null)
                    throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        Message msg = mq.get(request.getId());
        if (msg == null) throw new NoSuchMessageException();
        if (!request.getMatchHidden() && (msg.getHide() != 0)) throw new MessageHiddenException();

        return msg;
    }

    /**
     * Execute a GetMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the messages, or null if the queue did not
     *         return any information.
     */
    public List<Message> execute(GetMessages request) {
        if (request == null)
                    throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.get(request.getMarker(), request.getLimit(), request.getMatchHidden(), request.getWait());
    }

    /**
     * Execute an UpdateMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the message, or null if the queue did not return any
     *         information.
     */
    public Message execute(UpdateMessage request) {
        if (request == null)
                    throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.update(request.getId(), request.getTtl(), request.getHide());

    }

    /**
     * Execute an UpdateMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the messages, or null if the queue did not
     *         return any information.
     */
    public List<Message> execute(UpdateMessages request) {
        if (request == null)
                    throw new IllegalArgumentException();

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.update(request.getMarker(), request.getLimit(), request.getMatchHidden(),
                         request.getTtl(), request.getHide(), request.getWait());
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
            queues.add(new Queue(new Account(this, account), e.getKey()));
            limit--;
        }

        return queues;
    }
}