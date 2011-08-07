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

import org.openstack.burrow.client.methods.CreateMessage;
import org.openstack.burrow.client.methods.DeleteMessage;
import org.openstack.burrow.client.methods.DeleteMessages;
import org.openstack.burrow.client.methods.GetMessage;
import org.openstack.burrow.client.methods.GetMessages;
import org.openstack.burrow.client.methods.UpdateMessage;
import org.openstack.burrow.client.methods.UpdateMessages;

/**
 * A Queue contains an Account name and an Id.  A Queue can create, get, and delete a
 * Message or list of Messages.  Also can update a Message or list of Messages.
 */
public class Queue {
  private Account account;
  private String id;

    /**
     * Constructor for Queue which takes an Account and a Queue name as arguments
     * @param account An Account object for the Queue to be created in
     * @param queue   A String that signifies the Queue's name
     */
  public Queue(Account account, String queue) {
    this.account = account;
    this.id = queue;
  }

    /**
     * Returns a CreateMessage request object with the given message id and message body
     * @param messageId A String that contains the id for the Message to be created
     * @param body      A String that contains the body of the Message to be created
     * @return          A CreateMessage request object
     */
  public CreateMessage createMessage(String messageId, String body) {
    return new CreateMessage(this, messageId, body);
  }

    /**
     * Creates a DeleteMessage request object with the passed in message id
     * @param messageId A String that contains the id for the Message to be deleted
     * @return          A DeleteMessage request object
     */
  public DeleteMessage deleteMessage(String messageId) {
    return new DeleteMessage(this, messageId);
  }

    /**
     * Creates a DeleteMessages request object and takes no arguments
     * @return  A DeleteMessage request object that deletes Messages in this Queue
     */
  public DeleteMessages deleteMessages() {
    return new DeleteMessages(this);
  }

    /**
     * A getter function that returns the Queue's Account
     * @return This Queue's Account
     */
  public Account getAccount() {
    return account;
  }

    /**
     * A getter function that returns the Queue's id
     * @return This Queue's id as a String
     */
  public String getId() {
    return id;
  }

    /**
     * Creates a GetMessage request object with the passed in message id
     * @param messageId A String that contains of the id of Message to be retrieved
     * @return          A GetMessage request object
     */
  public GetMessage getMessage(String messageId) {
    return new GetMessage(this, messageId);
  }

    /**
     * Creates a GetMessages request object
     * @return A GetMessages request object that will retrieve the Messages in this queue
     */
  public GetMessages getMessages() {
    return new GetMessages(this);
  }

    /**
     * Creates an UpdateMessage request object with the passed in message id
     * @param messageId A String that contains of the id of Message to be updated
     * @return          An UpdateMessage request object
     */
  public UpdateMessage updateMessage(String messageId) {
    return new UpdateMessage(this, messageId);
  }

    /**
     * Creates an UpdateMessages request object
     * @return An UpdateMessages request object that will update the Messages
     *         in this queue
     */
  public UpdateMessages updateMessages() {
    return new UpdateMessages(this);
  }
}
