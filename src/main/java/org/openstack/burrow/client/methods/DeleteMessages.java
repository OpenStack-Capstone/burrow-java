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
 * DeleteMessages is a request object
 */
public class DeleteMessages implements MessageListRequest {
  private String detail;
  private Long limit;
  private String marker;
  private Boolean matchHidden;
  private Queue queue;
  private Long wait;

    /**
     * Constructor for DeleteMessages
     * @param queue
     */
  public DeleteMessages(Queue queue) {
    this.queue = queue;
    this.marker = null;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

    /**
     * Private Constructor for DeleteMessages
     * @param queue
     * @param marker
     * @param limit
     * @param matchHidden
     * @param detail
     * @param wait
     */
  private DeleteMessages(Queue queue, String marker, Long limit, Boolean matchHidden,
      String detail, Long wait) {
    this.queue = queue;
    this.marker = marker;
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
  public DeleteMessages withDetail(String detail) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

    /**
     *
     * @param limit
     * @return
     */
  public DeleteMessages withLimit(long limit) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

    /**
     *
     * @param marker
     * @return
     */
  public DeleteMessages withMarker(String marker) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

    /**
     *
     * @param matchHidden
     * @return
     */
  public DeleteMessages withMatchHidden(boolean matchHidden) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }

    /**
     *
     * @param wait
     * @return
     */
  public DeleteMessages withWait(long wait) {
    return new DeleteMessages(queue, marker, limit, matchHidden, detail, wait);
  }
}
