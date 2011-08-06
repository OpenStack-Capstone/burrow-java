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
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openstack.burrow.backend.AccountNotFoundException;
import org.openstack.burrow.backend.BurrowRuntimeException;
import org.openstack.burrow.backend.HttpProtocolException;
import org.openstack.burrow.backend.ProtocolException;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.methods.AccountListRequest;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class AccountListResponseConsumer extends AsyncCharConsumer<List<Account>> {
  private StringBuilder accumulator = null;
  private Exception exception = null;
  private String mimeType = null;

  AccountListResponseConsumer(AccountListRequest request) {
    super();
  }

  @Override
  protected List<Account> buildResult() throws Exception {
    if (exception != null) {
      throw exception;
    } else if (accumulator == null) {
      // It was not an error condition but we do not care about the response
      // body.
      return null;
    } else if ("application/json".equals(mimeType)) {
      try {
        JSONArray accountsJson = new JSONArray(accumulator.toString());
        List<Account> accounts = new ArrayList<Account>(accountsJson.length());
        try {
          // Assume the array is an array of objects.
          for (int idx = 0; idx < accountsJson.length(); idx++) {
            JSONObject accountJson = accountsJson.getJSONObject(idx);
            Account account = new AccountResponse(accountJson);
            accounts.add(idx, account);
          }
        } catch (JSONException e) {
          // The array was not an array of objects. Try again assuming an array
          // of strings.
          for (int idx = 0; idx < accountsJson.length(); idx++) {
            String accountId = accountsJson.getString(idx);
            Account account = new AccountResponse(accountId);
            accounts.add(idx, account);
          }
        }
        return accounts;
      } catch (JSONException e) {
        throw new ProtocolException("Unable to parse server response", e);
      }
    } else {
      throw new BurrowRuntimeException(
          "Unhandled circumstance in AccountListResponseConsumer; mimeType=" + mimeType + ", acc="
              + accumulator);
    }
  }

  @Override
  protected void onCharReceived(CharBuffer buf, IOControl ioctrl) throws IOException {
    if (accumulator != null)
      accumulator.append(buf);
  }

  @Override
  protected void onCleanup() {
    this.accumulator = null;
  }

  @Override
  protected void onResponseReceived(HttpResponse response) {
    StatusLine status = response.getStatusLine();
    int statusCode = status.getStatusCode();
    switch (statusCode) {
      case HttpStatus.SC_OK:
        mimeType = EntityUtils.getContentMimeType(response.getEntity());
        if ("application/json".equals(mimeType)) {
          accumulator = new StringBuilder();
        } else if (mimeType != null) {
          exception = new HttpProtocolException("Unhandled response mime type: " + mimeType);
        }
        return;
      case HttpStatus.SC_NO_CONTENT:
        // This is not an error condition, but we do not care about the body
        // and thus do not set up the accumulator.
        return;
      case HttpStatus.SC_NOT_FOUND:
        // This is an error condition, and we do not care about the body.
        exception = new AccountNotFoundException("No accounts exist on the server");
        return;
      default:
        // This is an error condition.
        exception = new HttpProtocolException("Unhandled response status code: " + statusCode);
        return;
    }
  }
}