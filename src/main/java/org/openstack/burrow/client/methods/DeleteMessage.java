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

package org.openstack.burrow.client.methods;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.NoSuchMessageException;

public class DeleteMessage {
  private String account;
  private Backend backend;
  private String messageId;
  private String queue;

  public DeleteMessage(Backend backend, String account, String queue, String messageId) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.messageId = messageId;
  }

  public void execute() throws NoSuchMessageException {
    backend.deleteMessage(account, queue, messageId);
  }
}
