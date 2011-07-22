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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.NoSuchMessageException;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.CreateMessage;
import org.openstack.burrow.client.methods.DeleteMessage;
import org.openstack.burrow.client.methods.DeleteMessages;
import org.openstack.burrow.client.methods.GetMessage;
import org.openstack.burrow.client.methods.GetMessages;
import org.openstack.burrow.client.methods.UpdateMessages;

public class Http implements Backend {
  private HttpClient client;
  private String host;
  private int port;
  private String scheme = "http";

  public Http(String host, int port) {
    this.host = host;
    this.port = port;
    this.client = new DefaultHttpClient();
  }

  /**
   * Delete accounts, including the associated queues and messages.
   * 
   * @param marker Optional. Only accounts with a name after this marker will be
   *          deleted.
   * @param limit Optional. Delete at most this many accounts.
   * @param detail Optional. Return the names of the accounts deleted.
   * @return A list of Account instances deleted, with the requested level of
   *         detail.
   */
  @Override
  public List<Account> deleteAccounts(String marker, Long limit, String detail) {
    return null;
  }

  /**
   * Delete queues, including associated messages.
   * 
   * @param account Delete queues in this account.
   * @param marker Optional. Only queues with a name after this marker will be
   *          deleted.
   * @param limit Optional. At most this many queues will be deleted.
   * @param detail Optional. If true, return the names of the queues deleted.
   * @return A list of Queue instances deleted, with the requested level of
   *         detail.
   */
  @Override
  public List<Queue> deleteQueues(String account, String marker, Long limit, String detail) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
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

  /**
   * List accounts.
   * 
   * @param marker Optional. Only accounts with a name after this marker will be
   *          returned.
   * @param limit Optional. Return at most this many accounts.
   * @return A list of Accounts.
   */
  @Override
  public List<Account> getAccounts(String marker, Long limit) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
  }

  /**
   * Construct a new HttpPut request for the CreateMessage action.
   * 
   * @param request
   * @return
   */
  private HttpPut getHttpRequest(CreateMessage request) {
    URI uri = getUri(request);
    HttpPut httpRequest = new HttpPut(uri);
    try {
      HttpEntity bodyEntity = new StringEntity(request.getBody(), "UTF-8");
      httpRequest.setEntity(bodyEntity);
    } catch (UnsupportedEncodingException e) {
      // This should be impossible for any legal String.
      throw new RuntimeException("Unable to create body HttpEntity: " + e);
    }
    return httpRequest;
  }

  private HttpDelete getHttpRequest(DeleteMessage request) {
    URI uri = getUri(request);
    HttpDelete httpRequest = new HttpDelete(uri);
    return httpRequest;
  }

  private HttpDelete getHttpRequest(DeleteMessages request) {
    URI uri = getUri(request);
    HttpDelete httpRequest = new HttpDelete(uri);
    return httpRequest;
  }

  private HttpGet getHttpRequest(GetMessage request) {
    URI uri = getUri(request);
    HttpGet httpRequest = new HttpGet(uri);
    return httpRequest;
  }

  private HttpGet getHttpRequest(GetMessages request) {
    URI uri = getUri(request);
    HttpGet httpRequest = new HttpGet(uri);
    return httpRequest;
  }

  private HttpPost getHttpRequest(UpdateMessages request) {
    URI uri = getUri(request);
    HttpPost httpRequest = new HttpPost(uri);
    return httpRequest;
  }

  private List<NameValuePair> getQueryParamaters(CreateMessage request) {
    Long ttl = request.getTtl();
    Long hide = request.getHide();
    if ((ttl != null) || (hide != null)) {
      List<NameValuePair> params = new ArrayList<NameValuePair>(2);
      if (ttl != null)
        params.add(new BasicNameValuePair("ttl", ttl.toString()));
      if (hide != null)
        params.add(new BasicNameValuePair("hide", hide.toString()));
      return params;
    } else {
      return null;
    }
  }

  private List<NameValuePair> getQueryParamaters(DeleteMessage request) {
    Boolean matchHidden = request.getMatchHidden();
    if (matchHidden != null) {
      List<NameValuePair> params = new ArrayList<NameValuePair>(1);
      params.add(new BasicNameValuePair("match_hidden", matchHidden.toString()));
      return params;
    } else {
      return null;
    }
  }

  private List<NameValuePair> getQueryParamaters(DeleteMessages request) {
    String marker = request.getMarker();
    Long limit = request.getLimit();
    Boolean matchHidden = request.getMatchHidden();
    String detail = request.getDetail();
    Long wait = request.getWait();
    if ((marker != null) || (limit != null) || (matchHidden != null) || (detail != null)
        || (wait != null)) {
      List<NameValuePair> params = new ArrayList<NameValuePair>(5);
      if (marker != null)
        params.add(new BasicNameValuePair("marker", marker));
      if (limit != null)
        params.add(new BasicNameValuePair("limit", limit.toString()));
      if (matchHidden != null)
        params.add(new BasicNameValuePair("match_hidden", matchHidden.toString()));
      if (detail != null)
        params.add(new BasicNameValuePair("detail", detail));
      if (wait != null)
        params.add(new BasicNameValuePair("wait", wait.toString()));
      return params;
    } else {
      return null;
    }
  }

  private List<NameValuePair> getQueryParamaters(GetMessage request) {
    Boolean matchHidden = request.getMatchHidden();
    String detail = request.getDetail();
    Long wait = request.getWait();
    if ((matchHidden != null) || (detail != null) || (wait != null)) {
      List<NameValuePair> params = new ArrayList<NameValuePair>(3);
      if (matchHidden != null)
        params.add(new BasicNameValuePair("match_hidden", matchHidden.toString()));
      if (detail != null)
        params.add(new BasicNameValuePair("detail", detail));
      if (wait != null)
        params.add(new BasicNameValuePair("wait", wait.toString()));
      return params;
    } else {
      return null;
    }
  }

  private List<NameValuePair> getQueryParamaters(GetMessages request) {
    String marker = request.getMarker();
    Long limit = request.getLimit();
    Boolean matchHidden = request.getMatchHidden();
    String detail = request.getDetail();
    Long wait = request.getWait();
    if ((marker != null) || (limit != null) || (matchHidden != null) || (detail != null)
        || (wait != null)) {
      List<NameValuePair> params = new ArrayList<NameValuePair>(5);
      if (marker != null)
        params.add(new BasicNameValuePair("marker", marker));
      if (limit != null)
        params.add(new BasicNameValuePair("limit", limit.toString()));
      if (matchHidden != null)
        params.add(new BasicNameValuePair("match_hidden", matchHidden.toString()));
      if (detail != null)
        params.add(new BasicNameValuePair("detail", detail));
      if (wait != null)
        params.add(new BasicNameValuePair("wait", wait.toString()));
      return params;
    } else {
      return null;
    }
  }

  private List<NameValuePair> getQueryParamaters(UpdateMessages request) {
    String marker = request.getMarker();
    Long limit = request.getLimit();
    Boolean matchHidden = request.getMatchHidden();
    Long ttl = request.getTtl();
    Long hide = request.getHide();
    String detail = request.getDetail();
    Long wait = request.getWait();
    if ((marker != null) || (limit != null) || (matchHidden != null) || (ttl != null)
        || (hide != null) || (detail != null) || (wait != null)) {
      List<NameValuePair> params = new ArrayList<NameValuePair>(7);
      if (marker != null)
        params.add(new BasicNameValuePair("marker", marker));
      if (limit != null)
        params.add(new BasicNameValuePair("limit", limit.toString()));
      if (matchHidden != null)
        params.add(new BasicNameValuePair("match_hidden", matchHidden.toString()));
      if (ttl != null)
        params.add(new BasicNameValuePair("ttl", ttl.toString()));
      if (hide != null)
        params.add(new BasicNameValuePair("hide", hide.toString()));
      if (detail != null)
        params.add(new BasicNameValuePair("detail", detail));
      if (wait != null)
        params.add(new BasicNameValuePair("wait", wait.toString()));
      return params;
    } else {
      return null;
    }
  }

  /**
   * List queues in an account.
   * 
   * @param account List queues in this account.
   * @param marker Optional. Only queues with a name after this marker will be
   *          listed.
   * @param limit Optional. At most this many queues will be listed.
   * @return A list of Queues.
   */
  @Override
  public List<Queue> getQueues(String account, String marker, Long limit) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
  }

  private URI getUri(CreateMessage request) {
    Queue queue = request.getQueue();
    Account account = queue.getAccount();
    try {
      return getUri(account.getId(), queue.getId(), request.getId(), getQueryParamaters(request));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Unable to build request URI: " + e);
    }
  }

  private URI getUri(DeleteMessage request) {
    Queue queue = request.getQueue();
    Account account = queue.getAccount();
    try {
      return getUri(account.getId(), queue.getId(), request.getId(), getQueryParamaters(request));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Unable to build request URI: " + e);
    }
  }

  private URI getUri(DeleteMessages request) {
    Queue queue = request.getQueue();
    Account account = queue.getAccount();
    try {
      return getUri(account.getId(), queue.getId(), null, getQueryParamaters(request));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Unable to build request URI: " + e);
    }
  }

  private URI getUri(GetMessage request) {
    Queue queue = request.getQueue();
    Account account = queue.getAccount();
    try {
      return getUri(account.getId(), queue.getId(), request.getId(), getQueryParamaters(request));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Unable to build request URI: " + e);
    }
  }

  private URI getUri(GetMessages request) {
    Queue queue = request.getQueue();
    Account account = queue.getAccount();
    try {
      return getUri(account.getId(), queue.getId(), null, getQueryParamaters(request));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Unable to build request URI: " + e);
    }
  }

  private URI getUri(String account, String queue, String message, List<NameValuePair> params)
      throws URISyntaxException {
    String path = "/v1.0";
    if (account != null)
      path += "/" + account;
    if (queue != null)
      path += "/" + queue;
    if (message != null)
      path += "/" + message;
    String encodedParams = null;
    if (params != null)
      encodedParams = URLEncodedUtils.format(params, "UTF-8");
    return URIUtils.createURI(scheme, host, port, path, encodedParams, null);
  }

  private URI getUri(UpdateMessages request) {
    Queue queue = request.getQueue();
    Account account = queue.getAccount();
    try {
      return getUri(account.getId(), queue.getId(), null, getQueryParamaters(request));
    } catch (URISyntaxException e) {
      throw new RuntimeException("Unable to build request URI: " + e);
    }
  }

  private List<Message> handleMultipleMessageHttpResponse(HttpResponse response) {
    StatusLine status = response.getStatusLine();
    HttpEntity entity = response.getEntity();
    if (entity == null)
      return null;
    String mimeType = EntityUtils.getContentMimeType(entity);
    switch (status.getStatusCode()) {
      case HttpStatus.SC_OK:
        if (mimeType.equals("application/json")) {
          try {
            String body = EntityUtils.toString(entity);
            JSONArray arrayJson = new JSONArray(body);
            List<Message> messages = new ArrayList<Message>(arrayJson.length());
            for (int idx = 0; idx < arrayJson.length(); idx++) {
              JSONObject messageJson = arrayJson.getJSONObject(idx);
              Message message = new MessageResponse(messageJson);
              messages.add(idx, message);
            }
            return messages;
          } catch (IOException e) {
            try {
              // Consume the entity to release HttpClient resources.
              EntityUtils.consume(entity);
            } catch (IOException e1) {
              // If we ever stop throwing an exception in the outside block,
              // this needs to be handled.
            }
            // TODO: Throw something appropriate.
            e.printStackTrace();
            throw new RuntimeException("IOException reading http response");
          } catch (JSONException e) {
            // It is not necessary to consume the entity because at the
            // first point where this exception can be thrown,
            // the entity has already been consumed by toString();
            // TODO: throw something appropriate.
            e.printStackTrace();
            throw new RuntimeException("JSONException reading response");
          }
        } else {
          // This situation cannot be handled.
          try {
            // Consume the entity to release HttpClient resources.
            EntityUtils.consume(entity);
          } catch (IOException e1) {
            // If we ever stop throwing an exception in the outside block,
            // this needs to be handled.
          }
          // TODO: Throw something appropriate.
          throw new RuntimeException("Non-Json response");
        }
      case HttpStatus.SC_NO_CONTENT:
        // This is not an error.
        try {
          // Consume the entity to release HttpClient resources.
          EntityUtils.consume(entity);
        } catch (IOException e) {
          // TODO: Throw something more appropriate.
          e.printStackTrace();
          throw new RuntimeException("Failed to consume HttpEntity");
        }
        return null;
      default:
        // This is probably an error.
        try {
          // Consume the entity to release HttpClient resources.
          EntityUtils.consume(entity);
        } catch (IOException e1) {
          // If we ever stop throwing an exception in the outside block,
          // this needs to be handled.
        }
        // TODO: Throw something appropriate.
        throw new RuntimeException("Unhandled http response code " + status.getStatusCode());
    }
  }

  private Message handleSingleMessageHttpResponse(HttpResponse response) {
    StatusLine status = response.getStatusLine();
    HttpEntity entity = response.getEntity();
    switch (status.getStatusCode()) {
      case HttpStatus.SC_OK:
      case HttpStatus.SC_CREATED:
        String responseMimeType = EntityUtils.getContentMimeType(entity);
        if (responseMimeType == null) {
          try {
            EntityUtils.consume(entity);
          } catch (IOException e) {
            // TODO: Throw something more appropriate.
            e.printStackTrace();
            throw new RuntimeException("Failed to consume HttpEntity");
          }
          return null;
        } else if (responseMimeType.equals("application/json")) {
          try {
            String body = EntityUtils.toString(entity);
            JSONObject messageJson = new JSONObject(body);
            return new MessageResponse(messageJson);
          } catch (JSONException e) {
            // At the first place this could be thrown,
            // the entity has already been consumed by toString(entity);
            // TODO: Throw something appropriate.
            e.printStackTrace();
            throw new RuntimeException("Failed to decode json");
          } catch (IOException e) {
            try {
              // Consume the entity to release HttpClient resources.
              EntityUtils.consume(entity);
            } catch (IOException e1) {
              // If we ever stop throwing an exception in the outside block,
              // this needs to be handled.
            }
            // TODO: Throw something appropriate.
            e.printStackTrace();
            throw new RuntimeException("Failed to convert the entity to a string");
          }
        } else {
          // We can't do anything sensible yet.
          try {
            // Consume the entity to release HttpClient resources.
            EntityUtils.consume(entity);
          } catch (IOException e) {
            // If we ever stop throwing an exception in the outside block,
            // this needs to be handled.
          }
          // TODO: Handle body-only responses.
          throw new RuntimeException("Unhandled response mime type " + responseMimeType);
        }
      case HttpStatus.SC_NO_CONTENT:
        try {
          EntityUtils.consume(entity);
        } catch (IOException e) {
          // TODO: Throw something more appropriate.
          e.printStackTrace();
          throw new RuntimeException("Failed to consume HttpEntity");
        }
        return null;
      case HttpStatus.SC_NOT_FOUND:
        try {
          // Consume the entity to release HttpClient resources.
          EntityUtils.consume(entity);
        } catch (IOException e) {
          // If we ever stop throwing an exception in the outside block,
          // this needs to be handled.
        }
        throw new NoSuchMessageException();
      default:
        // There was an error or an unexpected return condition.
        // TODO: Throw something more appropriate for each error condition.
        try {
          // Consume the entity to release HttpClient resources.
          EntityUtils.consume(entity);
        } catch (IOException e) {
          // If we ever stop throwing an exception in the outside block,
          // this needs to be handled.
        }
        throw new RuntimeException("Unhandled return code");
    }
  }

  /**
   * Update a message with a known id.
   * 
   * @param account Update a message in this account.
   * @param queue Update a message in this queue.
   * @param messageId Update a message with this id.
   * @param ttl Optional. Update the message to remain in the queue for up to
   *          this many seconds.
   * @param hide Optional. Update the message to be hidden for this many
   *          seconds.
   * @param detail Optional. Return this level of detail about the updated
   *          message.
   * @return An updated Message with the requested level of detail, or null if
   *         detail='none'.
   */
  @Override
  public Message updateMessage(String account, String queue, String messageId, Long ttl, Long hide,
      String detail) {
    try {
      List<NameValuePair> params = null;
      if ((ttl != null) || (hide != null) || (detail != null)) {
        params = new ArrayList<NameValuePair>(3);
        if (ttl != null)
          params.add(new BasicNameValuePair("ttl", ttl.toString()));
        if (hide != null)
          params.add(new BasicNameValuePair("hide", hide.toString()));
        if (detail != null)
          params.add(new BasicNameValuePair("detail", detail));
      }
      URI uri = getUri(account, queue, messageId, params);
      HttpPost request = new HttpPost(uri);
      HttpResponse response = client.execute(request);
      return handleSingleMessageHttpResponse(response);
    } catch (URISyntaxException e) {
      // Failed to construct the URI for this request.
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException("Failed to construct request URI " + e);
    } catch (ClientProtocolException e) {
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException("Failed to execute HttpRequest: ClientProtocolException " + e);
    } catch (IOException e) {
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException("Failed to execute HttpRequest: IOException " + e);
    }
  }
}
