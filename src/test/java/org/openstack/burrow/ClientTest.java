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

package org.openstack.burrow;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.Http;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Client;
import org.openstack.burrow.client.Queue;

/**
 * Unit tests for the Burrow Client.
 */
public class ClientTest extends TestCase {
  public static Test suite() {
    return new TestSuite(ClientTest.class);
  }

  private Account account;
  private Client client;
  private Queue queue;

  public ClientTest(String testName) {
    super(testName);
    Backend backend = new Http("localhost", 8080); // TODO: Parameterize this
    client = new Client(backend);
    account = client.Account("testAccount");
    queue = account.Queue("testQueue");
  }

  /**
   * Create a message.
   */
  public void testCreateMessage() {
    queue.createMessage("messageId", "messageBody").execute();
  }

  /**
   * Create a message with a hide.
   */
  public void testCreateMessageSetHide() {
    queue.createMessage("messageIdWithHide", "messageBody").setHide(900).execute();
  }

  /**
   * Create a message with a ttl.
   */
  public void testCreateMessageSetTtl() {
    queue.createMessage("messageIdWithTtl", "messageBody").setTtl(900).execute();
  }

  /**
   * Create a message with a ttl and a hide.
   */
  public void testCreateMessageSetTtlSetHide() {
    queue.createMessage("messageIdWithHideAndTtl", "messageBody").setTtl(900).setHide(20).execute();
  }

  public void testDeleteMessage() {
    queue.deleteMessage("messageId").execute();
  }

  public void testDeleteMessageMatchHidden() {
    queue.deleteMessage("messageId").matchHidden(true).execute();
  }

  public void testDeleteQueues() {
    account.deleteQueues().execute();
  }

  public void testDeleteQueuesRequestDetail() {
     account.deleteQueues().requestDetail("detail").execute();
  }

  public void testDeleteQueuesWithMarker() {
     account.deleteQueues().withMarker("marker").execute();
  }

  public void testDeleteMessages() {
      queue.deleteMessages().execute();
  }

  public void testDeleteMessagesRequestWait() {
      queue.deleteMessages().requestWait(18932038403L).execute();
  }

  public void testDeleteMessagesRequestDetail() {
      queue.deleteMessages().requestDetail("detail").execute();
  }

  public void testGetAccounts(){
      client.getAccounts().execute();
  }

  public void testGetAccountsMatchLimit(){
      client.getAccounts().matchLimit(1892L).execute();
  }

  public void testGetAccountsWithMarker(){
      client.getAccounts().withMarker("marker").execute();
  }
}
