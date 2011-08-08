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
 * GetMessage is a request object that is executed by the client.  A GetMessage has several
 * attribute fields including a flag to match hidden messages or not, the message's queue,
 * detail - the details of an object to return, limit - the maximum number of messages to match,
 * marker - a range of ids to match against, and wait - the amount of time to wait for a message
 * to appear in a queue, a message id, and how much time to hide the message from other
 * requests after getting it.
 */
public class GetMessage implements SingleMessageRequest {
  private String detail;
  private String id;
  private Boolean matchHidden;
  private Queue queue;
  private Long wait;

    /**
     * Constructor for GetMessage that takes a Queue and a message id as arguments
     * @param queue The Queue to get the message of
     * @param id The message id as a String
     */
  public GetMessage(Queue queue, String id) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = null;
    this.detail = null;
    this.wait = null;
  }

    /**
     * Private Constructor for GetMessage that takes a Queue, a marker, a limit, the matchHidden
     * flag, the detail, a wait time, and a message id as arguments.
     * @param queue A Queue object from which Messages are supposed to be deleted
     * @param id A message Id as a String
     * @param matchHidden A Boolean that signifies if hidden messages should be matched
     * @param detail A String denoting which attributes of a message to return
     * @param wait   A Long denoting the amount of time to wait for a Message to show up in
     *               a queue
     */
  private GetMessage(Queue queue, String id, Boolean matchHidden, String detail, Long wait) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = matchHidden;
    this.detail = detail;
    this.wait = wait;
  }

    /**
     * A getter function that will return which attributes of a message to be sent back on request
     * @return  A String containing the desired attributes
     */
  public String getDetail() {
    return detail;
  }

    /**
     * A getter function that returns the message id
     * @return The message id as a String
     */
  public String getId() {
    return id;
  }

    /**
     * A getter function that returns the matchHidden Boolean
     * @return The matchHidden flag as a Boolean
     */
  public Boolean getMatchHidden() {
    return matchHidden;
  }

    /**
     * A getter function that returns the messages Queue
     * @return The messages queue as a Queue
     */
  public Queue getQueue() {
    return queue;
  }

    /**
     * A getter function that returns the amount of time to wait for a message to appear
     * in the queue
     * @return The amount of wait time as a Long
     */
  public Long getWait() {
    return wait;
  }

    /**
     * Constructor for GetMessage with detail set
     * @param detail The detail as a string
     * @return A GetMessage request object with matchHidden set
     */
  public GetMessage withDetail(String detail) {
    return new GetMessage(queue, id, matchHidden, detail, wait);
  }

    /**
     * Constructor for GetMessage with the matchHidden flag set
     * @param matchHidden The matchHidden flag as a Boolean
     * @return A GetMessage request object with matchHidden set
     */
  public GetMessage withMatchHidden(boolean matchHidden) {
    return new GetMessage(queue, id, matchHidden, detail, wait);
  }

    /**
     * Constructor for GetMessages with the wait set
     * @param wait The amount of time to wait for a message to arrive in a queue
     * @return A GetMessages request object with the wait set
     */
  public GetMessage withWait(long wait) {
    return new GetMessage(queue, id, matchHidden, detail, wait);
  }
}
