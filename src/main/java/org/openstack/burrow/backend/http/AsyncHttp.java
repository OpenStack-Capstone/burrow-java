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

import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;
import org.openstack.burrow.backend.AsyncBackend;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttp implements AsyncBackend{
  private HttpAsyncClient client;
  private String host;
  private int port;
  private String scheme = "http";

  public AsyncHttp (String host, int port) {
    this.host = host;
    this.port = port;
    try {
      this.client = new DefaultHttpAsyncClient();
    } catch (IOReactorException e) {
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
      }
  }

  @Override
  public List<Account> deleteAccounts(String marker, Long limit, String detail) {
    return null;
  }

  @Override
  public List<Queue> deleteQueues(String account, String marker, Long limit, String detail) {
    return null;
  }

  @Override
  public List<Account> getAccounts(String marker, Long limit) {
    return null;
  }

  @Override
  public List<Queue> getQueues(String account, String marker, Long limit) {
    return null;
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
    return null;
  }

  @Override
  public Future<Message> executeAsync(DeleteMessage request) {
    return null;
  }

  @Override
  public Future<List<Message>> executeAsync(DeleteMessages request) {
    return null;
  }

  @Override
  public Future<Message> executeAsync(GetMessage request) {
    return null;
  }

  @Override
  public Future<List<Message>> executeAsync(GetMessages request) {
    return null;
  }

  @Override
  public Future<Message> executeAsync(UpdateMessage request) {
    return null;
  }

  @Override
  public Future<List<Message>> executeAsync(UpdateMessages request) {
    return null;
  }
}

