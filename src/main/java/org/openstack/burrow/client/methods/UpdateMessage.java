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

/**
 * UpdateMessage is a request object
 */
public class UpdateMessage implements SingleMessageRequest {
  private String detail;
  private Long hide;
  private String id;
  private Boolean matchHidden;
  private Queue queue;
  private Long ttl;
  private Long wait;

    /**
     * Constructor for UpdateMessage
     * @param queue
     * @param messageId
     */
  public UpdateMessage(Queue queue, String messageId) {
    this.queue = queue;
    this.id = messageId;
    this.ttl = null;
    this.hide = null;
    this.detail = null;
  }

    /**
     * Private Constructor for UpdateMessage
     * @param queue
     * @param id
     * @param matchHidden
     * @param ttl
     * @param hide
     * @param detail
     * @param wait
     */
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

    /**
     *
     * @return
     */
  public String getDetail() {
    return detail;
  }

    /**
     *
     * @return
     */
  public Long getHide() {
    return hide;
  }

    /**
     *
     * @return
     */
  public String getId() {
    return id;
  }

    /**
     *
     * @return
     */
  public Boolean getMatchHidden() {
    return matchHidden;
  }

    /**
     *
     * @return
     */
  public Queue getQueue() {
    return queue;
  }

    /**
     *
     * @return
     */
  public Long getTtl() {
    return ttl;
  }

    /**
     *
     * @return
     */
  public Long getWait() {
    return wait;
  }

    /**
     *
     * @param detail
     * @return
     */
  public UpdateMessage withDetail(String detail) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

    /**
     *
     * @param hide
     * @return
     */
  public UpdateMessage withHide(long hide) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

    /**
     *
     * @param matchHidden
     * @return
     */
  public UpdateMessage withMatchHidden(boolean matchHidden) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

    /**
     *
     * @param ttl
     * @return
     */
  public UpdateMessage withTtl(long ttl) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }

    /**
     *
     * @param wait
     * @return
     */
  public UpdateMessage withWait(long wait) {
    return new UpdateMessage(queue, id, matchHidden, ttl, hide, detail, wait);
  }
}
