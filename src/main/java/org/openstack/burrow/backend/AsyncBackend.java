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
import java.util.concurrent.Future;

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

public interface AsyncBackend extends Backend {
  /**
   * Asynchronously executes a CreateMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the created message, or null if the queue did not
   *         return any information.
   */
  public Future<Message> executeAsync(CreateMessage request);

  /**
   * Asynchronously executes a DeleteAccounts request.
   * 
   * @param request The request to execute.
   * @return A list of Account instances populated with any information returned
   *         about the accounts, or null if no information was returned.
   */
  public Future<List<Account>> executeAsync(DeleteAccounts request);

  /**
   * Asynchronously executes a DeleteMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the deleted message, or null if the queue did not
   *         return any information.
   */
  public Future<Message> executeAsync(DeleteMessage request);

  /**
   * Asynchronously executes a DeleteMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the deleted messages, or null if the queue did
   *         not return any information.
   */
  public Future<List<Message>> executeAsync(DeleteMessages request);

  /**
   * Asynchronously executes a DeleteQueues request.
   * 
   * @param request The request to execute.
   * @return A list of Queue instances populated with any information returned
   *         about the queues, or null if no information was returned.
   */
  public Future<List<Queue>> executeAsync(DeleteQueues request);

  /**
   * Asynchronously executes a GetAccounts request.
   * 
   * @param request The request to execute.
   * @return A list of Account instances populated with any information returned
   *         about the accounts, or null if no information was returned.
   */
  public Future<List<Account>> executeAsync(GetAccounts request);

  /**
   * Asynchronously executes a GetMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the message, or null if the queue did not return any
   *         information.
   */
  public Future<Message> executeAsync(GetMessage request);

  /**
   * Asynchronously executes a GetMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the messages, or null if the queue did not
   *         return any information.
   */
  public Future<List<Message>> executeAsync(GetMessages request);

  /**
   * Asynchronously executes a GetQueues request.
   * 
   * @param request The request to execute.
   * @return A list of Queue instances populated with any information returned
   *         by the queue about the queues, or null if the queue did not return
   *         any information.
   */
  public Future<List<Queue>> executeAsync(GetQueues request);

  /**
   * Asynchronously executes an UpdateMessage request.
   * 
   * @param request The request to execute.
   * @return A Message instance populated with any information returned by the
   *         queue about the message, or null if the queue did not return any
   *         information.
   */
  public Future<Message> executeAsync(UpdateMessage request);

  /**
   * Asynchronously executes an UpdateMessages request.
   * 
   * @param request The request to execute.
   * @return A list of Message instances populated with any information returned
   *         by the queue about the messages, or null if the queue did not
   *         return any information.
   */
  public Future<List<Message>> executeAsync(UpdateMessages request);

}
