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
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.NoSuchQueueException;

public class MessageListResponseConsumer extends AsyncCharConsumer<List<Message>> {
  private StringBuilder accumulator = null;
  private Exception exception = null;
  private String mimeType = null;

  @Override
  protected List<Message> buildResult() throws Exception {
    if (exception != null)
      throw exception;
    else if (accumulator == null) {
      // It was not an error condition but we do not care about the response
      // body.
      return null;
    } else if (mimeType.equals("application/json")) {
      JSONArray messagesJson = new JSONArray(accumulator.toString());
      List<Message> messages = new ArrayList<Message>(messagesJson.length());
      for (int idx = 0; idx < messagesJson.length(); idx++) {
        JSONObject messageJson = messagesJson.getJSONObject(idx);
        Message message = new MessageResponse(messageJson);
        messages.add(idx, message);
      }
      return messages;
    } else
      throw new RuntimeException("Unhandled circumstance in MessageListResponseConsumer; mimeType="
          + mimeType + ", acc=" + accumulator);
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
        if (mimeType != null) {
          if (mimeType.equals("application/json"))
            accumulator = new StringBuilder();
          else
            exception = new RuntimeException("Unhandled response mime type: " + mimeType);
        }
        return;
      case HttpStatus.SC_NO_CONTENT:
        // This is not an error condition, but we do not care about the body
        // and thus do not set up the accumulator.
        return;
      case HttpStatus.SC_NOT_FOUND:
        // This is an error condition, and we do not care about the body.
        exception = new NoSuchQueueException();
        return;
      default:
        // This is an error condition.
        exception = new RuntimeException("Unhandled response status code: " + statusCode);
        return;
    }
  }
}
