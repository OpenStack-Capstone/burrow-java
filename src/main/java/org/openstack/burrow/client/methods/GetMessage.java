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

public class GetMessage implements SingleMessageRequest {
  private String detail;
  private String id;
  private Boolean matchHidden;
  private Queue queue;
  private Long wait;
  private Long hide;

  public GetMessage(Queue queue, String id) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

  private GetMessage(Queue queue, String id, Boolean matchHidden, String detail, Long wait, Long hide) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = matchHidden;
    this.detail = detail;
    this.wait = wait;
    this.hide = hide;
  }

  public String getDetail() {
    return detail;
  }

  public String getId() {
    return id;
  }

    public Long getHide() {
        return hide;
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

  public GetMessage withDetail(String detail) {
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
  }

  public GetMessage withHide(long hide) {
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
  }

  public GetMessage withMatchHidden(boolean matchHidden) {
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
  }

  public GetMessage withWait(long wait) {
    return new GetMessage(queue, id, matchHidden, detail, wait, hide);
  }
}
