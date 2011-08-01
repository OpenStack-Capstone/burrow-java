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

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openstack.burrow.backend.Backend;
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

public class Http extends BaseHttp implements Backend {
  private HttpClient client;
  public Http(String host, int port) {
    super(host, port);
    this.client = new DefaultHttpClient();
  }

  @Override
  public Message execute(CreateMessage request) {
    HttpPut httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Account> execute(DeleteAccounts request) {
    HttpDelete httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleAccountHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Message execute(DeleteMessage request) {
    HttpDelete httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Message> execute(DeleteMessages request) {
    HttpDelete httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Queue> execute(DeleteQueues request) {
    HttpDelete httpRequest = getHttpRequest(request);
    Account account = request.getAccount();
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleQueueHttpResponse(account, response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Account> execute(GetAccounts request) {
    HttpGet httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleAccountHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Message execute(GetMessage request) {
    HttpGet httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Message> execute(GetMessages request) {
    HttpGet httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Queue> execute(GetQueues request) {
    HttpGet httpRequest = getHttpRequest(request);
    Account account = request.getAccount();
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleQueueHttpResponse(account, response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public Message execute(UpdateMessage request) {
    HttpPost httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }

  @Override
  public List<Message> execute(UpdateMessages request) {
    HttpPost httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something that isn't a RuntimeException
      e.printStackTrace();
      throw new RuntimeException("Error executing HTTP request: " + e);
    }
  }
}
