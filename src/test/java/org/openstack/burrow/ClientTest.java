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

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.Http;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Client;
import org.openstack.burrow.client.Message;
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
   * Create and then get a message.
   */
  public void testCreateGetMessage() {
    String id = "testCreateGetMessage";
    String body = "testCreateGetMessageBody";
    queue.createMessage(id, body).execute();
    Message message = queue.getMessage(id).execute();
    assertEquals(message.getBody(), body);
  }

  /**
   * Create two messages and then verify their presence in getMessages.
   */
  public void testCreateGetMessages() {
    String id1 = "testCreateGetMessages1";
    String id2 = "testCreateGetMessages2";
    String body = "testCreateGetMessagesBody";
    boolean seen_id1 = false;
    boolean seen_id2 = false;
    queue.createMessage(id1, body).execute();
    queue.createMessage(id2, body).execute();
    List<Message> messages = queue.getMessages().execute();
    for (Message message : messages) {
      if (message.getId().equals(id1))
        seen_id1 = true;
      else if (message.getId().equals(id2))
        seen_id2 = true;
    }
    assertTrue(seen_id1 && seen_id2);
  }
}
