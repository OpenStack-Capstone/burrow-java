package org.openstack.burrow.backend.memory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *  Implements a trivial future type for use with the asynchronous memory backend.
 */
class MemoryFuture<V> implements Future<V> {
    private V value;
    private ExecutionException thrown;

    /**
     * Constructs a Future object to contain the result of a successful operation
     * @param value The operations result.
     */
    MemoryFuture(V value) {
        this.value = value;
        thrown = null;
    }

    /**
     * Constructs a Future object to contain the result of an unsuccessful operation
     * @param thrown The exception thrown by the operation.
     */
    MemoryFuture(Throwable thrown) {
        this.thrown = new ExecutionException(thrown);
        this.value = null;
    }

    /**
     * Always returns false.
     *
     * @param b ignored
     * @return Always false
     */
    public boolean cancel(boolean b) {
        return false;
    }

    /**
     * Always returns false.
     *
     * @return Always false
     */
    public boolean isCancelled() {
        return false;
    }

    /**
     * Always returns true.
     *
     * @return Always true
     */
    public boolean isDone() {
        return true;
    }

    /**
     * Retrieve the result of the underlying operation.
     *
     * @return The result of the underlying operation
     * @throws InterruptedException Never.
     * @throws ExecutionException If the underlying operation threw an exception
     */
    public V get() throws InterruptedException, ExecutionException {
        if (thrown != null) throw thrown;
         return value;
    }

    /**
     * Retrieve the result of the underlying operation.
     *
     * @return The result of the underlying operation
     * @throws InterruptedException Never.
     * @throws TimeoutException Never.
     * @throws ExecutionException If the underlying operation threw an exception
     */
    public V get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        if (thrown != null) throw thrown;
        return value;
    }
}
