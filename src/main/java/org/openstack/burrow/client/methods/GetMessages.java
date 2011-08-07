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
 * GetMessages is a request object that is executed by the client.  A GetMessages has several
 * attribute fields including a flag to match hidden messages or not, the message's queue,
 * detail - the details of an object to return, limit - the maximum number of messages to match,
 * marker - a range of ids to match against, and wait - the amount of time to wait for a message
 * to appear in a queue, and hide - the amount of time to hide the message from other
 * requests after getting it.
 */
public class GetMessages implements MessageListRequest {
    private String detail;
    private Long limit;
    private String marker;
    private Boolean matchHidden;
    private Queue queue;
    private Long hide;
    private Long wait;

    /**
     * Constructor for GetMessages that takes a Queue as an argument
     * @param queue The Queue to get the messages of
     */
    public GetMessages(Queue queue) {
        this.queue = queue;
        this.marker = null;
        this.limit = null;
        this.matchHidden = null;
        this.detail = null;
        this.wait = null;
    }

    /**
     * Private Constructor for GetMessages that takes a Queue, a marker, a limit, the matchHidden
     * flag, the detail, and a wait time as arguments.
     * @param queue A Queue object from which Messages are supposed to be deleted
     * @param matchHidden A Boolean that signifies if hidden messages should be matched
     * @param detail A String denoting which attributes of a message to return
     * @param wait   A Long denoting the amount of time to wait for a Message to show up in
     *               a queue
     * @param hide  The message hide as a Long
     * @param marker A String denoting a range to limit matching of message Ids to
     * @param limit  A Long denoting the number of messages to match
     */
    public GetMessages(Queue queue, String marker, Long limit, Boolean matchHidden, String detail, Long wait, Long hide) {
        this.detail = detail;
        this.limit = limit;
        this.marker = marker;
        this.matchHidden = matchHidden;
        this.queue = queue;
        this.hide = hide;
        this.wait = wait;
    }

    /**
     * A getter function that returns the message's hide time
     * @return The message's hide as a Long
     */
    public Long getHide() {
        return hide;
    }

    /**
     * A getter function that will return which attributes of a message to be sent back on request
     * @return  A String containing the desired attributes
     */
    public String getDetail() {
        return detail;
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
     * A getter function that returns the amount of time to wait for a message to appear
     * in the queue
     * @return The amount of wait time as a Long
     */
    public Long getWait() {
        return wait;
    }

    /**
     * Constructor for GetMessages with a detail filter set
     * @param detail A String denoting which attributes of a message to return
     * @return A GetMessages object with the detail set accordingly
     */
    public GetMessages withDetail(String detail) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    /**
     * Constructor for GetMessages with a limit filter set
     * @param limit A Long denoting the number of messages to match
     * @return A GetMessages object with the limit set accordingly
     */
    public GetMessages withLimit(long limit) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    /**
     * Constructor for GetMessages with a marker filter set
     * @param marker A String denoting a range to limit matching of message Ids to
     * @return A GetMessages object with the marker set accordingly
     */
    public GetMessages withMarker(String marker) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    /**
     * Constructor for GetMessages with the matchHidden flag set
     * @param matchHidden The matchHidden flag as a Boolean
     * @return A GetMessages request object with matchHidden set
     */
    public GetMessages withMatchHidden(boolean matchHidden) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    /**
     * Constructor for GetMessages with the wait set
     * @param wait The amount of time to wait for a message to arrive in a queue
     * @return A GetMessages request object with the wait set
     */
    public GetMessages withWait(long wait) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }

    /**
     * Constructor for GetMessages with the hide time set
     * @param hide The hide time for a message as a Long
     * @return A GetMessages request object that will hide the message for the set amount
     *         of time
     */
    public GetMessages withHide(long hide) {
        return new GetMessages(queue, marker, limit, matchHidden, detail, wait, hide);
    }
}
