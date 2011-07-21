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

import junit.framework.TestCase;

import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Client;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.NoSuchMessageException;
import org.openstack.burrow.client.Queue;

/**
 * Unit tests for the Burrow Client.
 */
abstract class ClientTest extends TestCase {
  /**
   * Scan a list of messages for the presence of one or more message ids.
   * 
   * @param messages The messages to scan.
   * @param ids The message ids to scan for.
   * @return for each id, true if the message id was seen and false otherwise.
   */
  static boolean[] scanMessages(List<Message> messages, String[] ids) {
    boolean[] seen = new boolean[ids.length];
    for (int idx = 0; idx < seen.length; idx++)
      seen[idx] = false;
    for (Message message : messages)
      for (int idx = 0; idx < ids.length; idx++)
        if (message.getId().equals(ids[idx]))
          seen[idx] = true;
    return seen;
  }

  protected Account account;
  protected Client client;
  protected Queue queue;

  protected ClientTest(String testName, Backend backend) {
    super(testName);
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
    String[] ids = {"testCreateGetMessages1", "testCreateGetMessages2"};
    String body = "testCreateGetMessagesBody";
    queue.createMessage(ids[0], body).execute();
    queue.createMessage(ids[1], body).execute();
    List<Message> messages = queue.getMessages().execute();
    boolean[] seen = scanMessages(messages, ids);
    assertTrue(seen[0] && seen[1]);
  }

  /**
   * Create a visible and a hidden message, then delete all non-hidden messages
   * in the queue.
   */
  public void testDeleteMessages() {
    String[] ids = {"testDeleteMessages1", "testDeleteMessages2"};
    String body = "testDeleteMessagesBody";
    boolean[] seen;
    queue.createMessage(ids[0], body).setHide(9999).execute();
    queue.createMessage(ids[1], body).setHide(0).execute();
    seen = scanMessages(queue.getMessages().execute(), ids);
    assertFalse(seen[0]);
    assertTrue(seen[1]);
    queue.deleteMessages().matchHidden(false).execute();
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    queue.createMessage("404workaround", "404workaround").execute();
    seen = scanMessages(queue.getMessages().matchHidden(true).execute(), ids);
    assertTrue(seen[0]);
    assertFalse(seen[1]);
  }

  /**
   * Create hidden messages then use updateMessages to reveal them.
   */
  public void testMultipleUpdateHide() {
    String[] ids = {"testMultipleUpdateHideMessage1", "testMultipleUpdateHideMessage2"};
    String body = "testMultipleUpdateHideMessageBody";
    boolean[] seen;
    queue.createMessage(ids[0], body).setHide(9999).execute();
    queue.createMessage(ids[1], body).setHide(9999).execute();
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    queue.createMessage("404workaround", "404workaround").execute();
    seen = scanMessages(queue.getMessages().execute(), ids);
    assertFalse(seen[0]);
    assertFalse(seen[1]);
    queue.updateMessages().setHide(0).matchHidden(true).execute();
    seen = scanMessages(queue.getMessages().execute(), ids);
    assertTrue(seen[0]);
    assertTrue(seen[1]);
  }

  /**
   * Create a hidden message then use updateMessage to reveal it.
   */
  public void testUpdateHide() {
    String ids[] = {"testUpdateHideMessage"};
    String body = "testUpdateHideMessageBody";
    boolean[] seen;
    queue.createMessage(ids[0], body).setHide(99999).execute();
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    queue.createMessage("404workaround", "404workaround").execute();
    seen = scanMessages(queue.getMessages().execute(), ids);
    assertFalse(seen[0]);
    queue.updateMessage(ids[0]).setHide(0).execute();
    seen = scanMessages(queue.getMessages().execute(), ids);
    assertTrue(seen[0]);
  }
}
