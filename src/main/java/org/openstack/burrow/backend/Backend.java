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
import org.openstack.burrow.client.NoSuchMessageException;
import org.openstack.burrow.client.Queue;

public interface Backend {

  /**
   * Create a message with a given id.
   * 
   * @param account Create a message in this account.
   * @param queue Create a message in this queue.
   * @param messageId Create a message with this id.
   * @param body Create a message with this body.
   * @param ttl Optional. Create a message that will remain in the queue for up
   *          to this many seconds.
   * @param hide Optional. Create a message that is hidden for this many
   *          seconds.
   */
  public void createMessage(String account, String queue, String messageId, String body, Long ttl,
      Long hide);

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
   * Delete a message with a known id.
   * 
   * 
   * @param account Delete a message in this account.
   * @param queue Delete a message in this queue.
   * @param messageId Delete a message with this id.
   */
  public void deleteMessage(String account, String queue, String messageId)
      throws NoSuchMessageException;

  /**
   * Delete messages in a queue.
   * 
   * @param account Delete messages in this account.
   * @param queue Delete messages in this queue.
   * @param marker Optional. Delete messages with ids after this marker.
   * @param limit Optional. Delete at most this many messages.
   * @param matchHidden Optional. Delete messages that are hidden.
   * @param detail Optional. Return this level of detail about the deleted
   *          messages.
   * @param wait Optional. Wait up to this many seconds to delete a message if
   *          none would otherwise be deleted.
   * @return A list of Message instances with the requested level of detail, or
   *         null if detail='none'.
   */
  public List<Message> deleteMessages(String account, String queue, String marker, Long limit,
      Boolean matchHidden, String detail, Long wait);

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
   * List accounts.
   * 
   * @param marker Optional. Only accounts with a name after this marker will be
   *          returned.
   * @param limit Optional. Return at most this many accounts.
   * @return A list of Accounts.
   */
  public List<Account> getAccounts(String marker, Long limit);

  /**
   * Get a message with a known id.
   * 
   * @param account Get a message from this account.
   * @param queue Get a message from this queue.
   * @param messageId Get a message with this id.
   * @param detail Return this level of detail about the message.
   * @return A Message instance with the requested level of detail, or null if
   *         detail='none'.
   */
  public Message getMessage(String account, String queue, String messageId, String detail)
      throws NoSuchMessageException;

  /**
   * Get messages from a queue.
   * 
   * @param account Get messages in this account.
   * @param queue Get messages in this queue.
   * @param marker Optional. Get messages with ids after this marker.
   * @param limit Optional. Get at most this many messages.
   * @param matchHidden Optional. Get messages that are hidden.
   * @param detail Optional. Return this level of detail for the messages.
   * @param wait Optional. Wait up to this many seconds to get a message if none
   *          would otherwise be returned.
   * @return A list of Message instances with the requested level of detail.
   */
  public List<Message> getMessages(String account, String queue, String marker, Long limit,
      Boolean matchHidden, String detail, Long wait);

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

  /**
   * Update messages in a queue.
   * 
   * @param account Update messages in this account.
   * @param queue Update messages in this queue.
   * @param marker Optional. Update messages with ids after this marker.
   * @param limit Optional. Update at most this many messages.
   * @param matchHidden Optional. Update messages that are hidden.
   * @param ttl Optional. Update messages to remain in the queue for up to this
   *          many seconds.
   * @param hide Optional. Update messages to be hidden this many seconds.
   * @param detail Optional. Return this level of detail for the updated
   *          messages.
   * @param wait Optional. Wait up to this many seconds to update a message if
   *          none would otherwise be updated.
   * @return A list of updated Message instances with the requested level of
   *         detail, or null if detail='none'.
   */
  public List<Message> updateMessages(String account, String queue, String marker, Long limit,
      Boolean matchHidden, Long ttl, Long hide, String detail, Long wait);
}
