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
 * GetMessages is a request object
 */
public class GetMessages implements MessageListRequest {
  private String detail;
  private Long limit;
  private String marker;
  private Long hide;
  private Boolean matchHidden;
  private Queue queue;
  private Long wait;

    /**
     * Constructor for GetMessages
     * @param queue
     */
  public GetMessages(Queue queue) {
    this.queue = queue;
    this.marker = null;
    this.limit = null;
    this.hide = null;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

    /**
     * Private Constructor for GetMessages
     * @param queue
     * @param marker
     * @param limit
     * @param matchHidden
     * @param detail
     * @param wait
     */
  private GetMessages(Queue queue, String marker, Long limit, Long hide, Boolean matchHidden, String detail,
      Long wait) {
    this.queue = queue;
    this.marker = marker;
    this.limit = limit;
    this.hide = hide;
    this.matchHidden = matchHidden;
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
  public Long getLimit() {
    return limit;
  }

    /**
     *
     * @return
     */
  public String getMarker() {
    return marker;
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
  public Long getWait() {
    return wait;
  }

    /**
     *
     * @param detail
     * @return
     */
  public GetMessages withDetail(String detail) {
    return new GetMessages(queue, marker, limit, hide, matchHidden, detail, wait);
  }

    /**
     *
     * @param limit
     * @return
     */
  public GetMessages withLimit(long limit) {
    return new GetMessages(queue, marker, limit, hide, matchHidden, detail, wait);
  }

    /**
     *
     * @param marker
     * @return
     */
  public GetMessages withMarker(String marker) {
    return new GetMessages(queue, marker, limit, hide, matchHidden, detail, wait);
  }

    /**
     *
     * @param hide
     * @return
     */
  public GetMessages withHide(long hide) {
    return new GetMessages(queue, marker, limit, hide, matchHidden, detail, wait);
  }

    /**
     *
     * @param matchHidden
     * @return
     */
  public GetMessages withMatchHidden(boolean matchHidden) {
    return new GetMessages(queue, marker, limit, hide, matchHidden, detail, wait);
  }

    /**
     *
     * @param wait
     * @return
     */
  public GetMessages withWait(long wait) {
    return new GetMessages(queue, marker, limit, hide, matchHidden, detail, wait);
  }
}
