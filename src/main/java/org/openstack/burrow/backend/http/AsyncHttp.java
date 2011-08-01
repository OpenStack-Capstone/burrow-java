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

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.concurrent.FutureCallback;
import org.apache.http.nio.reactor.IOReactorException;
import org.openstack.burrow.backend.AsyncBackend;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.CreateMessage;
import org.openstack.burrow.client.methods.DeleteAccounts;
import org.openstack.burrow.client.methods.DeleteMessage;
import org.openstack.burrow.client.methods.DeleteMessages;
import org.openstack.burrow.client.methods.DeleteQueues;
import org.openstack.burrow.client.methods.GetAccounts;
import org.openstack.burrow.client.methods.GetMessage;
import org.openstack.burrow.client.methods.GetMessages;
import org.openstack.burrow.client.methods.GetQueues;
import org.openstack.burrow.client.methods.UpdateMessage;
import org.openstack.burrow.client.methods.UpdateMessages;

public class AsyncHttp extends BaseHttp implements AsyncBackend {
  private HttpAsyncClient client;

  public AsyncHttp(String host, int port) throws IOReactorException {
    super(host, port);
    this.client = new DefaultHttpAsyncClient();
    // TODO: Move this into a Backend.start() method
    this.client.start();
  }

  @Override
  public Message execute(CreateMessage request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Account> execute(DeleteAccounts request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Message execute(DeleteMessage request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Message> execute(DeleteMessages request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Queue> execute(DeleteQueues request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Account> execute(GetAccounts request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Message execute(GetMessage request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Message> execute(GetMessages request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Queue> execute(GetQueues request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Message execute(UpdateMessage request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Message> execute(UpdateMessages request) {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Future<Message> executeAsync(CreateMessage request) {
    HttpPut httpRequest = getHttpRequest(request);
    final FutureMessage futureMessage = new FutureMessage();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessage.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessage.setException(e);
      }

    });
    return futureMessage;
  }

  @Override
  public Future<List<Account>> executeAsync(DeleteAccounts request) {
    HttpDelete httpRequest = getHttpRequest(request);
    final FutureAccountList futureAccountList = new FutureAccountList();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureAccountList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureAccountList.setException(e);
      }

    });
    return futureAccountList;
  }

  @Override
  public Future<Message> executeAsync(DeleteMessage request) {
    HttpDelete httpRequest = getHttpRequest(request);
    final FutureMessage futureMessage = new FutureMessage();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessage.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessage.setException(e);
      }

    });
    return futureMessage;
  }

  @Override
  public Future<List<Message>> executeAsync(DeleteMessages request) {
    HttpDelete httpRequest = getHttpRequest(request);
    final FutureMessageList futureMessageList = new FutureMessageList();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessageList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessageList.setException(e);
      }

    });
    return futureMessageList;
  }

  @Override
  public Future<List<Queue>> executeAsync(DeleteQueues request) {
    HttpDelete httpRequest = getHttpRequest(request);
    final FutureQueueList futureQueueList = new FutureQueueList(request.getAccount());
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureQueueList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureQueueList.setException(e);
      }

    });
    return futureQueueList;
  }

  @Override
  public Future<List<Account>> executeAsync(GetAccounts request) {
    HttpGet httpRequest = getHttpRequest(request);
    final FutureAccountList futureAccountList = new FutureAccountList();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureAccountList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureAccountList.setException(e);
      }

    });
    return futureAccountList;
  }

  @Override
  public Future<Message> executeAsync(GetMessage request) {
    HttpGet httpRequest = getHttpRequest(request);
    final FutureMessage futureMessage = new FutureMessage();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessage.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessage.setException(e);
      }

    });
    return futureMessage;
  }

  @Override
  public Future<List<Message>> executeAsync(GetMessages request) {
    HttpGet httpRequest = getHttpRequest(request);
    final FutureMessageList futureMessageList = new FutureMessageList();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessageList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessageList.setException(e);
      }

    });
    return futureMessageList;
  }

  @Override
  public Future<List<Queue>> executeAsync(GetQueues request) {
    HttpGet httpRequest = getHttpRequest(request);
    final FutureQueueList futureQueueList = new FutureQueueList(request.getAccount());
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureQueueList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureQueueList.setException(e);
      }

    });
    return futureQueueList;
  }

  @Override
  public Future<Message> executeAsync(UpdateMessage request) {
    HttpPost httpRequest = getHttpRequest(request);
    final FutureMessage futureMessage = new FutureMessage();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessage.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessage.setException(e);
      }

    });
    return futureMessage;
  }

  @Override
  public Future<List<Message>> executeAsync(UpdateMessages request) {
    HttpPost httpRequest = getHttpRequest(request);
    final FutureMessageList futureMessageList = new FutureMessageList();
    client.execute(httpRequest, new FutureCallback<HttpResponse>() {

      public void cancelled() {
        throw new RuntimeException("Should not have called .cancelled() on FutureCallBack");
      }

      public void completed(final HttpResponse response) {
        futureMessageList.setHttpResponse(response);
      }

      public void failed(final Exception e) {
        futureMessageList.setException(e);
      }

    });
    return futureMessageList;
  }
}
