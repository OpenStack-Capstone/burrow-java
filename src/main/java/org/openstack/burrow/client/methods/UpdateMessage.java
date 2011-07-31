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

import org.openstack.burrow.client.Queue;

public class UpdateMessage {
  private String detail;
  private Long hide;
  private String id;
  private Boolean matchHidden;
  private Queue queue;
  private Long ttl;
  private Long wait;

  public UpdateMessage(Queue queue, String messageId) {
    this.queue = queue;
    this.id = messageId;
    this.ttl = null;
    this.hide = null;
    this.detail = null;
  }

  private UpdateMessage(Queue queue, String id, Boolean matchHidden, Long ttl, Long hide,
      String detail, Long wait) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = matchHidden;
    this.ttl = ttl;
    this.hide = hide;
    this.detail = detail;
    this.wait = wait;
  }

  public String getDetail() {
    return detail;
  }

  public Long getHide() {
    return hide;
  }

  public String getId() {
    return id;
  }

  public Boolean getMatchHidden() {
    return matchHidden;
  }

  public Queue getQueue() {
    return queue;
  }

  public Long getTtl() {
    return ttl;
  }

  public Long getWait() {
    return wait;
  }

  public UpdateMessage withDetail(String detail) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessage withHide(long hide) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessage withMatchHidden(boolean matchHidden) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessage withTtl(long ttl) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessage withWait(long wait) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }
}
