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
import org.openstack.burrow.client.methods.DeleteAccounts;
import org.openstack.burrow.client.methods.DeleteMessage;
import org.openstack.burrow.client.methods.DeleteMessages;
import org.openstack.burrow.client.methods.DeleteQueues;
import org.openstack.burrow.client.methods.GetAccounts;
import org.openstack.burrow.client.methods.GetMessage;
import org.openstack.burrow.client.methods.GetMessages;
import org.openstack.burrow.client.methods.GetQueues;
import org.openstack.burrow.client.methods.UpdateMessage;
import org.openstack.burrow.client.methods.UpdateMessages;

public interface Backend {
  /**
   * Execute a CreateMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the created message, or null if the queue did not
   *         return any information.
   */
  public Message execute(CreateMessage request) throws CommandException, ProtocolException;

  /**
   * Execute a DeleteAccounts request.
   * 
   * @param request The request to execute.
   * @return A list of Account instances populated with any information returned
   *         about the accounts, or null if no information was returned.
   */
  public List<Account> execute(DeleteAccounts request) throws CommandException, ProtocolException;

  /**
   * Execute a DeleteMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the deleted message, or null if the queue did not
   *         return any information.
   */
  public Message execute(DeleteMessage request) throws CommandException, ProtocolException;

  /**
   * Execute a DeleteMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the deleted messages, or null if the queue did
   *         not return any information.
   */
  public List<Message> execute(DeleteMessages request) throws CommandException, ProtocolException;

  /**
   * Execute a DeleteQueues request.
   * 
   * @param request The request to execute.
   * @return A list of Queue instances populated with any information returned
   *         about the queues, or null if no information was returned.
   */
  public List<Queue> execute(DeleteQueues request) throws CommandException, ProtocolException;

  /**
   * Execute a GetAccounts request.
   * 
   * @param request The request to execute.
   * @return A list of Account instances populated with any information returned
   *         about the accounts, or null if no information was returned.
   */
  public List<Account> execute(GetAccounts request) throws CommandException, ProtocolException;

  /**
   * Execute a GetMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the message, or null if the queue did not return any
   *         information.
   */
  public Message execute(GetMessage request) throws CommandException, ProtocolException;

  /**
   * Execute a GetMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the messages, or null if the queue did not
   *         return any information.
   */
  public List<Message> execute(GetMessages request) throws CommandException, ProtocolException;

  /**
   * Execute a GetQueues request.
   * 
   * @param request The request to execute.
   * @return A list of Queue instances populated with any information returned
   *         by the queue about the queues, or null if the queue did not return
   *         any information.
   */
  public List<Queue> execute(GetQueues request) throws CommandException, ProtocolException;

  /**
   * Execute an UpdateMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the message, or null if the queue did not return any
   *         information.
   */
  public Message execute(UpdateMessage request) throws CommandException, ProtocolException;

  /**
   * Execute an UpdateMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the messages, or null if the queue did not
   *         return any information.
   */
  public List<Message> execute(UpdateMessages request) throws CommandException, ProtocolException;
}
