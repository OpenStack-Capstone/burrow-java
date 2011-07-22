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
import org.openstack.burrow.client.methods.CreateMessage;
import org.openstack.burrow.client.methods.DeleteMessage;
import org.openstack.burrow.client.methods.DeleteMessages;
import org.openstack.burrow.client.methods.GetMessage;
import org.openstack.burrow.client.methods.GetMessages;
import org.openstack.burrow.client.methods.UpdateMessage;
import org.openstack.burrow.client.methods.UpdateMessages;

public class Queue {
  private Account account;
  private Backend backend;
  private String id;

  public Queue(Backend backend, Account account, String queue) {
    this.backend = backend;
    this.account = account;
    this.id = queue;
  }

  public Queue(Backend backend, String account, String id) {
    this.backend = backend;
    this.account = new Account(backend, account);
    this.id = id;
  }

  public CreateMessage createMessage(String messageId, String body) {
    return new CreateMessage(this, messageId, body);
  }

  public DeleteMessage deleteMessage(String messageId) {
    return new DeleteMessage(this, messageId);
  }

  public DeleteMessages deleteMessages() {
    return new DeleteMessages(this);
  }

  public Account getAccount() {
    return account;
  }

  public String getId() {
    return id;
  }

  public GetMessage getMessage(String messageId) {
    return new GetMessage(backend, account.getId(), id, messageId);
  }

  public GetMessages getMessages() {
    return new GetMessages(backend, account.getId(), id);
  }

  public UpdateMessage updateMessage(String messageId) {
    return new UpdateMessage(backend, account.getId(), id, messageId);
  }

  public UpdateMessages updateMessages() {
    return new UpdateMessages(backend, account.getId(), id);
  }
}
