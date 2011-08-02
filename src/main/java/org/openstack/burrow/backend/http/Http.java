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
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.CommandException;
import org.openstack.burrow.backend.HttpProtocolException;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import java.io.IOException;
import java.util.List;

public class Http extends BaseHttp implements Backend {
  private HttpClient client;

  public Http(String host, int port) {
    super(host, port);
    this.client = new DefaultHttpClient();
  }

  @Override
  public Message execute(CreateMessage request) throws CommandException, HttpProtocolException {
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
  public List<Account> execute(DeleteAccounts request) throws CommandException, HttpProtocolException {
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
  public Message execute(DeleteMessage request) throws CommandException, HttpProtocolException {
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
  public List<Message> execute(DeleteMessages request) throws CommandException, HttpProtocolException {
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
  public List<Queue> execute(DeleteQueues request) throws CommandException, HttpProtocolException {
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
  public List<Account> execute(GetAccounts request) throws CommandException, HttpProtocolException {
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
  public Message execute(GetMessage request) throws CommandException, HttpProtocolException {
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
  public List<Message> execute(GetMessages request) throws CommandException, HttpProtocolException {
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
  public List<Queue> execute(GetQueues request) throws CommandException, HttpProtocolException {
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
  public Message execute(UpdateMessage request) throws CommandException, HttpProtocolException {
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
  public List<Message> execute(UpdateMessages request) throws CommandException, HttpProtocolException {
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
