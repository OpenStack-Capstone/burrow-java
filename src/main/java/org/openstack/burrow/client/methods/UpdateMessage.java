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
import org.openstack.burrow.client.Message;

public class UpdateMessage {
  private String account;
  private Backend backend;
  private String detail;
  private Long hide;
  private String messageId;
  private String queue;
  private Long ttl;

  public UpdateMessage(Backend backend, String account, String queue, String messageId) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.messageId = messageId;
    this.ttl = null;
    this.hide = null;
    this.detail = null;
  }

  private UpdateMessage(Backend backend, String account, String queue, String messageId, Long ttl,
      Long hide, String detail) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.messageId = messageId;
    this.ttl = ttl;
    this.hide = hide;
    this.detail = detail;
  }

  public Message execute() {
    return backend.updateMessage(account, queue, messageId, ttl, hide, detail);
  }

  public UpdateMessage requestDetail(String detail) {
    return new UpdateMessage(backend, account, queue, messageId, ttl, hide, detail);
  }

  public UpdateMessage setHide(long hide) {
    return new UpdateMessage(backend, account, queue, messageId, ttl, hide, detail);
  }

  public UpdateMessage setTtl(long ttl) {
    return new UpdateMessage(backend, account, queue, messageId, ttl, hide, detail);
  }
}
