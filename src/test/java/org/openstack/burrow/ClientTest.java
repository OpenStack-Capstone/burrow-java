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

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.Http;
import org.openstack.burrow.client.Client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for the Burrow Client.
 */
public class ClientTest extends TestCase {
  public static Test suite() {
    return new TestSuite(ClientTest.class);
  }

  private Client client;

  public ClientTest(String testName) {
    super(testName);
    Backend backend = new Http("localhost", 8080); // TODO: Parameterize this
    client = new Client(backend);
  }

  /**
   * Create a message.
   */
  public void testCreateMessage() {
    client.Account("newAccount").Queue("newQueue").createMessage("messageId", "messageBody")
        .execute();
  }


  public void testDeleteMessage() {
    Backend backend = null; // TODO: TEST AGAINST AN ACTUAL BACKEND
    Client client = new Client(backend);
    client.Account("newAccount").Queue("newQueue").deleteMessage("messageId").execute();
  } 

  public void testDeleteQueues() {
    Backend backend = null; // TODO: TEST AGAINST AN ACTUAL BACKEND
    Client client = new Client(backend);
    client.Account("newAccount").deleteQueues().execute();
  }

  public void testAccount() {
    Backend backend = null; // TODO: TEST AGAINST AN ACTUAL BACKEND
    Client client = new Client(backend);
    client.Account("newAccount").matchLimit(limit).execute();

  /**
   * Create a message with a hide.
   */
  public void testCreateMessageWithHide() {
    client.Account("newAccount").Queue("newQueue")
        .createMessage("messageIdWithHide", "messageBody").setHide(900).execute();
  }

  /**
   * Create a message with a ttl.
   */
  public void testCreateMessageWithTtl() {
    client.Account("newAccount").Queue("newQueue").createMessage("messageIdWithTtl", "messageBody")
        .setTtl(900).execute();
  }

  /**
   * Create a message with a ttl and a hide.
   */
  public void testCreateMessageWithTtlAndHide() {
    client.Account("newAccount").Queue("newQueue").createMessage("messageIdWithHideAndTtl",
        "messageBody").setTtl(900).setHide(20).execute();
>>>>>>> e9ae4e4951f41233a2f5ba98229317de95928c92
  }
}
