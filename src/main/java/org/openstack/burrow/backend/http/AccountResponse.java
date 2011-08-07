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

/**
 * AccountResponse extends Account and provides the ability to handle response from the
 * server asynchronously
 */
class AccountResponse extends Account {

    /**
     * Constructor for AccountResponse that takes an account id as an argument
     * @param accountJson A JSONObject that contains an account id
     * @throws JSONException Thrown if an error is encountered when getting the id from
     *                       the JSONObject
     */
  AccountResponse(JSONObject accountJson) throws JSONException {
    super(accountJson.getString("id"));
  }

    /**
     * Constructor for AccountResponse that takes an account id as an argument
     * @param account A String that contains an account id
     */
  AccountResponse(String account) {
    super(account);
  }
}
