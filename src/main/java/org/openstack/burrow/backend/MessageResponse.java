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

import org.json.JSONException;
import org.json.JSONObject;
import org.openstack.burrow.client.Message;

class MessageResponse extends Message {

  MessageResponse(JSONObject message) throws JSONException {
    super();
    setId(message.optString("id", null));
    setBody(message.optString("body", null));
    if (message.has("ttl"))
      setTtl(message.getLong("ttl"));
    if (message.has("hide"))
      setTtl(message.getLong("hide"));
  }
}
