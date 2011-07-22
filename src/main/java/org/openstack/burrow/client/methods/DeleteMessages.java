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

public class DeleteMessages {
  private String detail;
  private Long limit;
  private String marker;
  private Boolean matchHidden;
  private Queue queue;
  private Long wait;

  public DeleteMessages(Queue queue) {
    this.queue = queue;
    this.marker = null;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

  private DeleteMessages(Queue queue, String marker, Long limit, Boolean matchHidden,
      String detail, Long wait) {
    this.queue = queue;
    this.marker = marker;
    this.matchHidden = matchHidden;
    this.detail = detail;
    this.wait = wait;
  }

  public String getDetail() {
    return detail;
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

  public Long getWait() {
    return wait;
  }

  public DeleteMessages withDetail(String detail) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages withLimit(Long limit) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages withMarker(String marker) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages withMatchHidden(boolean matchHidden) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

  public DeleteMessages withWait(Long wait) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }
}
