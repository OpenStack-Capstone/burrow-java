package org.openstack.burrow.backend.http;

import org.apache.http.HttpResponse;
import org.openstack.burrow.client.Message;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.openstack.burrow.backend.http.AsyncHttp.handleSingleMessageHttpResponse;

public class FutureMessage implements Future<Message> {
    private static HttpResponse httpResponse;
    private static Exception exception;

    FutureMessage () {
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
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
        while (httpResponse == null){
            this.wait();
        }
        return handleSingleMessageHttpResponse(httpResponse);
    }

    @Override
    public synchronized Message get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }

    public synchronized void setHttpResponse(HttpResponse response){
        this.notify();
        httpResponse = response;
        this.notifyAll();
    }

    public synchronized void setException(Exception e) {
        this.notify();
        exception = e;
        this.notifyAll();
    }
}
