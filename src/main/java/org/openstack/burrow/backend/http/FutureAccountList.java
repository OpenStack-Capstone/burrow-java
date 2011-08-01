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

import static org.openstack.burrow.backend.http.BaseHttp.handleMultipleAccountHttpResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.openstack.burrow.client.Account;

public class FutureAccountList implements Future<List<Account>> {
  private List<Account> accountList;
  private Exception exception;
  private HttpResponse httpResponse;

  FutureAccountList() {
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return true;
  }

  @Override
  public synchronized List<Account> get() throws InterruptedException, ExecutionException {
    while (true) {
      if (this.exception != null) {
        // Something went wrong and we have an exception.
        if (exception instanceof ExecutionException)
          throw (ExecutionException) exception;
        else if (exception instanceof InterruptedException)
          throw (InterruptedException) exception;
        else
          throw new RuntimeException("Unexpected Exception Type: " + exception);
      } else if (this.accountList != null) {
        // There is a message already here and processed.
        return this.accountList;
      } else if (this.httpResponse != null) {
        // We have been woken because there is a httpResponse and it has not
        // been processed yet.
        // try {
        this.accountList = handleMultipleAccountHttpResponse(this.httpResponse);
        this.notifyAll();
        return this.accountList;
        // } catch (Exception e) {
        // // TODO: Catch and Throw appropriate exceptions
        // this.exception = new ExecutionException(new RuntimeException(e));
        // this.notifyAll();
        // throw (ExecutionException) this.exception;
        // }
      } else
        this.wait();
    }
  }

  @Override
  public synchronized List<Account> get(long timeout, TimeUnit unit) throws InterruptedException,
      ExecutionException, TimeoutException {
    return null;
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public synchronized boolean isDone() {
    if (this.exception != null) {
      return true;
    } else if (this.accountList != null) {
      return true;
    } else if (this.httpResponse != null) {
      return true;
    }
    return false;
  }

  public synchronized void setException(Exception e) {
    exception = new ExecutionException(e);
    this.notifyAll();
  }

  public synchronized void setHttpResponse(HttpResponse response) {
    httpResponse = response;
    this.notify();
  }
}