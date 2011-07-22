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

package org.openstack.burrow.backend;

import java.util.List;

import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.CreateMessage;
import org.openstack.burrow.client.methods.DeleteMessage;
import org.openstack.burrow.client.methods.DeleteMessages;
import org.openstack.burrow.client.methods.GetMessage;
import org.openstack.burrow.client.methods.GetMessages;
import org.openstack.burrow.client.methods.UpdateMessages;

public interface Backend {
  /**
   * Delete accounts, including the associated queues and messages.
   * 
   * @param marker Optional. Only accounts with a name after this marker will be
   *          deleted.
   * @param limit Optional. Delete at most this many accounts.
   * @param detail Optional. Return the names of the accounts deleted.
   * @return A list of Account instances deleted, with the requested level of
   *         detail.
   */
  public List<Account> deleteAccounts(String marker, Long limit, String detail);

  /**
   * Delete queues, including associated messages.
   * 
   * @param account Delete queues in this account.
   * @param marker Optional. Only queues with a name after this marker will be
   *          deleted.
   * @param limit Optional. At most this many queues will be deleted.
   * @param detail Optional. If true, return the names of the queues deleted.
   * @return A list of Queue instances deleted, with the requested level of
   *         detail.
   */
  public List<Queue> deleteQueues(String account, String marker, Long limit, String detail);

  /**
   * Execute a CreateMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the created message, or null if the queue did not
   *         return any information.
   */
  public Message execute(CreateMessage request);

  /**
   * Execute a DeleteMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the deleted message, or null if the queue did not
   *         return any information.
   */
  public Message execute(DeleteMessage request);

  /**
   * Execute a DeleteMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the deleted messages, or null if the queue did
   *         not return any information.
   */
  public List<Message> execute(DeleteMessages request);

  /**
   * Execute a GetMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the message, or null if the queue did not return any
   *         information.
   */
  public Message execute(GetMessage request);

  /**
   * Execute a GetMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the messages, or null if the queue did not
   *         return any information.
   */
  public List<Message> execute(GetMessages request);

  /**
   * Execute an UpdateMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the messages, or null if the queue did not
   *         return any information.
   */
  public List<Message> execute(UpdateMessages request);

  /**
   * List accounts.
   * 
   * @param marker Optional. Only accounts with a name after this marker will be
   *          returned.
   * @param limit Optional. Return at most this many accounts.
   * @return A list of Accounts.
   */
  public List<Account> getAccounts(String marker, Long limit);

  /**
   * List queues in an account.
   * 
   * @param account List queues in this account.
   * @param marker Optional. Only queues with a name after this marker will be
   *          listed.
   * @param limit Optional. At most this many queues will be listed.
   * @return A list of Queues.
   */
  public List<Queue> getQueues(String account, String marker, Long limit);

  /**
   * Update a message with a known id.
   * 
   * @param account Update a message in this account.
   * @param queue Update a message in this queue.
   * @param messageId Update a message with this id.
   * @param ttl Optional. Update the message to remain in the queue for up to
   *          this many seconds.
   * @param hide Optional. Update the message to be hidden for this many
   *          seconds.
   * @param detail Optional. Return this level of detail about the updated
   *          message.
   * @return An updated Message with the requested level of detail, or null if
   *         detail='none'.
   */
  public Message updateMessage(String account, String queue, String messageId, Long ttl, Long hide,
      String detail);
}
