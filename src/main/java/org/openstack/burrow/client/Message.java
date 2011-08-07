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
 * A Message contains several attributes including a message body, an id, the Queue the
 * Message resides in, a ttl, and a hide time length.  A Message can get and set these
 * attributes.
 */
public class Message {
  // TODO Add more attributes.
  protected String body;
  protected Long hide;
  protected String id;
  protected Queue queue;
  protected Long ttl;

    /**
     * Protected constructor for Message that takes no arguments.  Sets all attribute
     * fields to null.
     */
  protected Message() {
    this.id = null;
    this.body = null;
    this.ttl = null;
    this.hide = null;
    this.queue = null;
  }

    /**
     * Protected Constructor for Message that takes a SingleMessageRequest as an
     * argument
     * @param request A SingleMessageRequest object that sets the message id and Queue
     */
  protected Message(SingleMessageRequest request) {
    this.id = request.getId();
    this.queue = request.getQueue();
    this.body = null;
    this.ttl = null;
    this.hide = null;
  }

    /**
     * Protected Constructor for Message that takes a MessageListRequest as an
     * argument
     * @param request A SingleMessageRequest object that sets the Queue
     */
  protected Message(MessageListRequest request) {
    this.queue = request.getQueue();
    this.id = null;
    this.body = null;
    this.ttl = null;
    this.hide = null;
  }

    /**
     * A getter function that returns the message body
     * @return The message body as a String
     */
  public String getBody() {
    return body;
  }

    /**
     * A getter function that returns the message's hide
     * @return The message's hide as a Long
     */
  public long getHide() {
    return hide;
  }

    /**
     * A getter function that returns the message id
     * @return The message id as a String
     */
  public String getId() {
    return id;
  }

    /**
     * A getter function that returns the message Queue
     * @return The message queue as a Queue
     */
  public Queue getQueue() {
    return queue;
  }

    /**
     * A getter function that returns the message's ttl
     * @return The message's ttl as a Long
     */
  public long getTtl() {
    return ttl;
  }

    /**
     * A setter function that sets the message body
     * @param body The message body as a String
     */
  protected void setBody(String body) {
    this.body = body;
  }

    /**
     * A setter function that sets the message's hide
     * @param hide The message's hide as a Long
     */
  protected void setHide(Long hide) {
    this.hide = hide;
  }

    /**
     * A setter function that sets the message id
     * @param id The message id as a String
     */
  protected void setId(String id) {
    this.id = id;
  }

    /**
     * A setter function that sets the message Queue
     * @param queue The message queue as a Queue
     */
  protected void setQueue(Queue queue) {
    this.queue = queue;
  }

    /**
     * A setter function that sets the message's ttl
     * @param ttl The message's ttl as a Long
     */
  protected void setTtl(Long ttl) {
    this.ttl = ttl;
  }
}
