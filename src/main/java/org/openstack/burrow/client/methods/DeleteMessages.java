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

public class DeleteMessages {
  private String account;
  private Backend backend;
  private String detail;
  private Long limit;
  private String marker;
  private Boolean matchHidden;
  private String queue;
  private Long wait;

  public DeleteMessages(Backend backend, String account, String queue) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.marker = null;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

  private DeleteMessages(Backend backend, String account, String queue, String marker, Long limit,
      Boolean matchHidden, String detail, Long wait) {
    this.backend = backend;
    this.account = account;
    this.queue = queue;
    this.marker = marker;
    this.matchHidden = matchHidden;
    this.detail = detail;
    this.wait = wait;
  }

  public List<Message> execute() {
    return backend.deleteMessages(account, queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages matchHidden(boolean matchHidden) {
    return new DeleteMessages(backend, account, queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages matchLimit(Long limit) {
    return new DeleteMessages(backend, account, queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages requestDetail(String detail) {
    return new DeleteMessages(backend, account, queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages requestWait(Long wait) {
    return new DeleteMessages(backend, account, queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages withMarker(String marker) {
    return new DeleteMessages(backend, account, queue, marker, limit, matchHidden, detail, wait);
  }
}
