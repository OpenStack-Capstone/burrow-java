package org.openstack.burrow.backend.memory;

import org.openstack.burrow.backend.AsyncBackend;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import java.util.List;
import java.util.concurrent.Future;

public class AsyncMemory extends Memory implements AsyncBackend {
    /**
     * Asynchronously executes a CreateMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the created message, or null if the queue did not
     *         return any information.
     */
    public Future<Message> executeAsync(CreateMessage request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<Message>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<Message>(e);
        }
    }

    /**
     * Asynchronously executes a DeleteAccounts request.
     *
     * @param request The request to execute.
     * @return A list of Account instances populated with any information returned
     *         about the accounts, or null if no information was returned.
     */
    public Future<List<Account>> executeAsync(DeleteAccounts request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Account>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Account>>(e);
        }
    }

    /**
     * Asynchronously executes a DeleteMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the deleted message, or null if the queue did not
     *         return any information.
     */
    public Future<Message> executeAsync(DeleteMessage request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<Message>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<Message>(e);
        }
    }

    /**
     * Asynchronously executes a DeleteMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the deleted messages, or null if the queue did
     *         not return any information.
     */
    public Future<List<Message>> executeAsync(DeleteMessages request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Message>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Message>>(e);
        }
    }

    /**
     * Asynchronously executes a DeleteQueues request.
     *
     * @param request The request to execute.
     * @return A list of Queue instances populated with any information returned
     *         about the queues, or null if no information was returned.
     */
    public Future<List<Queue>> executeAsync(DeleteQueues request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Queue>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Queue>>(e);
        }
    }

    /**
     * Asynchronously executes a GetAccounts request.
     *
     * @param request The request to execute.
     * @return A list of Account instances populated with any information returned
     *         about the accounts, or null if no information was returned.
     */
    public Future<List<Account>> executeAsync(GetAccounts request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Account>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Account>>(e);
        }
    }

    /**
     * Asynchronously executes a GetMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the message, or null if the queue did not return any
     *         information.
     */
    public Future<Message> executeAsync(GetMessage request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<Message>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<Message>(e);
        }
    }

    /**
     * Asynchronously executes a GetMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the messages, or null if the queue did not
     *         return any information.
     */
    public Future<List<Message>> executeAsync(GetMessages request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Message>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Message>>(e);
        }
    }

    /**
     * Asynchronously executes a GetQueues request.
     *
     * @param request The request to execute.
     * @return A list of Queue instances populated with any information returned
     *         by the queue about the queues, or null if the queue did not return
     *         any information.
     */
    public Future<List<Queue>> executeAsync(GetQueues request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Queue>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Queue>>(e);
        }
    }

    /**
     * Asynchronously executes an UpdateMessage request.
     *
     * @param request The request to execute.
     * @return A Message instance populated with any information returned by the
     *         queue about the message, or null if the queue did not return any
     *         information.
     */
    public Future<Message> executeAsync(UpdateMessage request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<Message>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<Message>(e);
        }
    }

    /**
     * Asynchronously executes an UpdateMessages request.
     *
     * @param request The request to execute.
     * @return A list of Message instances populated with any information returned
     *         by the queue about the messages, or null if the queue did not
     *         return any information.
     */
    public Future<List<Message>> executeAsync(UpdateMessages request) {
        if (request == null)
                throw new IllegalArgumentException("Request may not be null");
        try {
            return new MemoryFuture<List<Message>>(execute(request));
        } catch (Exception e) {
            return new MemoryFuture<List<Message>>(e);
        }
    }
}
