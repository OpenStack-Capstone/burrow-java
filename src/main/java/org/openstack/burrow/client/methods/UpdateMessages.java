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

import java.util.List;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.Message;

public class UpdateMessages {
  private String account;
  private Backend backend;
  private String detail;
  private Long hide;
  private Long limit;
  private String marker;
  private Boolean matchHidden;
  private String queue;
  private Long ttl;
  private Long wait;

  public UpdateMessages(Backend backend, String account, String queue) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.marker = null;
    this.limit = null;
    this.matchHidden = null;
    this.ttl = null;
    this.hide = null;
    this.detail = null;
    this.wait = null;
  }

  private UpdateMessages(Backend backend, String account, String queue, String marker, Long limit,
      Boolean matchHidden, Long ttl, Long hide, String detail, Long wait) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.marker = marker;
    this.limit = limit;
    this.matchHidden = matchHidden;
    this.ttl = ttl;
    this.hide = hide;
    this.detail = detail;
    this.wait = wait;
  }

  public List<Message> execute() {
    return this.backend.updateMessages(account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages matchHidden(boolean matchHidden) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages matchLimit(Long limit) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages requestDetail(String detail) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages requestWait(long wait) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages setHide(long hide) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages setTtl(long ttl) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }

  public UpdateMessages withMarker(String marker) {
    return new UpdateMessages(backend, account, queue, marker, limit, matchHidden, ttl, hide,
        detail, wait);
  }
}
