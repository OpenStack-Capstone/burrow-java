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

package org.openstack.burrow.backend;

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
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.NoSuchMessageException;
import org.openstack.burrow.client.Queue;

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
   * Create a message with a given id.
   * 
   * @param account Create a message in this account.
   * @param queue Create a message in this queue.
   * @param messageId Create a message with this id.
   * @param body Create a message with this body.
   * @param ttl Optional. Create a message that will remain in the queue for up
   *          to this many seconds.
   * @param hide Optional. Create a message that is hidden for this many
   *          seconds.
   */
  public void createMessage(String account, String queue, String messageId, String body, Long ttl,
      Long hide) {
    try {
      List<NameValuePair> params = null;
      if ((ttl != null) || (hide != null)) {
        params = new ArrayList<NameValuePair>(2);
        if (ttl != null)
          params.add(new BasicNameValuePair("ttl", ttl.toString()));
        if (hide != null)
          params.add(new BasicNameValuePair("hide", hide.toString()));
      }
      URI uri = getUri(account, queue, messageId, params);
      HttpPut request = new HttpPut(uri);
      request.setEntity(new StringEntity(body, "UTF-8"));
      HttpResponse response = client.execute(request);
      StatusLine status = response.getStatusLine();
      switch (status.getStatusCode()) {
        case HttpStatus.SC_CREATED:
        case HttpStatus.SC_NO_CONTENT:
          return;
        default:
          // TODO: Throw something
          System.exit(1);
      }
    } catch (URISyntaxException e) {
      // Failed to construct the URI for this request.
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (UnsupportedEncodingException e) {
      // Thrown by the StringEntity constructor.
      // This should never happen under any circumstances because the UTF-8
      // encoding is chosen statically and should always be supported.
      // TODO: Throw something (DoesOnePlusOneEqualTwoInThisUniverse?)
      e.printStackTrace();
      throw new RuntimeException();
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    }
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
  public List<Account> deleteAccounts(String marker, Long limit, String detail) {
    return null;
  }

  /**
   * Delete a message with a known id.
   * 
   * @param account Delete a message in this account.
   * @param queue Delete a message in this queue.
   * @param messageId Delete a message with this id.
   */
  public void deleteMessage(String account, String queue, String messageId) {
    try {
      URI uri = getUri(account, queue, messageId, null);
      HttpDelete request = new HttpDelete(uri);
      HttpResponse response = client.execute(request);
      StatusLine status = response.getStatusLine();
      switch (status.getStatusCode()) {
        case HttpStatus.SC_NO_CONTENT:
          return;
        case HttpStatus.SC_NOT_FOUND:
          throw new NoSuchMessageException();
        default:
          // TODO: Throw something more appropriate
          throw new RuntimeException();
      }
    } catch (URISyntaxException e) {
      // Failed to construct the URI for this request.
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  /**
   * Delete messages in a queue.
   * 
   * @param account Delete messages in this account.
   * @param queue Delete messages in this queue.
   * @param marker Optional. Delete messages with ids after this marker.
   * @param limit Optional. Delete at most this many messages.
   * @param matchHidden Optional. Delete messages that are hidden.
   * @param detail Optional. Return this level of detail about the deleted
   *          messages.
   * @param wait Optional. Wait up to this many seconds to delete a message if
   *          none would otherwise be deleted.
   * @return A list of Message instances with the requested level of detail, or
   *         null if detail='none'.
   */
  public List<Message> deleteMessages(String account, String queue, String marker, Long limit,
      Boolean matchHidden, String detail, Long wait) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
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
  public List<Queue> deleteQueues(String account, String marker, Long limit, String detail) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
  }

  /**
   * List accounts.
   * 
   * @param marker Optional. Only accounts with a name after this marker will be
   *          returned.
   * @param limit Optional. Return at most this many accounts.
   * @return A list of Accounts.
   */
  public List<Account> getAccounts(String marker, Long limit) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
  }

  /**
   * Get a message with a known id.
   * 
   * @param account Get a message from this account.
   * @param queue Get a message from this queue.
   * @param messageId Get a message with this id.
   * @param detail Return this level of detail about the message.
   * @return A Message instance with the requested level of detail, or null if
   *         detail='none'.
   */
  public Message getMessage(String account, String queue, String messageId, String detail)
      throws NoSuchMessageException {
    try {
      List<NameValuePair> params = null;
      if (detail != null) {
        params = new ArrayList<NameValuePair>(1);
        params.add(new BasicNameValuePair("detail", detail));
      }
      URI uri = getUri(account, queue, messageId, params);
      HttpGet request = new HttpGet(uri);
      HttpResponse response = client.execute(request);
      StatusLine status = response.getStatusLine();
      switch (status.getStatusCode()) {
        case HttpStatus.SC_OK:
          HttpEntity responseEntity = response.getEntity();
          String responseMimeType = EntityUtils.getContentMimeType(responseEntity);
          if (responseMimeType.equals("application/json")) {
            String responseBody = EntityUtils.toString(responseEntity);
            JSONObject messageJson = new JSONObject(responseBody);
            return new MessageResponse(messageJson);
          } else {
            // TODO: Handle body-only responses.
            throw new RuntimeException();
          }
        case HttpStatus.SC_NOT_FOUND:
          throw new NoSuchMessageException();
        default:
          // TODO: Throw something
          throw new RuntimeException();
      }
    } catch (URISyntaxException e) {
      // Failed to construct the URI for this request.
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (JSONException e) {
      // Thrown by creating the JSONObject from the response body,
      // or potentially by creating the MessageResponse from the json.
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  /**
   * Get messages from a queue.
   * 
   * @param account Get messages in this account.
   * @param queue Get messages in this queue.
   * @param marker Optional. Get messages with ids after this marker.
   * @param limit Optional. Get at most this many messages.
   * @param matchHidden Optional. Get messages that are hidden.
   * @param detail Optional. Return this level of detail for the messages.
   * @param wait Optional. Wait up to this many seconds to get a message if none
   *          would otherwise be returned.
   * @return A list of Message instances with the requested level of detail.
   */
  public List<Message> getMessages(String account, String queue, String marker, Long limit,
      Boolean matchHidden, String detail, Long wait) {
    try {
      List<NameValuePair> params = null;
      if ((marker != null) || (limit != null) || (matchHidden != null) || (detail != null)
          || (wait != null)) {
        params = new ArrayList<NameValuePair>(5);
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
      }
      URI uri = getUri(account, queue, null, params);
      HttpGet request = new HttpGet(uri);
      HttpResponse response = client.execute(request);
      StatusLine status = response.getStatusLine();
      switch (status.getStatusCode()) {
        case HttpStatus.SC_OK:
          HttpEntity responseEntity = response.getEntity();
          String responseMimeType = EntityUtils.getContentMimeType(responseEntity);
          if (responseMimeType.equals("application/json")) {
            String responseBody = EntityUtils.toString(responseEntity);
            JSONArray responseJson = new JSONArray(responseBody);
            List<Message> messages = new ArrayList<Message>(responseJson.length());
            for (int idx = 0; idx < responseJson.length(); idx++) {
              JSONObject messageJson = responseJson.getJSONObject(idx);
              Message message = new MessageResponse(messageJson);
              messages.add(idx, message);
            }
            return messages;
          } else {
            // TODO: Throw something. We *can't* handle this, ever.
            throw new RuntimeException();
          }
        default:
          // TODO: Throw something!
          throw new RuntimeException();
      }
    } catch (URISyntaxException e) {
      // Failed to construct the URI for this request.
      // TODO: Throw something
      e.printStackTrace();
      throw new RuntimeException();
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      throw new RuntimeException();
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
  public List<Queue> getQueues(String account, String marker, Long limit) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
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
  public Message updateMessage(String account, String queue, String messageId, Long ttl, Long hide,
      String detail) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
  }

  /**
   * Update messages in a queue.
   * 
   * @param account Update messages in this account.
   * @param queue Update messages in this queue.
   * @param marker Optional. Update messages with ids after this marker.
   * @param limit Optional. Update at most this many messages.
   * @param matchHidden Optional. Update messages that are hidden.
   * @param ttl Optional. Update messages to remain in the queue for up to this
   *          many seconds.
   * @param hide Optional. Update messages to be hidden this many seconds.
   * @param detail Optional. Return this level of detail for the updated
   *          messages.
   * @param wait Optional. Wait up to this many seconds to update a message if
   *          none would otherwise be updated.
   * @return A list of updated Message instances with the requested level of
   *         detail, or null if detail='none'.
   */
  public List<Message> updateMessages(String account, String queue, String marker, Long limit,
      Boolean matchHidden, Long ttl, Long hide, String detail, Long wait) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
  }
}
