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
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.QueueListRequest;

/**
 * QueueResponse extends Queue and provides the ability to handle response from the
 * server asynchronously
 */
class QueueResponse extends Queue {

    /**
     * Constructor for QueueResponse that takes an Account and a JSONObject as arguments
     * @param account The Account in which the Queue resides
     * @param queue   A JSONObject that contains the Queue name
     * @throws JSONException Arises if an error occurs while getting the Queue name
     */
  QueueResponse(Account account, JSONObject queue) throws JSONException {
    super(account, queue.getString("id"));
  }

    /**
     * Constructor for QueueResponse that takes a QueueListRequest and a JSONObject
     * as arguments
     * @param request A QueueListRequest object that contains the Account for the Queue
     * @param queue   A JSONObject that contains the Queue name
     * @throws JSONException Arises if an error occurs while getting the Queue name
     */
  QueueResponse(QueueListRequest request, JSONObject queue) throws JSONException {
    super(request.getAccount(), queue.getString("id"));
  }

    /**
     * Constructor for QueueResponse that takes an Account and a String as arguments
     * @param account The Account in which the Queue resides
     * @param id   A String that contains the Queue name
     */
  QueueResponse(Account account, String id) {
    super(account, id);
  }
  
    /**
     * Constructor for QueueResponse that takes a QueueListRequest and a String as
     * arguments
     * @param request A QueueListRequest object that contains the Account for the Queue
     * @param id   A String that contains the Queue name
     */
  QueueResponse(QueueListRequest request, String id) {
    super(request.getAccount(), id);
  }
}
