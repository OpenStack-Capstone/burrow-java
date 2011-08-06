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
 * Class Queue
 */
public class Queue {
  private Account account;
  private String id;

    /**
     * Constructor for Queue
     * @param account
     * @param queue
     */
  public Queue(Account account, String queue) {
    this.account = account;
    this.id = queue;
  }

    /**
     *
     * @param messageId
     * @param body
     * @return
     */
  public CreateMessage createMessage(String messageId, String body) {
    return new CreateMessage(this, messageId, body);
  }

    /**
     *
     * @param messageId
     * @return
     */
  public DeleteMessage deleteMessage(String messageId) {
    return new DeleteMessage(this, messageId);
  }

    /**
     *
     * @return
     */
  public DeleteMessages deleteMessages() {
    return new DeleteMessages(this);
  }

    /**
     *
     * @return
     */
  public Account getAccount() {
    return account;
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
     * @param messageId
     * @return
     */
  public GetMessage getMessage(String messageId) {
    return new GetMessage(this, messageId);
  }

    /**
     *
     * @return
     */
  public GetMessages getMessages() {
    return new GetMessages(this);
  }

    /**
     *
     * @param messageId
     * @return
     */
  public UpdateMessage updateMessage(String messageId) {
    return new UpdateMessage(this, messageId);
  }

    /**
     *
     * @return
     */
  public UpdateMessages updateMessages() {
    return new UpdateMessages(this);
  }
}
