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

package org.openstack.burrow.client;

import org.openstack.burrow.client.methods.DeleteAccounts;
import org.openstack.burrow.client.methods.GetAccounts;

/**
 * A Client can create Accounts and can either get Accounts for that
 * Client or delete Accounts for that Client
 */
public class Client {

    /**
     * Constructor for Client which takes no arguments
     */
  public Client() {
  }

    /**
     * Takes in an accountName and returns a new Account object with that name
     * @param accountName The name of the Account for the Client as a String
     * @return A new Account with the passed in accountName
     */
  public Account Account(String accountName) {
    return new Account(accountName);
  }

    /**
     * Takes no arguments and returns a new DeleteAccounts object
     * @return A DeleteAccounts request object
     */
  public DeleteAccounts deleteAccounts() {
    return new DeleteAccounts();
  }

    /**
     * Takes no arguments and returns a new GetAccounts object
     * @return A GetAccounts request object
     */
  public GetAccounts getAccounts() {
    return new GetAccounts();
  }
}
