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
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Queue;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureQueueList implements Future<List<Queue>> {
    private HttpResponse httpResponse;
    private Exception exception;
    private List<Queue> queueList;
    private Account account;

    FutureQueueList () {
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
        else if (this.queueList != null) {
            return true;
        }
        else if (this.httpResponse != null) {
            //TODO: Process response
            return true;
        }
        return false;
    }

    @Override
    public synchronized List<Queue> get() throws InterruptedException, ExecutionException {
       while (true) {
            if (this.exception != null) {
                // Something went wrong and we have an exception.
                try {
                    throw this.exception;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (this.queueList != null) {
                // There is a message already here and processed.
                return this.queueList;
            } else if (this.httpResponse != null) {
                // We have been woken because there is a httpResponse and it has not been processed yet.
                try {
                    //TODO: figure out how to set the class variable account
                    //this.queueList = handleMultipleQueueHttpResponse(this.account, this.httpResponse);
                    this.notifyAll();
                    return this.queueList;
                    //TODO: Catch and Throw appropriate exceptions
                } catch (Exception e) {
                    this.exception = new ExecutionException(new RuntimeException("An error arose:" + e));                    this.notifyAll();
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
    public synchronized List<Queue> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
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