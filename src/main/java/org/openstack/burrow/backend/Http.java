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

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
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
import org.openstack.burrow.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Http implements Backend {
  private String scheme = "http";
  private String host;
  private int port;
  private HttpClient client;

  public Http(String host, int port) {
    this.host = host;
    this.port = port;
    this.client = new DefaultHttpClient();
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
      // HttpPut request = new HttpPut(uri);
      HttpPut request = new HttpPut("");
      request.setEntity(new StringEntity(body, "UTF-8"));
      HttpResponse response = client.execute(request);
      StatusLine status = response.getStatusLine();
      switch (status.getStatusCode()) {
        case HttpStatus.SC_CREATED:
        case HttpStatus.SC_NO_CONTENT:
        if (1 != 1)
          return;
        else
          throw new RuntimeException();
        default:
          throw new RuntimeException();
          // TODO: Throw something
      }
    } catch (URISyntaxException e) {
      // Failed to construct the URI for this request.
      // TODO: Throw something
      e.printStackTrace();
      System.exit(1);
      throw new RuntimeException();
    } catch (UnsupportedEncodingException e) {
      // Thrown by the StringEntity constructor.
      // This should never happen under any circumstances because the UTF-8
      // encoding is chosen statically and should always be supported.
      // TODO: Throw something (DoesOnePlusOneEqualTwoInThisUniverse?)
      e.printStackTrace();
      System.exit(1);
      throw new RuntimeException();
    } catch (ClientProtocolException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      System.exit(1);
      throw new RuntimeException();
    } catch (IOException e) {
      // Thrown by client.execute()
      // TODO: Throw something
      e.printStackTrace();
      System.exit(1);
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
    /*
    List<NameValuePair> params = null;
    if ((marker != null) || (limit != null) || (detail != null)) {
      params = new ArrayList<NameValuePair>();
      if (marker != null)
        params.add(new BasicNameValuePair("marker", marker));
      if (limit != null)
        params.add(new BasicNameValuePair("limit", limit.toString()));
      if (detail != null)
        params.add(new BasicNameValuePair("detail", detail));
    }
    URI uri = null;
    try {
      uri = getUri(null, null, null, params);
    } catch (URISyntaxException e) {
      // Failed to construct a URI for the request.
      // This should never happen unless the configuration is completely bogus.
      // TODO: Raise an appropriate exception, perhaps inside getUri.
      e.printStackTrace();
    }
    HttpDelete request = new HttpDelete(uri);

    HttpResponse response = null;
    try {
      response = client.execute(request);
    } catch (IOException e) {
      // Failed to execute the http request.
      // TODO: Raise an appropriate exception
      e.printStackTrace();
    }
    StatusLine status = response.getStatusLine();
    HttpEntity responseEntity = response.getEntity();
    if (status.getStatusCode() == 200) {
      try {
        String responseBody = EntityUtils.toString(responseEntity, "UTF-8");
        JSONArray accountsJson = new JSONArray(responseBody);
        if (accountsJson.length() == 0)
          return Collections.emptyList();
        else {
          List<Account> accounts = new ArrayList<Account>(accountsJson.length());
          try {
            // Initially assume the response is a list of strings.
            for (int idx = 0; idx < accountsJson.length(); idx++) {
              // This getString will throw JSONException if we have objects.
              String name = accountsJson.getString(idx);
              accounts.add(idx, new AccountResponse(this, name));
            }

          } catch (JSONException e) {
            // The response is not a list of strings. Assume it is a list of
            // objects.
            for (int idx = 0; idx < accountsJson.length(); idx++) {
              JSONObject accountJson = accountsJson.getJSONObject(idx);
              try {
                AccountResponse account = new AccountResponse(this, accountJson);
                accounts.add(idx, account);
              } catch (JSONException e2) {
                // Failed to retrieve needed details from the JSON object.
                // TODO: Raise an appropriate exception
                e2.printStackTrace();
              }
            }
          }
        }
      } catch (IOException e) {
        // Failed to get a string from the responseEntity.
        // TODO: Re-raise appropriate exception
        e.printStackTrace();
      } catch (JSONException e) {
        // The response body could not be decoded as a JSON array,
        // or the array is not entirely of strings or entirely of objects,
        // or
        // TODO: Raise an appropriate exception
        e.printStackTrace();
      }
    }
    */
    return null;
  }

  /**
   * Delete a message with a known id.
   * 
   * @param account Delete a message in this account.
   * @param queue Delete a message in this queue.
   * @param messageId Delete a message with this id.
   * @param detail Optional. Return this level of detail about the deleted
   *          message.
   * @return A Message instance with the requested level of detail, or null if
   *         detail='none'.
   */
  public Message deleteMessage(String account, String queue, String messageId, Boolean matchHidden,
      String detail) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
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
  public Message getMessage(String account, String queue, String messageId, String detail) {
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
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
    return null; // To change body of implemented methods use File | Settings |
                 // File Templates.
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
