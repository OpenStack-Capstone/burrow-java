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

/**
 * Http extends BaseHttp and provides a synchronous service to send request to and receive
 * responses from the server
 */
public class Http extends BaseHttp implements Backend {
  private HttpClient client;

    /**
     * Constructor for Http that takes a host name and port number as arguments
     * @param host A host name as a String
     * @param port A port number as an int
     */
  public Http(String host, int port) {
    super(host, port);
    this.client = new DefaultHttpClient();
  }

    /**
     * Executes a CreateMessage request synchronously
     * @param request The CreateMessage request object to execute
     * @return        The Message object requested
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public Message execute(CreateMessage request) throws CommandException, HttpProtocolException {
    HttpPut httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a DeleteAccounts request synchronously
     * @param request The DeleteAccounts request object to execute
     * @return        The List of Accounts object requested
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Account> execute(DeleteAccounts request) throws CommandException, HttpProtocolException {
    HttpDelete httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleAccountHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a DeleteMessage request synchronously
     * @param request The DeleteMessage request object to execute
     * @return        A Message object
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public Message execute(DeleteMessage request) throws CommandException, HttpProtocolException {
    HttpDelete httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a DeleteMessages request synchronously
     * @param request The DeleteMessages request object to execute
     * @return        A List of Messages
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Message> execute(DeleteMessages request) throws CommandException, HttpProtocolException {
    HttpDelete httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a DeleteQueues request synchronously
     * @param request The DeleteQueues request object to execute
     * @return        A List of Queues
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Queue> execute(DeleteQueues request) throws CommandException, HttpProtocolException {
    HttpDelete httpRequest = getHttpRequest(request);
    Account account = request.getAccount();
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleQueueHttpResponse(account, response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a GetAccounts request synchronously
     * @param request The GetAccounts request object to execute
     * @return        A List of Accounts
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Account> execute(GetAccounts request) throws CommandException, HttpProtocolException {
    HttpGet httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleAccountHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a GetMessage request synchronously
     * @param request The GetMessage request object to execute
     * @return        A Message object
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public Message execute(GetMessage request) throws CommandException, HttpProtocolException {
    HttpGet httpRequest = null;
    HttpPost httpPostRequest = null;
    if (request.getHide() != null) {
        httpPostRequest = getHttpPostRequest(request);
    } else {
        httpRequest = getHttpRequest(request);
    }
    try {
      HttpResponse response = null;
      if (httpPostRequest != null) {
          response = client.execute(httpPostRequest);
      } else {
        response = client.execute(httpRequest);
      }
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a GetMessages request synchronously
     * @param request The GetMessages request object to execute
     * @return        A List of Messages
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Message> execute(GetMessages request) throws CommandException, HttpProtocolException {
    HttpGet httpRequest = null;
    HttpPost httpPostRequest = null;
    if (request.getHide() != null) {
        httpPostRequest = getHttpPostRequest(request);
    } else {
        httpRequest = getHttpRequest(request);
    }
    try {
      HttpResponse response = null;
      if (httpPostRequest != null) {
          response = client.execute(httpPostRequest);
      } else {
        response = client.execute(httpRequest);
      }
      return handleMultipleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a GetQueues request synchronously
     * @param request The GetQueues request object to execute
     * @return        A List of Queues
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Queue> execute(GetQueues request) throws CommandException, HttpProtocolException {
    HttpGet httpRequest = getHttpRequest(request);
    Account account = request.getAccount();
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleQueueHttpResponse(account, response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a UpdateMessage request synchronously
     * @param request The UpdateMessage request object to execute
     * @return        A Message object
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public Message execute(UpdateMessage request) throws CommandException, HttpProtocolException {
    HttpPost httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleSingleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }

    /**
     * Executes a UpdateMessages request synchronously
     * @param request The UpdateMessages request object to execute
     * @return        A List of Message objects
     * @throws CommandException  Thrown if cannot process the request
     * @throws HttpProtocolException  Thrown if an error occurs while executing the Http
     *                                request
     */
  @Override
  public List<Message> execute(UpdateMessages request) throws CommandException, HttpProtocolException {
    HttpPost httpRequest = getHttpRequest(request);
    try {
      HttpResponse response = client.execute(httpRequest);
      return handleMultipleMessageHttpResponse(response);
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    } catch (IOException e) {
      // Thrown by client.execute()
      throw new HttpProtocolException("Error executing HTTP request: " + e);
    }
  }
}
