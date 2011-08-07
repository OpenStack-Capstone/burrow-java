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
 * UpdateMessages is a request object that is executed by the client.  An UpdateMessages has several
 * attribute fields including a flag to match hidden messages or not, the message's queue,
 * detail - the details of an object to return, the message's time to live, limit - the number of
 * messages to process, marker - a range of ids to match against, and
 * wait - the amount of time to wait for a message to appear in a queue,
 * and hide - the amount of time to hide the message from other
 * requests after getting it.
 */
public class UpdateMessages implements MessageListRequest {
  private String detail;
  private Long hide;
  private Long limit;
  private String marker;
  private Boolean matchHidden;
  private Queue queue;
  private Long ttl;
  private Long wait;

    /**
     * Public Constructor for UpdateMessages that takes a Queue as an argument
     * @param queue The Queue to update the messages of
     */
  public UpdateMessages(Queue queue) {
    this.queue = queue;
    this.marker = null;
    this.limit = null;
    this.matchHidden = null;
    this.ttl = null;
    this.hide = null;
    this.detail = null;
    this.wait = null;
  }

    /**
     * Private Constructor for UpdateMessages that takes a Queue, a marker, a limit, the matchHidden
     * flag, the detail, a wait time, a message id, and a ttl as arguments.
     * @param queue A Queue object from which Messages are supposed to be deleted
     * @param matchHidden A Boolean that signifies if hidden messages should be matched
     * @param detail A String denoting which attributes of a message to return
     * @param wait   A Long denoting the amount of time to wait for a Message to show up in
     *               a queue
     * @param hide  The message hide as a Long
     * @param ttl   The message ttl as a Long
     * @param marker A String denoting a range to limit matching of message Ids to
     * @param limit  A Long denoting the number of messages to match
     */
  private UpdateMessages(Queue queue, String marker, Long limit, Boolean matchHidden, Long ttl,
      Long hide, String detail, Long wait) {
    this.queue = queue;
    this.marker = marker;
    this.limit = limit;
    this.matchHidden = matchHidden;
    this.ttl = ttl;
    this.hide = hide;
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
     * A getter function that returns the message's hide
     * @return The message's hide as a Long
     */
  public Long getHide() {
    return hide;
  }

    /**
     * A getter function that will return the number of matched messages to be sent back on request
     * @return  A Long signifying the determined limit
     */
  public Long getLimit() {
    return limit;
  }

    /**
     * A getter function that will return the range of message ids to be matched against
     * @return  A String of the message Id to match Ids after
     */
  public String getMarker() {
    return marker;
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
     * A getter function that returns the message's ttl
     * @return The message's ttl as a Long
     */
  public Long getTtl() {
    return ttl;
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
     * Constructor for UpdateMessages with a detail filter set
     * @param detail A String denoting which attributes of a message to return
     * @return A UpdateMessages object with the detail set accordingly
     */
  public UpdateMessages withDetail(String detail) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

    /**
     * Constructor for UpdateMessages with the hide time set
     * @param hide The hide time for a message as a Long
     * @return A UpdateMessages request object that will hide the message for the set amount
     *         of time
     */
  public UpdateMessages withHide(long hide) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

    /**
     * Constructor for UpdateMessages with a limit filter set
     * @param limit A Long denoting the number of messages to match
     * @return A UpdateMessages object with the limit set accordingly
     */
  public UpdateMessages withLimit(long limit) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

    /**
     * Constructor for UpdateMessages with a marker filter set
     * @param marker A String denoting a range to limit matching of message Ids to
     * @return A UpdateMessages object with the marker set accordingly
     */
  public UpdateMessages withMarker(String marker) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

    /**
     * Constructor for UpdateMessages with the matchHidden flag set
     * @param matchHidden The matchHidden flag as a Boolean
     * @return A UpdateMessages request object with matchHidden set
     */
  public UpdateMessages withMatchHidden(boolean matchHidden) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

     /**
     * Constructor for UpdateMessages with the ttl set
     * @param ttl The time to live for a message as a Long
     * @return A UpdateMessages request object that will the set amount of time for a message to
     *         be alive
     */
  public UpdateMessages withTtl(long ttl) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }

    /**
     * Constructor for UpdateMessages with the wait set
     * @param wait The amount of time to wait for a message to arrive in a queue
     * @return A UpdateMessages request object with the wait set
     */
  public UpdateMessages withWait(long wait) {
    return new UpdateMessages(queue, marker, limit, matchHidden, ttl, hide, detail, wait);
  }
}
