package org.openstack.burrow.backend.http;

import org.apache.http.HttpResponse;
import org.openstack.burrow.client.Message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureMessage implements Future<Message> {


    FutureMessage () {

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public synchronized Message get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public synchronized Message get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public synchronized HttpResponse setHttpResponse(HttpResponse response){
        return null;
    }

    public Exception setException(Exception e) {
        return null;
    }
}
