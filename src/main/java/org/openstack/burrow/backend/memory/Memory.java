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

import org.openstack.burrow.backend.AccountNotFoundException;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.CommandException;
import org.openstack.burrow.backend.MessageNotFoundException;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO: Handle 'detail' and 'wait'

public class Memory implements Backend {
    private HashedList<String, MemoryAccount> accountMap;

    public Memory() {
        accountMap = new HashedList<String, MemoryAccount>();
    }

    private MemoryAccount createIfAbsent(String account) {
        if (account == null) throw new IllegalArgumentException("Account identifier may be null.");
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
        if (account == null || queue == null)
            throw new IllegalArgumentException("Neither account nor queue identifiers may be null.");

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

    private MemoryQueue ensurePresent(String account, String queue) throws CommandException {
        if (account == null || queue == null)
            throw new IllegalArgumentException("Neither account nor queue identifiers may be null.");

        if (!accountMap.containsKey(account)
                || accountMap.get(account).isEmpty())
            throw new AccountNotFoundException("No such account.");

        if (!accountMap.get(account).containsKey(queue)
                || accountMap.get(account).get(queue).isEmpty())
            throw new AccountNotFoundException("No such queue.");

        return accountMap.get(account).get(queue);
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
            throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = createIfAbsent(account, queue);

        return mq.put(request.getId(), request.getBody(), request.getTtl(), request.getHide());
    }

    /**
     * Execute a DeleteAccounts request.
     *
     * @param request The request to execute.
     * @return A list of Account instances populated with any information returned
     *         about the accounts, or null if no information was returned.
     */
    public List<Account> execute(DeleteAccounts request) {
        if (request == null)
            throw new IllegalArgumentException("Request object may not be null.");

        List<Account> deleted = new ArrayList<Account>();

        Iterator<Entry<String, MemoryAccount>> iter;
        if (request.getMarker() != null) iter = accountMap.newIteratorFrom(request.getMarker());
        else iter = accountMap.newIterator();

        long limit = request.getLimit() == null ? -1l : request.getLimit();

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryAccount> e = iter.next();
            deleted.add(new Account(e.getKey()));
            iter.remove();
            limit--;
        }

        return deleted;
    }

    /**
     * Execute a DeleteMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the deleted message, or null if the queue did not
     *         return any information.
     * @throws CommandException Iff the requested account or queue does not exist.
     */
    public Message execute(DeleteMessage request) throws CommandException {
        if (request == null)
                    throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        Message msg = mq.remove(request.getId());

        if (mq.isEmpty()) accountMap.get(account).remove(queue);

        return msg;
    }

    /**
     * Execute a DeleteMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the deleted messages, or null if the queue did
     *         not return any information.
     * @throws CommandException Iff the requested account or queue does not exist.
     */
    public List<Message> execute(DeleteMessages request) throws CommandException {
        if (request == null)
                    throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        List<Message> messages = mq.remove(request.getMarker(), request.getLimit(), request.getMatchHidden(), request.getWait());

        if (mq.isEmpty()) accountMap.get(account).remove(queue);

        return messages;
    }

    /**
     * Execute a DeleteQueues request.
     *
     * @param request The request to execute.
     * @return A list of Queue instances populated with any information returned
     *         about the queues, or null if no information was returned.
     * @throws CommandException Iff the requested account does not exist.
     */
    public List<Queue> execute(DeleteQueues request) throws CommandException {
        if (request == null)
            throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getAccount().getId();

        if (!accountMap.containsKey(account)) throw new AccountNotFoundException("No such account");

        List<Queue> deleted = new ArrayList<Queue>();

        Iterator<Entry<String, MemoryQueue>> iter;
        if (request.getMarker() != null) {
            iter = accountMap.get(account).newIteratorFrom(request.getMarker());
        } else iter = accountMap.get(account).newIterator();

        long limit = request.getLimit() == null ? -1l : request.getLimit();

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryQueue> e = iter.next();
            deleted.add(new Queue(request.getAccount(), e.getKey()));
            iter.remove();
            limit--;
        }

        if (accountMap.get(account).isEmpty()) accountMap.remove(account);

        return deleted;
    }

    /**
     * Execute a GetAccounts request.
     *
     * @param request The request to execute.
     * @return A list of Account instances populated with any information returned
     *         about the accounts, or null if no information was returned.
     */
    public List<Account> execute(GetAccounts request) {
        if (request == null)
            throw new IllegalArgumentException("Request object may not be null.");
        List<Account> accts = new ArrayList<Account>();

        Iterator<Entry<String, MemoryAccount>> iter;
        if (request.getMarker() != null) iter = accountMap.newIteratorFrom(request.getMarker());
        else iter = accountMap.newIterator();

        long limit = request.getLimit() == null ? -1l : request.getLimit();

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryAccount> e = iter.next();
            accts.add(new Account(e.getKey()));
            limit--;
        }

        return accts;
    }

    /**
     * Execute a GetMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the message, or null if the queue did not return any
     *         information.
     * @throws CommandException Iff the requested message does not exist.
     */
    public Message execute(GetMessage request) throws CommandException {
        if (request == null)
                    throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        Message msg = mq.get(request.getId());
        if (msg == null) throw new MessageNotFoundException("No such message.");

        return msg;
    }

    /**
     * Execute a GetMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the messages, or null if the queue did not
     *         return any information.
     * @throws CommandException Iff the requested account or queue does not exist.
     */
    public List<Message> execute(GetMessages request) throws CommandException {
        if (request == null)
                    throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.get(request.getMarker(), request.getLimit(), request.getMatchHidden(),
                      request.getWait());
    }

    /**
     * Execute a GetQueues request.
     *
     * @param request The request to execute.
     * @return A list of Queue instances populated with any information returned
     *         by the queue about the queues, or null if the queue did not return
     *         any information.
     * @throws CommandException Iff the requested account does not exist
     */

    public List<Queue> execute(GetQueues request) throws CommandException {
        if (request == null)
            throw new IllegalArgumentException("Request object may not be null.");
        if (request.getAccount().getId() == null)
            throw new IllegalArgumentException("Account identifier may not be null");
        if (!accountMap.containsKey(request.getAccount().getId()))
                throw new AccountNotFoundException("No such account.");

        MemoryAccount ma = accountMap.get(request.getAccount().getId());

        if (ma.isEmpty()) throw new AccountNotFoundException("No such account.");

        List<Queue> queues = new ArrayList<Queue>();
        Iterator<Entry<String, MemoryQueue>> iter;

        if (request.getMarker() != null) {
            iter = ma.newIteratorFrom(request.getMarker());
        } else iter = ma.newIterator();

        long limit = request.getLimit() == null ? -1l : request.getLimit();

        while ((limit != 0) && (iter.hasNext())) {
            Entry<String, MemoryQueue> e = iter.next();
            queues.add(new Queue(request.getAccount(), e.getKey()));
            limit--;
        }

        return queues;
    }

    /**
     * Execute an UpdateMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the message, or null if the queue did not return any
     *         information.
     * @throws CommandException Iff the requested account or queue does not exist.
     */
    public Message execute(UpdateMessage request) throws CommandException {
        if (request == null)
                    throw new IllegalArgumentException("Request object may not be null.");

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
     * @throws CommandException Iff the requested account or queue does not exist.
     */
    public List<Message> execute(UpdateMessages request) throws CommandException {
        if (request == null)
                    throw new IllegalArgumentException("Request object may not be null.");

        String account = request.getQueue().getAccount().getId();
        String queue = request.getQueue().getId();
        MemoryQueue mq = ensurePresent(account, queue);

        return mq.update(request.getMarker(), request.getLimit(), request.getMatchHidden(),
                         request.getTtl(), request.getHide(), request.getWait());
    }
}