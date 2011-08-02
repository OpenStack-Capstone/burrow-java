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
import org.openstack.burrow.backend.CommandException;
import org.openstack.burrow.backend.ProtocolException;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Client;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;

/**
 * Unit tests for the Burrow Client.
 */
abstract class ClientTest extends TestCase {
  /**
   * Scan a list of accounts for the presence of one or more account ids.
   * 
   * @param ids The account ids to scan for.
   * @return for each id, true if the account id was seen and false otherwise.
   */
  static boolean[] scanAccounts(List<Account> accounts, String[] ids) {
    boolean[] seen = new boolean[ids.length];
    for (int idx = 0; idx < seen.length; idx++)
      seen[idx] = false;
    for (Account a : accounts)
      for (int idx = 0; idx < ids.length; idx++)
        if (a.getId().equals(ids[idx]))
          seen[idx] = true;
    return seen;
  }

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

  /**
   * Scan a list of queues for the presence of one or more queue ids.
   * 
   * @param queues The queues to scan.
   * @param ids The queue ids to scan for.
   * @return for each id, true if the queue id was seen and false otherwise.
   */
  static boolean[] scanQueues(List<Queue> queues, String[] ids) {
    boolean[] seen = new boolean[ids.length];
    for (int idx = 0; idx < seen.length; idx++)
      seen[idx] = false;
    for (Queue queue : queues)
      for (int idx = 0; idx < ids.length; idx++)
        if (queue.getId().equals(ids[idx]))
          seen[idx] = true;
    return seen;
  }

  protected Account account;
  protected Backend backend;
  protected Client client;
  protected Queue queue;

  protected ClientTest(String testName, Backend backend) {
    super(testName);
    this.backend = backend;
    client = new Client();
    account = client.Account("testAccount");
    queue = account.Queue("testQueue");
  }

  /**
   * Create then delete a message, then verify that a second delete fails.
   */
  public void testCreateDeleteMessage() throws ProtocolException {
    String id = "testCreateDeleteMessage";
    String body = "testCreateDeleteMessageBody";
    try {
        backend.execute(queue.createMessage(id, body));
        backend.execute(queue.deleteMessage(id));
    } catch (CommandException ce) {
        fail("Initial create/delete should be successful.");
    }
    try {
      backend.execute(queue.deleteMessage(id));
      fail("deleteMessage should have failed");
    } catch (CommandException ce) {
      // This is expected.
    }
  }

  /**
   * Create and delete a hidden message.
   */
  public void testCreateDeleteMessageWithMatchHidden() throws ProtocolException {
    String id = "testCreateDeleteMessage";
    String body = "testCreateDeleteMessageBody";
    try {
    backend.execute(queue.createMessage(id, body).withHide((100L)));
    backend.execute(queue.deleteMessage(id).withMatchHidden(true));
    } catch (CommandException ce) {
        fail("Initial create/delete should be successful.");
    }
    try {
      backend.execute(queue.deleteMessage(id).withMatchHidden(true));
      fail("deleteMessage should have failed");
    } catch (CommandException ce) {
      // This is expected.
    }
  }

  /**
   * Create and then get a message.
   */
  public void testCreateGetMessage() throws CommandException, ProtocolException {
    String id = "testCreateGetMessage";
    String body = "testCreateGetMessageBody";
    backend.execute(queue.createMessage(id, body));
    Message message = backend.execute(queue.getMessage(id));
    assertEquals(id, message.getId());
    assertEquals(body, message.getBody());
  }

  /**
   * Create two messages and then verify their presence in getMessages.
   */
  public void testCreateGetMessages() throws CommandException, ProtocolException {
    String[] ids = {"testCreateGetMessages1", "testCreateGetMessages2"};
    String body = "testCreateGetMessagesBody";
    backend.execute(queue.createMessage(ids[0], body));
    backend.execute(queue.createMessage(ids[1], body));
    List<Message> messages = backend.execute(queue.getMessages());
    boolean[] seen = scanMessages(messages, ids);
    assertTrue(seen[0] && seen[1]);
  }

  /**
   * Create a message with a TTL.
   */
  public void testCreateGetMessageWithTtl() throws CommandException, ProtocolException {
    String id = "testCreateGetMessage";
    String body = "testCreateGetMessageBody";
    int ttl = 100;
    backend.execute(queue.createMessage(id, body).withTtl(ttl));
    Message message = backend.execute(queue.getMessage(id));
    assertEquals(message.getBody(), body);
    assertTrue("Expected message.getTtl() <= ttl, found message.getTtl()=" + message.getTtl()
        + "  ttl=" + ttl, message.getTtl() <= ttl);
  }

  /**
   * Delete all accounts, then implicitly create two and delete them one at a
   * time.
   */
  public void testDeleteAccounts() throws CommandException, ProtocolException {
    String messageId = "testDeleteAccountsMessageId";
    String messageBody = "testDeleteAccountsMessageBody";
    String queueId = "testDeleteAccountsQueueId";
    String accountIds[] = {"testDeleteAccountsId1", "testDeleteAccountsId2"};
    Account accounts[] = {client.Account(accountIds[0]), client.Account(accountIds[1])};
    Queue queues[] = {accounts[0].Queue(queueId), accounts[1].Queue(queueId)};
    boolean seen[];
    // Blindly nuke the entire server.
    backend.execute(client.deleteAccounts());
    // Create the accounts by creating the messages.
    backend.execute(queues[0].createMessage(messageId, messageBody));
    backend.execute(queues[1].createMessage(messageId, messageBody));
    // Delete one account with detail=id.
    seen =
        scanAccounts(backend.execute(client.deleteAccounts().withLimit(1).withDetail("id")),
            accountIds);
    assertTrue(seen[0] || seen[1]);
    assertFalse(seen[0] && seen[1]);
    // Delete one account with detail=all.
    seen =
        scanAccounts(backend.execute(client.deleteAccounts().withLimit(1).withDetail("all")),
            accountIds);
    assertTrue(seen[0] || seen[1]);
    assertFalse(seen[0] && seen[1]);
  }

  /**
   * Delete a message that does not exist.
   */
  public void testDeleteAMessageThatDoesNotExist() throws ProtocolException {
    String id = "testDeleteAMessageThatDoesNotExist";
    try {
      backend.execute(queue.deleteMessage(id));
      fail("deleteMessage should have failed for a message that does not exist");
    } catch (CommandException e) {
      // Expected.
    }
  }

  /**
   * Create a visible and a hidden message, then delete all non-hidden messages
   * in the queue.
   */
  public void testDeleteMessagesWithHide() throws CommandException, ProtocolException {
    String[] ids = {"testDeleteMessagesWithHide1", "testDeleteMessagesWithHide2"};
    String body = "testDeleteMessagesBodyWithHide";
    boolean[] seen;
    backend.execute(queue.createMessage(ids[0], body).withHide(9999));
    backend.execute(queue.createMessage(ids[1], body).withHide(0));
    seen = scanMessages(backend.execute(queue.getMessages()), ids);
    assertFalse(seen[0]);
    assertTrue(seen[1]);
    backend.execute(queue.deleteMessages().withMatchHidden(false));
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    backend.execute(queue.createMessage("404workaround", "404workaround"));
    seen = scanMessages(backend.execute(queue.getMessages().withMatchHidden(true)), ids);
    assertTrue(seen[0]);
    assertFalse(seen[1]);
  }

  /**
   * Implicitly create two queues, then delete all queues on the account.
   */
  public void testDeleteQueues() throws ProtocolException {
    String messageId = "testDeleteQueues";
    String queueIds[] = {"testDeleteQueues1", "testDeleteQueues2"};
    Queue queues[] = {account.Queue(queueIds[0]), account.Queue(queueIds[1])};
    String body = "testDeleteQueuesMessageBody";
    boolean[] seenQueues;
    try {
    // Create the messages, implicitly creating the queues.
    backend.execute(queues[0].createMessage(messageId, body));
    backend.execute(queues[1].createMessage(messageId, body));
    // Verify that the queues now exist.
    seenQueues = scanQueues(backend.execute(account.getQueues()), queueIds);
    assertTrue(seenQueues[0]);
    assertTrue(seenQueues[1]);
    // Delete all queues on the account.
    backend.execute(account.deleteQueues());
    // Verify that no queues exist on the account.
    } catch (CommandException ce) {
        fail("Initial create/getQueues should be successful.");
    }
    try {
      backend.execute(account.getQueues());
      fail("getQueues should have raised NoSuchAccountException when no queues exist");
    } catch (CommandException e) {
      // This is expected.
    }
  }

  /**
   * Implicitly create two queues then delete them one at a time.
   */
  public void testDeleteQueuesWithDetail() throws CommandException, ProtocolException {
    String messageId = "testDeleteQueuesWithDetail";
    String messageBody = "testDeleteQueuesWithDetailMessageBody";
    String queueIds[] = {"testDeleteQueuesWithDetailQueue1", "testDeleteQueuesWithDetailQueue2"};
    Queue queues[] = {account.Queue(queueIds[0]), account.Queue(queueIds[1])};
    boolean[] seenQueues;
    // Blindly delete all queues to clear the account.
    try {
      backend.execute(account.deleteQueues());
    } catch (CommandException ce) {
      // This is okay.
    }
    // Create the messages, implicitly creating the queues.
    backend.execute(queues[0].createMessage(messageId, messageBody));
    backend.execute(queues[1].createMessage(messageId, messageBody));
    // Verify that the queues now exist.
    seenQueues = scanQueues(backend.execute(account.getQueues()), queueIds);
    assertTrue(seenQueues[0]);
    assertTrue(seenQueues[1]);
    // Delete one queue with detail=id and scan for it being one of the queues
    // we just created.
    seenQueues =
        scanQueues(backend.execute(account.deleteQueues().withLimit(1).withDetail("id")), queueIds);
    assertTrue(seenQueues[0] || seenQueues[1]);
    assertFalse(seenQueues[0] && seenQueues[1]);
    // Delete one more queue with detail=all and scan for it being the other one
    // we just created.
    seenQueues =
        scanQueues(backend.execute(account.deleteQueues().withLimit(1).withDetail("id")), queueIds);
    assertTrue(seenQueues[0] || seenQueues[1]);
    assertFalse(seenQueues[0] && seenQueues[1]);
  }

  /**
   * Implicitly create an account by creating a message.
   */
  public void testGetAccounts() throws CommandException, ProtocolException {
    String messageId = "testGetAccountsMessageId";
    String messageBody = "testGetAccountsMessageBody";
    String accountIds[] = {account.getId()};
    boolean seen[];
    // Create a message to implicitly create the queue and account.
    backend.execute(queue.createMessage(messageId, messageBody));
    seen = scanAccounts(backend.execute(client.getAccounts()), accountIds);
    assertTrue(seen[0]);
  }

  /**
   * Get a message that does not exist.
   */
  public void testGetAMessageThatDoesNotExist() throws ProtocolException {
    String id = "testGetAMessageThatDoesNotExist";
    try {
      backend.execute(queue.getMessage(id));
      fail("getMessage should have failed");
    } catch (CommandException ce) {
      // this is expected.
    }
  }

  /**
   * Create a message, verify the queue exists, then delete all messages in the
   * queue and verify it goes away.
   */
  public void testGetQueuesDeleteMessages() throws ProtocolException {
    String messageId = "testGetQueues";
    String messageBody = "testGetQueuesMessageBody";
    String queueIds[] = {queue.getId()};
    boolean[] seen;
    try {
    backend.execute(queue.createMessage(messageId, messageBody));
    seen = scanQueues(backend.execute(account.getQueues()), queueIds);
    assertTrue(seen[0]);
    } catch (CommandException ce) {
        fail("Initial create/delete should be successful.");
    }
    try {
      backend.execute(queue.deleteMessages().withMatchHidden(true));
      List<Queue> queues = backend.execute(account.getQueues());
      seen = scanQueues(queues, queueIds);
      assertFalse(seen[0]);
    } catch (CommandException ce) {
      // Expected, if there are no other queues in the account.
    }
  }

  /**
   * Create hidden messages then use updateMessages to reveal them.
   */
  public void testMultipleUpdateHide() throws CommandException, ProtocolException {
    String[] ids = {"testMultipleUpdateHideMessage1", "testMultipleUpdateHideMessage2"};
    String body = "testMultipleUpdateHideMessageBody";
    boolean[] seen;
    backend.execute(queue.createMessage(ids[0], body).withHide(9999));
    backend.execute(queue.createMessage(ids[1], body).withHide(9999));
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    backend.execute(queue.createMessage("404workaround", "404workaround"));
    seen = scanMessages(backend.execute(queue.getMessages()), ids);
    assertFalse(seen[0]);
    assertFalse(seen[1]);
    backend.execute(queue.updateMessages().withHide(0).withMatchHidden(true));
    seen = scanMessages(backend.execute(queue.getMessages()), ids);
    assertTrue(seen[0]);
    assertTrue(seen[1]);
  }

  /**
   * Create a hidden message then use updateMessage to reveal it.
   */
  public void testUpdateHide() throws CommandException, ProtocolException {
    String ids[] = {"testUpdateHideMessage"};
    String body = "testUpdateHideMessageBody";
    boolean[] seen;
    backend.execute(queue.createMessage(ids[0], body).withHide(99999));
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    backend.execute(queue.createMessage("404workaround", "404workaround"));
    seen = scanMessages(backend.execute(queue.getMessages()), ids);
    assertFalse(seen[0]);
    backend.execute(queue.updateMessage(ids[0]).withHide(0));
    seen = scanMessages(backend.execute(queue.getMessages()), ids);
    assertTrue(seen[0]);
  }

}
