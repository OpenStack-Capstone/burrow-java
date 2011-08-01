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
package org.openstack.burrow.backend.http;

import org.apache.http.HttpResponse;
import org.openstack.burrow.client.Message;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.openstack.burrow.backend.http.AsyncHttp.handleMultipleMessageHttpResponse;

public class FutureMessageList implements Future<List<Message>> {
    private HttpResponse httpResponse;
    private Exception exception;
    private List<Message> messageList;

    FutureMessageList () {
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
    public synchronized boolean isDone() {
        if (this.exception != null) {
            return true;
        }
        else if (this.messageList != null) {
            return true;
        }
        else if (this.httpResponse != null) {
            this.messageList = handleMultipleMessageHttpResponse(this.httpResponse);
            this.notifyAll();
            return true;
        }
        return false;
    }

    @Override
    public synchronized List<Message> get() throws InterruptedException, ExecutionException {
        while (true) {
            if (this.exception != null) {
                // Something went wrong and we have an exception.
                try {
                    throw this.exception;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (this.messageList != null) {
                // There is a message already here and processed.
                return this.messageList;
            } else if (this.httpResponse != null) {
                // We have been woken because there is a httpResponse and it has not been processed yet.
                try {
                    this.messageList = handleMultipleMessageHttpResponse(this.httpResponse);
                    this.notifyAll();
                    return this.messageList;
                    //TODO: Catch and Throw appropriate exceptions
                } catch (Exception e) {
                    this.exception = new ExecutionException(new RuntimeException("An error arose:" + e));
                    this.notifyAll();
                    try {
                        throw this.exception;
                    } catch (Exception ex) {
                        e.printStackTrace();
                    }
                }
            } else
                this.wait();
        }
    }

    @Override
    public synchronized List<Message> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
        //TODO: Support this get() call
    }

    public synchronized void setHttpResponse(HttpResponse response){
        httpResponse = response;
        this.notify();
    }

    public synchronized void setException(Exception e) {
        exception = e;
        this.notify();
    }
}

