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

import org.openstack.burrow.client.methods.DeleteQueues;
import org.openstack.burrow.client.methods.GetQueues;

/**
 * An Account contains an id and can create create, get, and delete Queues from the
 * account.
 */
public class Account {
  private String id;

    /**
     * Constructor for Account that takes an account Id
     * @param account A String that is a
     */
  public Account(String account) {
    this.id = account;
  }

    /**
     * Takes no arguments and returns a DeleteQueues object
     * @return A DeleteQueues request object
     */
  public DeleteQueues deleteQueues() {
    return new DeleteQueues(this);
  }

    /**
     * A getter function that will return the Id of the Account
     * @return The Id of the Account as a String
     */
  public String getId() {
    return id;
  }

    /**
     * Takes no arguments and creates a GetQueues object
     * @return A GetQueues request object
     */
  public GetQueues getQueues() {
    return new GetQueues(this);
  }

    /**
     * Creates a new Queue with the passed in queue name
     * @param queue A String of the name of the Queue to be created
     * @return A Queue object with the passed in name
     */
  public Queue Queue(String queue) {
    return new Queue(this, queue);
  }
}
