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

import org.json.JSONException;
import org.json.JSONObject;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.methods.MessageListRequest;
import org.openstack.burrow.client.methods.SingleMessageRequest;


/**
 * MessageResponse extends Message and provides the ability to handle responses from the
 * server asynchronously
 */
class MessageResponse extends Message {

    /**
     * Constructor for MessageResponse that takes a JSONObject as an argument
     * @param message A JSONObject that contains the id, body, ttl, and hide of the message
     * @throws JSONException Thrown if an issue occurs when parsing JSONObject
     */
  MessageResponse(JSONObject message) throws JSONException {
    setId(message.optString("id", null));
    setBody(message.optString("body", null));
    if (message.has("ttl"))
      setTtl(message.getLong("ttl"));
    if (message.has("hide"))
      setTtl(message.getLong("hide"));
  }

    /**
     * Constructor for MessageResponse that takes a JSONObject and a SingleMessageRequest
     * as arguments
     * @param request A SingleMessageRequest object
     * @param message A JSONObject that contains the id, body, ttl, and hide of the message
     * @throws JSONException Thrown if an issue occurs when parsing JSONObject
     */
  MessageResponse(SingleMessageRequest request, JSONObject message) throws JSONException {
    super(request);
    setId(message.optString("id", id));
    setBody(message.optString("body", body));
    if (message.has("ttl"))
      setTtl(message.getLong("ttl"));
    if (message.has("hide"))
      setTtl(message.getLong("hide"));
  }

    /**
     * Constructor for MessageResponse that takes a JSONObject and a MessageListRequest
     * as arguments
     * @param request A MessageListRequest object
     * @param message A JSONObject that contains the id, body, ttl, and hide of the message
     * @throws JSONException Thrown if an issue occurs when parsing JSONObject
     */
  MessageResponse(MessageListRequest request, JSONObject message) throws JSONException {
    super(request);
    setId(message.optString("id", null));
    setBody(message.optString("body", null));
    if (message.has("ttl"))
      setTtl(message.getLong("ttl"));
    if (message.has("hide"))
      setTtl(message.getLong("hide"));    
  }

    /**
     * Constructor for MessageResponse that takes a StringBuilder and a SingleMessageRequest
     * as arguments
     * @param request A SingleMessageRequest object
     * @param accumulator A StringBuilder that will hold response information
     */
  MessageResponse(SingleMessageRequest request, StringBuilder accumulator) {
    super(request);
    setBody(accumulator.toString());
  }
}
