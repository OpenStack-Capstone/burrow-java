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
 * DeleteMessage is a request object that is executed by the client.  A DeleteMessage has several
 * attribute fields including the message id, a direction to match hidden messages or not, and
 * the message's queue.
 */
public class DeleteMessage implements SingleMessageRequest {
  private String id;
  private Boolean matchHidden;
  private Queue queue;

    /**
     * Public Constructor for DeleteMessage.  Takes in a Queue and a message id.
     * @param queue A Queue object to which the Message is supposed to be added
     * @param id    The message id as a String
     */
  public DeleteMessage(Queue queue, String id) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = null;
  }

    /**
     * Private Constructor for DeleteMessage.  Takes in a Queue, a message id, and a boolean
     * to determine if hidden messages should be matched.
     * @param queue A Queue object to which the Message is supposed to be added
     * @param id    The message id as a String
     * @param matchHidden A Boolean that signifies if hidden messages should be matched
     */
  private DeleteMessage(Queue queue, String id, Boolean matchHidden) {
    this.queue = queue;
    this.id = id;
    this.matchHidden = matchHidden;
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
     * A getter function that returns the Queue of the message to be deleted
     * @return The Queue of the specified message
     */
  public Queue getQueue() {
    return queue;
  }

    /**
     * Constructor for DeleteMessage with the matchHidden flag set
     * @param matchHidden The matchHidden flag as a Boolean
     * @return A DeleteMessage request object with matchHidden set
     */
  public DeleteMessage withMatchHidden(boolean matchHidden) {
    return new DeleteMessage(queue, id, matchHidden);
  }
}
