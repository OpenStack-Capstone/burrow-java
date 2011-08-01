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
import org.openstack.burrow.client.methods.DeleteQueues;
import org.openstack.burrow.client.methods.GetQueues;

public class Account {
  private Backend backend;
  private String id;

  public Account(Backend backend, String account) {
    this.backend = backend;
    this.id = account;
  }

  public DeleteQueues deleteQueues() {
    return new DeleteQueues(backend, id);
  }

  public String getId() {
    return id;
  }

  public GetQueues getQueues() {
    return new GetQueues(this);
  }

  public Queue Queue(String queue) {
    return new Queue(this, queue);
  }
}
