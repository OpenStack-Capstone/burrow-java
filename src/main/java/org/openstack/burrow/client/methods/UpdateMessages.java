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

public class UpdateMessages implements MessageListRequest {
  private String detail;
  private Long hide;
  private Long limit;
  private String marker;
  private Boolean matchHidden;
  private Queue queue;
  private Long ttl;
  private Long wait;

  public UpdateMessages(Queue queue) {
    this.queue = queue;
    this.marker = null;
    this.limit = null;
    this.matchHidden = null;
    this.ttl = null;
    this.hide = null;
    this.detail = null;
    this.wait = null;
  }

  private UpdateMessages(Queue queue, String marker, Long limit, Boolean matchHidden, Long ttl,
      Long hide, String detail, Long wait) {
    this.queue = queue;
    this.marker = marker;
    this.limit = limit;
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

  public Long getLimit() {
    return limit;
  }

  public String getMarker() {
    return marker;
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

  public UpdateMessages withDetail(String detail) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessages withHide(long hide) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessages withLimit(long limit) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessages withMarker(String marker) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessages withMatchHidden(boolean matchHidden) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessages withTtl(long ttl) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

  public UpdateMessages withWait(long wait) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }
}
