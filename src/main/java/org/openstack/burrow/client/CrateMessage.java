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

public class CreateMessage {
  private String account;
  private Backend backend;
  private String body;
  private Long hide;
  private String messageId;
  private String queue;
  private Long ttl;

  CreateMessage(Backend backend, String account, String queue, String messageId, String body) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.messageId = messageId;
    this.body = body;
    this.ttl = null;
    this.hide = null;
  }

  private CreateMessage(Backend backend, String account, String queue, String messageId,
      String body, Long ttl, Long hide) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.messageId = messageId;
    this.body = body;
    this.ttl = ttl;
    this.hide = hide;
  }

  public void execute() {
    this.backend.createMessage(account, queue, messageId, body, ttl, hide);
  }

  public CreateMessage hide(long hide) {
    return new CreateMessage(backend, account, queue, messageId, body, ttl, hide);
  }

  public CreateMessage ttl(long ttl) {
    return new CreateMessage(backend, account, queue, messageId, body, ttl, hide);
  }
}
