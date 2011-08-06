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

package org.openstack.burrow.client;

import org.openstack.burrow.client.methods.MessageListRequest;
import org.openstack.burrow.client.methods.SingleMessageRequest;

/**
 * Class Message
 */
public class Message {
  // TODO Add more attributes.
  protected String body;
  protected Long hide;
  protected String id;
  protected Queue queue;
  protected Long ttl;

    /**
     * Protected constructor for Message that takes no arguments
     */
  protected Message() {
    this.id = null;
    this.body = null;
    this.ttl = null;
    this.hide = null;
    this.queue = null;
  }

    /**
     *
     * @param request
     */
  protected Message(SingleMessageRequest request) {
    this.id = request.getId();
    this.queue = request.getQueue();
    this.body = null;
    this.ttl = null;
    this.hide = null;
  }

    /**
     *
     * @param request
     */
  protected Message(MessageListRequest request) {
    this.queue = request.getQueue();
    this.id = null;
    this.body = null;
    this.ttl = null;
    this.hide = null;
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
  public long getHide() {
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
     */
  public Queue getQueue() {
    return queue;
  }

    /**
     *
     * @return
     */
  public long getTtl() {
    return ttl;
  }

    /**
     *
     * @param body
     */
  protected void setBody(String body) {
    this.body = body;
  }

    /**
     *
     * @param hide
     */
  protected void setHide(Long hide) {
    this.hide = hide;
  }

    /**
     *
     * @param id
     */
  protected void setId(String id) {
    this.id = id;
  }

    /**
     *
     * @param queue
     */
  protected void setQueue(Queue queue) {
    this.queue = queue;
  }

    /**
     *
     * @param ttl
     */
  protected void setTtl(Long ttl) {
    this.ttl = ttl;
  }
}
