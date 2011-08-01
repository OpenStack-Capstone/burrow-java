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

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.methods.DeleteAccounts;
import org.openstack.burrow.client.methods.GetAccounts;

public class Client {
  private Backend backend;

  public Client(Backend backend) {
    this.backend = backend;
  }

  public Account Account(String accountName) {
    return new Account(accountName);
  }

  public DeleteAccounts deleteAccounts() {
    return new DeleteAccounts(backend);
  }

  public GetAccounts getAccounts() {
    return new GetAccounts(backend);
  }
}
