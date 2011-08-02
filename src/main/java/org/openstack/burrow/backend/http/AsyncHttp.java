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

import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.client.methods.HttpAsyncDelete;
import org.apache.http.nio.client.methods.HttpAsyncGet;
import org.apache.http.nio.client.methods.HttpAsyncPost;
import org.apache.http.nio.client.methods.HttpAsyncPut;
import org.apache.http.nio.reactor.IOReactorException;
import org.openstack.burrow.backend.AsyncBackend;
import org.openstack.burrow.backend.BurrowRuntimeException;
import org.openstack.burrow.backend.CommandException;
import org.openstack.burrow.backend.ProtocolException;
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
  public Message execute(CreateMessage request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Account> execute(DeleteAccounts request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public Message execute(DeleteMessage request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Message> execute(DeleteMessages request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Queue> execute(DeleteQueues request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Account> execute(GetAccounts request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public Message execute(GetMessage request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Message> execute(GetMessages request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Queue> execute(GetQueues request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public Message execute(UpdateMessage request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public List<Message> execute(UpdateMessages request) throws CommandException, ProtocolException {
    try {
      return executeAsync(request).get();
    } catch (InterruptedException e) {
      throw new BurrowRuntimeException("InterruptedException executing HTTP request", e);
    } catch (ExecutionException e) {
      Throwable t = e.getCause();
      if (t instanceof CommandException)
        throw (CommandException) t;
      if (t instanceof ProtocolException)
        throw (ProtocolException) t;
      throw new BurrowRuntimeException("ExecutionException executing HTTP request", e);
    }
  }

  @Override
  public Future<Message> executeAsync(CreateMessage request) {
    URI uri = getUri(request);
    HttpAsyncPut httpRequest =
        new HttpAsyncPut(uri, request.getBody(), "application/json", "UTF-8");
    return client.execute(httpRequest, new SingleMessageResponseConsumer(request), null);
  }

  @Override
  public Future<List<Account>> executeAsync(DeleteAccounts request) {
    URI uri = getUri(request);
    HttpAsyncDelete httpRequest = new HttpAsyncDelete(uri);
    return client.execute(httpRequest, new AccountListResponseConsumer(request), null);
  }

  @Override
  public Future<Message> executeAsync(DeleteMessage request) {
    URI uri = getUri(request);
    HttpAsyncDelete httpRequest = new HttpAsyncDelete(uri);
    return client.execute(httpRequest, new SingleMessageResponseConsumer(request), null);
  }

  @Override
  public Future<List<Message>> executeAsync(DeleteMessages request) {
    URI uri = getUri(request);
    HttpAsyncDelete httpRequest = new HttpAsyncDelete(uri);
    return client.execute(httpRequest, new MessageListResponseConsumer(request), null);
  }

  @Override
  public Future<List<Queue>> executeAsync(DeleteQueues request) {
    URI uri = getUri(request);
    HttpAsyncDelete httpRequest = new HttpAsyncDelete(uri);
    return client.execute(httpRequest, new QueueListResponseConsumer(request), null);
  }

  @Override
  public Future<List<Account>> executeAsync(GetAccounts request) {
    URI uri = getUri(request);
    HttpAsyncGet httpRequest = new HttpAsyncGet(uri);
    return client.execute(httpRequest, new AccountListResponseConsumer(request), null);
  }

  @Override
  public Future<Message> executeAsync(GetMessage request) {
    URI uri = getUri(request);
    HttpAsyncGet httpRequest = new HttpAsyncGet(uri);
    return client.execute(httpRequest, new SingleMessageResponseConsumer(request), null);
  }

  @Override
  public Future<List<Message>> executeAsync(GetMessages request) {
    URI uri = getUri(request);
    HttpAsyncGet httpRequest = new HttpAsyncGet(uri);
    return client.execute(httpRequest, new MessageListResponseConsumer(request), null);
  }

  @Override
  public Future<List<Queue>> executeAsync(GetQueues request) {
    URI uri = getUri(request);
    HttpAsyncGet httpRequest = new HttpAsyncGet(uri);
    return client.execute(httpRequest, new QueueListResponseConsumer(request), null);
  }

  @Override
  public Future<Message> executeAsync(UpdateMessage request) {
    URI uri = getUri(request);
    HttpAsyncPost httpRequest = new HttpAsyncPost(uri, "");
    return client.execute(httpRequest, new SingleMessageResponseConsumer(request), null);
  }

  @Override
  public Future<List<Message>> executeAsync(UpdateMessages request) {
    URI uri = getUri(request);
    HttpAsyncPost httpRequest = new HttpAsyncPost(uri, "");
    return client.execute(httpRequest, new MessageListResponseConsumer(request), null);
  }
}
