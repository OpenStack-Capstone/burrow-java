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

public class Queue {
  private String account;
  private Backend backend;
  private String queue;

  Queue(Backend backend, String account, String queue) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
  }

  public CreateMessage createMessage(String messageId, String body) {
    return new CreateMessage(backend, account, queue, messageId, body);
  }

  public DeleteMessage deleteMessage(String messageId) {
    return new DeleteMessage(backend, account, queue, messageId);
  }

  public DeleteMessages deleteMessages() {
    return new DeleteMessages(backend, account, queue);
  }

  public GetMessage getMessage(String messageId) {
    return new GetMessage(backend, account, queue, messageId);
  }

  public GetMessages getMessages() {
    return new GetMessages(backend, account, queue);
  }

  public UpdateMessage updateMessage(String messageId) {
    return new UpdateMessage(backend, account, queue, messageId);
  }

  public UpdateMessages updateMessages() {
    return new UpdateMessages(backend, account, queue);
  }
}
