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
 * CreateMessage is a request object
 */

public class CreateMessage implements SingleMessageRequest {
  private String body;
  private Long hide;
  private String id;
  private Queue queue;
  private Long ttl;

    /**
     *
     * @param queue
     * @param id
     * @param body
     */
  public CreateMessage(Queue queue, String id, String body) {
    this.queue = queue;
    this.id = id;
    this.body = body;
    this.ttl = null;
    this.hide = null;
  }

    /**
     *
     * @param queue
     * @param id
     * @param body
     * @param ttl
     * @param hide
     */
  private CreateMessage(Queue queue, String id, String body, Long ttl, Long hide) {
    this.queue = queue;
    this.id = id;
    this.body = body;
    this.ttl = ttl;
    this.hide = hide;
  }

    /**
     *
     * @return
     */
  public String getBody() {
    return body;
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
     * @param hide
     * @return
     */
  public CreateMessage withHide(long hide) {
    return new CreateMessage(queue, id, body, ttl, hide);
  }

    /**
     *
     * @param ttl
     * @return
     */
  public CreateMessage withTtl(long ttl) {
    return new CreateMessage(queue, id, body, ttl, hide);
  }
}
