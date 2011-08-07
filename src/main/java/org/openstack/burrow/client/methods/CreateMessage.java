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
 * CreateMessage is a request object that is executed by the client.  CreateMessage has several
 * attribute fields including the message body, a hide field, a message id, the message's queue
 * and a ttl to optionally set.
 */

public class CreateMessage implements SingleMessageRequest {
  private String body;
  private Long hide;
  private String id;
  private Queue queue;
  private Long ttl;

    /**
     * Public Constructor for CreateMessage.  Takes in a Queue, a message id, and a message body
     * as arguments.
     * @param queue A Queue object to which the Message is supposed to be added
     * @param id    The message id as a String
     * @param body  The message body as a String
     */
  public CreateMessage(Queue queue, String id, String body) {
    this.queue = queue;
    this.id = id;
    this.body = body;
    this.ttl = null;
    this.hide = null;
  }

    /**
     * Private Constructor for CreateMessage. Takes in a Queue, a message id, a message body, a
     * ttl, and a hide as arguments.
     * @param queue A Queue object to which the Message is supposed to be added
     * @param id    The message id as a String
     * @param body  The message body as a String
     * @param ttl   The message ttl as a Long
     * @param hide  The message hide as a Long
     */
  private CreateMessage(Queue queue, String id, String body, Long ttl, Long hide) {
    this.queue = queue;
    this.id = id;
    this.body = body;
    this.ttl = ttl;
    this.hide = hide;
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
  public Long getHide() {
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
  public Long getTtl() {
    return ttl;
  }

    /**
     * Constructor for CreateMessage with the hide time set
     * @param hide The hide time for a message as a Long
     * @return A CreateMessage request object that will hide the message for the set amount of
     *         time
     */
  public CreateMessage withHide(long hide) {
    return new CreateMessage(queue, id, body, ttl, hide);
  }

    /**
     * Constructor for CreateMessage with the ttl set
     * @param ttl The time to live for a message as a Long
     * @return A CreateMessage request object that will the set amount of time for a message to
     *         be alive
     */
  public CreateMessage withTtl(long ttl) {
    return new CreateMessage(queue, id, body, ttl, hide);
  }
}
