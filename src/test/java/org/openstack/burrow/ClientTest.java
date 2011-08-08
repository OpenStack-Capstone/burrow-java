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

import org.openstack.burrow.backend.AccountNotFoundException;
import org.openstack.burrow.backend.Backend;
import org.openstack.burrow.backend.MessageNotFoundException;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Client;
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

  protected Message execute(CreateMessage request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Account> execute(DeleteAccounts request) throws Throwable {
    return backend.execute(request);
  }

  protected Message execute(DeleteMessage request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Message> execute(DeleteMessages request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Queue> execute(DeleteQueues request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Account> execute(GetAccounts request) throws Throwable {
    return backend.execute(request);
  }

  protected Message execute(GetMessage request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Message> execute(GetMessages request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Queue> execute(GetQueues request) throws Throwable {
    return backend.execute(request);
  }

  protected Message execute(UpdateMessage request) throws Throwable {
    return backend.execute(request);
  }

  protected List<Message> execute(UpdateMessages request) throws Throwable {
    return backend.execute(request);
  }

  /**
   * Create then delete a message, then verify that a second delete fails.
   */
  public void testCreateDeleteMessage() throws Throwable {
    String id = "testCreateDeleteMessage";
    String body = "testCreateDeleteMessageBody";
    execute(queue.createMessage(id, body));
    execute(queue.deleteMessage(id));
    try {
      execute(queue.deleteMessage(id));
      fail("deleteMessage should have failed");
    } catch (MessageNotFoundException e) {
      // This is expected.
    }
  }

  /**
   * Create and delete a hidden message.
   * 
   * @throws Throwable
   */
  public void testCreateDeleteMessageWithMatchHidden() throws Throwable {
    String id = "testCreateDeleteMessage";
    String body = "testCreateDeleteMessageBody";
    execute(queue.createMessage(id, body).withHide((100L)));
    execute(queue.deleteMessage(id).withMatchHidden(true));
    try {
      execute(queue.deleteMessage(id).withMatchHidden(true));
      fail("deleteMessage should have failed");
    } catch (MessageNotFoundException e) {
      // This is expected.
    }
  }

  /**
   * Create and then get a message.
   */
  public void testCreateGetMessage() throws Throwable {
    String id = "testCreateGetMessage";
    String body = "testCreateGetMessageBody";
    execute(queue.createMessage(id, body));
    Message message = execute(queue.getMessage(id));
    assertEquals("Response Message has wrong id", id, message.getId());
    assertEquals("Response Message has wrong body", body, message.getBody());
  }

  /**
   * Create two messages and then verify their presence in getMessages.
   */
  public void testCreateGetMessages() throws Throwable {
    String[] ids = {"testCreateGetMessages1", "testCreateGetMessages2"};
    String body = "testCreateGetMessagesBody";
    execute(queue.createMessage(ids[0], body));
    execute(queue.createMessage(ids[1], body));
    List<Message> messages = execute(queue.getMessages());
    boolean[] seen = scanMessages(messages, ids);
    assertTrue("Expected to see both messages", seen[0] && seen[1]);
  }

  /**
   * Create a message with a TTL.
   */
  public void testCreateGetMessageWithTtl() throws Throwable {
    String id = "testCreateGetMessage";
    String body = "testCreateGetMessageBody";
    int ttl = 100;
    execute(queue.createMessage(id, body).withTtl(ttl));
    Message message = execute(queue.getMessage(id));
    assertEquals("Response Message has wrong body", message.getBody(), body);
    assertTrue("Expected message.getTtl() <= ttl, found message.getTtl()=" + message.getTtl()
        + "  ttl=" + ttl, message.getTtl() <= ttl);
  }

  /**
   * Delete all accounts, then implicitly create two and delete them one at a
   * time.
   */
  public void testDeleteAccounts() throws Throwable {
    String messageId = "testDeleteAccountsMessageId";
    String messageBody = "testDeleteAccountsMessageBody";
    String queueId = "testDeleteAccountsQueueId";
    String accountIds[] = {"testDeleteAccountsId1", "testDeleteAccountsId2"};
    Account accounts[] = {client.Account(accountIds[0]), client.Account(accountIds[1])};
    Queue queues[] = {accounts[0].Queue(queueId), accounts[1].Queue(queueId)};
    boolean seen[];
    // Blindly nuke the entire server.
    execute(client.deleteAccounts());
    // Create the accounts by creating the messages.
    execute(queues[0].createMessage(messageId, messageBody));
    execute(queues[1].createMessage(messageId, messageBody));
    // Delete one account with detail=id.
    seen = scanAccounts(execute(client.deleteAccounts().withLimit(1).withDetail("id")), accountIds);
    assertTrue("Expected to see one message", seen[0] || seen[1]);
    assertFalse("Expected to not see both messages", seen[0] && seen[1]);
    // Delete one account with detail=all.
    seen =
        scanAccounts(execute(client.deleteAccounts().withLimit(1).withDetail("all")), accountIds);
    assertTrue("Expected to see one message", seen[0] || seen[1]);
    assertFalse("Expected to not see both messages", seen[0] && seen[1]);
  }

  /**
   * Delete a message that does not exist.
   */
  public void testDeleteAMessageThatDoesNotExist() throws Throwable {
    String id = "testDeleteAMessageThatDoesNotExist";
    try {
      execute(queue.deleteMessage(id));
      fail("deleteMessage should have failed for a message that does not exist");
    } catch (MessageNotFoundException e) {
      // Expected.
    }
  }

  /**
   * Create a visible and a hidden message, then delete all non-hidden messages
   * in the queue.
   */
  public void testDeleteMessagesWithHide() throws Throwable {
    String[] ids = {"testDeleteMessagesWithHide1", "testDeleteMessagesWithHide2"};
    String body = "testDeleteMessagesBodyWithHide";
    boolean[] seen;
    execute(queue.createMessage(ids[0], body).withHide(9999));
    execute(queue.createMessage(ids[1], body).withHide(0));
    seen = scanMessages(execute(queue.getMessages()), ids);
    assertFalse("Expected to not see hidden message", seen[0]);
    assertTrue("Expected to see non-hidden message", seen[1]);
    execute(queue.deleteMessages().withMatchHidden(false));
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    execute(queue.createMessage("404workaround", "404workaround"));
    seen = scanMessages(execute(queue.getMessages().withMatchHidden(true)), ids);
    assertTrue("Expected to see hidden message", seen[0]);
    assertFalse("Expected to not see deleted message", seen[1]);
  }

  /**
   * Implicitly create two queues, then delete all queues on the account.
   */
  public void testDeleteQueues() throws Throwable {
    String messageId = "testDeleteQueues";
    String queueIds[] = {"testDeleteQueues1", "testDeleteQueues2"};
    Queue queues[] = {account.Queue(queueIds[0]), account.Queue(queueIds[1])};
    String body = "testDeleteQueuesMessageBody";
    boolean[] seenQueues;
    // Create the messages, implicitly creating the queues.
    execute(queues[0].createMessage(messageId, body));
    execute(queues[1].createMessage(messageId, body));
    // Verify that the queues now exist.
    seenQueues = scanQueues(execute(account.getQueues()), queueIds);
    assertTrue("Expected to see queue 0", seenQueues[0]);
    assertTrue("Expected to see queue 1", seenQueues[1]);
    // Delete all queues on the account.
    execute(account.deleteQueues());
    // Verify that no queues exist on the account.
    try {
      execute(account.getQueues());
      fail("getQueues should have raised AccountNotFoundException when no queues exist");
    } catch (AccountNotFoundException e) {
      // This is expected.
    }
  }

  /**
   * Implicitly create two queues then delete them one at a time.
   */
  public void testDeleteQueuesWithDetail() throws Throwable {
    String messageId = "testDeleteQueuesWithDetail";
    String messageBody = "testDeleteQueuesWithDetailMessageBody";
    String queueIds[] = {"testDeleteQueuesWithDetailQueue1", "testDeleteQueuesWithDetailQueue2"};
    Queue queues[] = {account.Queue(queueIds[0]), account.Queue(queueIds[1])};
    boolean[] seenQueues;
    // Blindly delete all queues to clear the account.
    try {
      execute(account.deleteQueues());
    } catch (AccountNotFoundException e) {
      // This is okay.
    }
    // Create the messages, implicitly creating the queues.
    execute(queues[0].createMessage(messageId, messageBody));
    execute(queues[1].createMessage(messageId, messageBody));
    // Verify that the queues now exist.
    seenQueues = scanQueues(execute(account.getQueues()), queueIds);
    assertTrue("Expected to see queue 0", seenQueues[0]);
    assertTrue("Expected to see queue 1", seenQueues[1]);
    // Delete one queue with detail=id and scan for it being one of the queues
    // we just created.
    seenQueues =
        scanQueues(execute(account.deleteQueues().withLimit(1).withDetail("id")), queueIds);
    assertTrue("Expected to see one queue when deleting", seenQueues[0] || seenQueues[1]);
    assertFalse("Expected to not see both queues when deleting", seenQueues[0] && seenQueues[1]);
    // Delete one more queue with detail=all and scan for it being the other one
    // we just created.
    seenQueues =
        scanQueues(execute(account.deleteQueues().withLimit(1).withDetail("id")), queueIds);
    assertTrue("Expected to see one queue", seenQueues[0] || seenQueues[1]);
    assertFalse("Expected to not see both queues", seenQueues[0] && seenQueues[1]);
  }

  /**
   * Implicitly create an account by creating a message.
   */
  public void testGetAccounts() throws Throwable {
    String messageId = "testGetAccountsMessageId";
    String messageBody = "testGetAccountsMessageBody";
    String accountIds[] = {account.getId()};
    boolean seen[];
    // Create a message to implicitly create the queue and account.
    execute(queue.createMessage(messageId, messageBody));
    seen = scanAccounts(execute(client.getAccounts()), accountIds);
    assertTrue("Expected to see created message", seen[0]);
  }

  /**
   * Get a message that does not exist.
   */
  public void testGetAMessageThatDoesNotExist() throws Throwable {
    String id = "testGetAMessageThatDoesNotExist";
    try {
      execute(queue.getMessage(id));
      fail("getMessage should have failed");
    } catch (MessageNotFoundException e) {
      // this is expected.
    }
  }

  /**
   * Create a message, verify the queue exists, then delete all messages in the
   * queue and verify it goes away.
   */
  public void testGetQueuesDeleteMessages() throws Throwable {
    String messageId = "testGetQueues";
    String messageBody = "testGetQueuesMessageBody";
    String queueIds[] = {queue.getId()};
    boolean[] seen;
    execute(queue.createMessage(messageId, messageBody));
    seen = scanQueues(execute(account.getQueues()), queueIds);
    assertTrue("Expected to see queue", seen[0]);
    try {
      execute(queue.deleteMessages().withMatchHidden(true));
      List<Queue> queues = execute(account.getQueues());
      seen = scanQueues(queues, queueIds);
      assertFalse("Expected to not see queue", seen[0]);
    } catch (AccountNotFoundException e) {
      // Expected, if there are no other queues in the account.
    }
  }

  /**
   * Create hidden messages then use updateMessages to reveal them.
   */
  public void testMultipleUpdateHide() throws Throwable {
    String[] ids = {"testMultipleUpdateHideMessage1", "testMultipleUpdateHideMessage2"};
    String body = "testMultipleUpdateHideMessageBody";
    boolean[] seen;
    execute(queue.createMessage(ids[0], body).withHide(9999));
    execute(queue.createMessage(ids[1], body).withHide(9999));
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    execute(queue.createMessage("404workaround", "404workaround"));
    seen = scanMessages(execute(queue.getMessages()), ids);
    assertFalse("Expected to not see message 0", seen[0]);
    assertFalse("Expected to not see message 1", seen[1]);
    execute(queue.updateMessages().withHide(0).withMatchHidden(true));
    seen = scanMessages(execute(queue.getMessages()), ids);
    assertTrue("Expected to see message 0", seen[0]);
    assertTrue("Expected to see message 1", seen[1]);
  }

  /**
   * Create a hidden message then use updateMessage to reveal it.
   */
  public void testUpdateHide() throws Throwable {
    String ids[] = {"testUpdateHideMessage"};
    String body = "testUpdateHideMessageBody";
    boolean[] seen;
    execute(queue.createMessage(ids[0], body).withHide(99999));
    // TODO: Remove when getMessages no longer 404s on queues with only hidden
    // messages!
    execute(queue.createMessage("404workaround", "404workaround"));
    seen = scanMessages(execute(queue.getMessages()), ids);
    assertFalse("Expected to not see hidden message", seen[0]);
    execute(queue.updateMessage(ids[0]).withHide(0));
    seen = scanMessages(execute(queue.getMessages()), ids);
    assertTrue("Expected to see message", seen[0]);
  }
}
