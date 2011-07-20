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
import org.openstack.burrow.client.NoSuchMessageException;
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
   * Create then delete a message, then verify that a second delete fails.
   */
  public void testCreateDeleteMessage() {
    String id = "testCreateDeleteMessage";
    String body = "testCreateDeleteMessageBody";
    queue.createMessage(id, body).execute();
    queue.deleteMessage(id).execute();
    try {
      queue.deleteMessage(id).execute();
      fail("deleteMessage should have failed");
    } catch (NoSuchMessageException e) {
      // This is expected.
    }
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

  /**
   * Create a message with a long hide, check that it is not visible, update it
   * with hide=0 and check that it is visible.
   */
  public void testUpdateHide() {
    String id = "testUpdateHideMessage";
    String body = "testUpdateHideMessageBody";
    queue.createMessage(id, body).setHide(99999).execute();
    Boolean seen_1 = false;
    List<Message> messages_1 = queue.getMessages().execute();
    for (Message message : messages_1) {
      if (message.getId().equals(id))
        seen_1 = true;
    }
    assertFalse(seen_1);
    queue.updateMessage(id).setHide(0).execute();
    Boolean seen_2 = false;
    List<Message> messages_2 = queue.getMessages().execute();
    for (Message message : messages_2) {
      if (message.getId().equals(id))
        seen_2 = true;
    }
    assertTrue(seen_2);
  }
}
