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

import junit.framework.TestCase;
import org.openstack.burrow.backend.AsyncBackend;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Client;
import org.openstack.burrow.client.Message;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Unit tests for the Burrow Client.
 */
public abstract class AsyncClientTest extends TestCase {
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
     * @param ids      The message ids to scan for.
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
     * @param ids    The queue ids to scan for.
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
    protected AsyncBackend backend;
    protected Client client;
    protected Queue queue;

    protected AsyncClientTest(String testName, AsyncBackend backend) {
        super(testName);
        this.backend = backend;
        client = new Client();
        account = client.Account("testAccount");
        queue = account.Queue("testQueue");
    }

    /**
     * Create then delete a message, then verify that a second delete fails.
     */
    public void testCreateDeleteMessage() throws InterruptedException, ExecutionException {
        String id = "testCreateDeleteMessage";
        String body = "testCreateDeleteMessageBody";

        backend.executeAsync(queue.createMessage(id, body)).get();
        backend.executeAsync(queue.deleteMessage(id)).get();

        try {
            backend.executeAsync(queue.deleteMessage(id)).get();
        } catch (ExecutionException ee) {
            // This is expected.
        }
    }

    /**
     * Create and delete a hidden message.
     */
    public void testCreateDeleteMessageWithMatchHidden() throws InterruptedException, ExecutionException {
        String id = "testCreateDeleteMessage";
        String body = "testCreateDeleteMessageBody";
        backend.executeAsync(queue.createMessage(id, body).withHide((100L))).get();
        backend.executeAsync(queue.deleteMessage(id).withMatchHidden(true)).get();
        try {
            backend.executeAsync(queue.deleteMessage(id).withMatchHidden(true)).get();
            fail("deleteMessage should have failed");
        } catch (ExecutionException ce) {
            // This is expected.
        }
    }

    /**
     * Create and then get a message.
     */
    public void testCreateGetMessage() throws InterruptedException, ExecutionException {
        String id = "testCreateGetMessage";
        String body = "testCreateGetMessageBody";
        backend.executeAsync(queue.createMessage(id, body)).get();
        Message message = backend.executeAsync(queue.getMessage(id)).get();
        assertEquals(id, message.getId());
        assertEquals(body, message.getBody());
    }

    /**
     * Create two messages and then verify their presence in getMessages.
     */
    public void testCreateGetMessages() throws InterruptedException, ExecutionException {
        String[] ids = {"testCreateGetMessages1", "testCreateGetMessages2"};
        String body = "testCreateGetMessagesBody";
        backend.executeAsync(queue.createMessage(ids[0], body));
        backend.executeAsync(queue.createMessage(ids[1], body));
        List<Message> messages = backend.executeAsync(queue.getMessages()).get();
        boolean[] seen = scanMessages(messages, ids);
        assertTrue(seen[0] && seen[1]);
    }

    /**
     * Create a message with a TTL.
     */
    public void testCreateGetMessageWithTtl() throws InterruptedException, ExecutionException {
        String id = "testCreateGetMessage";
        String body = "testCreateGetMessageBody";
        int ttl = 100;
        backend.executeAsync(queue.createMessage(id, body).withTtl(ttl));
        Message message = backend.executeAsync(queue.getMessage(id)).get();
        assertEquals(message.getBody(), body);
        assertTrue("Expected message.getTtl() <= ttl, found message.getTtl()=" + message.getTtl()
                + "  ttl=" + ttl, message.getTtl() <= ttl);
    }

    /**
     * Delete all accounts, then implicitly create two and delete them one at a
     * time.
     */
    public void testDeleteAccounts() throws InterruptedException, ExecutionException {
        String messageId = "testDeleteAccountsMessageId";
        String messageBody = "testDeleteAccountsMessageBody";
        String queueId = "testDeleteAccountsQueueId";
        String accountIds[] = {"testDeleteAccountsId1", "testDeleteAccountsId2"};
        Account accounts[] = {client.Account(accountIds[0]), client.Account(accountIds[1])};
        Queue queues[] = {accounts[0].Queue(queueId), accounts[1].Queue(queueId)};
        boolean seen[];
        // Blindly nuke the entire server.
        backend.executeAsync(client.deleteAccounts());
        // Create the accounts by creating the messages.
        backend.executeAsync(queues[0].createMessage(messageId, messageBody));
        backend.executeAsync(queues[1].createMessage(messageId, messageBody));
        // Delete one account with detail=id.
        seen =
                scanAccounts(backend.executeAsync(client.deleteAccounts().withLimit(1).withDetail("id")).get(),
                        accountIds);
        assertTrue(seen[0] || seen[1]);
        assertFalse(seen[0] && seen[1]);
        // Delete one account with detail=all.
        seen =
                scanAccounts(backend.executeAsync(client.deleteAccounts().withLimit(1).withDetail("all")).get(),
                        accountIds);
        assertTrue(seen[0] || seen[1]);
        assertFalse(seen[0] && seen[1]);
    }

    /**
     * Delete a message that does not exist.
     */
    public void testDeleteAMessageThatDoesNotExist() throws InterruptedException, ExecutionException {
        String id = "testDeleteAMessageThatDoesNotExist";
        try {
            backend.executeAsync(queue.deleteMessage(id)).get();
            fail("deleteMessage should have failed for a message that does not exist");
        } catch (ExecutionException e) {
            // Expected.
        }
    }

    /**
     * Create a visible and a hidden message, then delete all non-hidden messages
     * in the queue.
     */
    public void testDeleteMessagesWithHide() throws InterruptedException, ExecutionException {
        String[] ids = {"testDeleteMessagesWithHide1", "testDeleteMessagesWithHide2"};
        String body = "testDeleteMessagesBodyWithHide";
        boolean[] seen;
        backend.executeAsync(queue.createMessage(ids[0], body).withHide(9999));
        backend.executeAsync(queue.createMessage(ids[1], body).withHide(0));
        seen = scanMessages(backend.executeAsync(queue.getMessages()).get(), ids);
        assertFalse(seen[0]);
        assertTrue(seen[1]);
        backend.executeAsync(queue.deleteMessages().withMatchHidden(false));
        // TODO: Remove when getMessages no longer 404s on queues with only hidden
        // messages!
        backend.executeAsync(queue.createMessage("404workaround", "404workaround"));
        seen = scanMessages(backend.executeAsync(queue.getMessages().withMatchHidden(true)).get(), ids);
        assertTrue(seen[0]);
        assertFalse(seen[1]);
    }

    /**
     * Implicitly create two queues, then delete all queues on the account.
     */
    public void testDeleteQueues() throws InterruptedException, ExecutionException {
        String messageId = "testDeleteQueues";
        String queueIds[] = {"testDeleteQueues1", "testDeleteQueues2"};
        Queue queues[] = {account.Queue(queueIds[0]), account.Queue(queueIds[1])};
        String body = "testDeleteQueuesMessageBody";
        boolean[] seenQueues;
        try {
            // Create the messages, implicitly creating the queues.
            backend.executeAsync(queues[0].createMessage(messageId, body));
            backend.executeAsync(queues[1].createMessage(messageId, body));
            // Verify that the queues now exist.
            seenQueues = scanQueues(backend.executeAsync(account.getQueues()).get(), queueIds);
            assertTrue(seenQueues[0]);
            assertTrue(seenQueues[1]);
            // Delete all queues on the account.
            backend.executeAsync(account.deleteQueues());
            // Verify that no queues exist on the account.
        } catch (ExecutionException ce) {
            fail("Initial create/getQueues should be successful.");
        }
        try {
            backend.executeAsync(account.getQueues()).get();
            fail("getQueues should have raised ExecutionException when no queues exist");
        } catch (ExecutionException e) {
            // This is expected.
        }
    }

    /**
     * Implicitly create two queues then delete them one at a time.
     */
    public void testDeleteQueuesWithDetail() throws InterruptedException, ExecutionException {
        String messageId = "testDeleteQueuesWithDetail";
        String messageBody = "testDeleteQueuesWithDetailMessageBody";
        String queueIds[] = {"testDeleteQueuesWithDetailQueue1", "testDeleteQueuesWithDetailQueue2"};
        Queue queues[] = {account.Queue(queueIds[0]), account.Queue(queueIds[1])};
        boolean[] seenQueues;
        // Blindly delete all queues to clear the account.
        try {
            backend.executeAsync(account.deleteQueues()).get();
        } catch (ExecutionException ce) {
            // This is okay.
        }
        // Create the messages, implicitly creating the queues.
        backend.executeAsync(queues[0].createMessage(messageId, messageBody));
        backend.executeAsync(queues[1].createMessage(messageId, messageBody));
        // Verify that the queues now exist.
        seenQueues = scanQueues(backend.executeAsync(account.getQueues()).get(), queueIds);
        assertTrue(seenQueues[0]);
        assertTrue(seenQueues[1]);
        // Delete one queue with detail=id and scan for it being one of the queues
        // we just created.
        seenQueues =
                scanQueues(backend.executeAsync(account.deleteQueues().withLimit(1).withDetail("id")).get(), queueIds);
        assertTrue(seenQueues[0] || seenQueues[1]);
        assertFalse(seenQueues[0] && seenQueues[1]);
        // Delete one more queue with detail=all and scan for it being the other one
        // we just created.
        seenQueues =
                scanQueues(backend.executeAsync(account.deleteQueues().withLimit(1).withDetail("id")).get(), queueIds);
        assertTrue(seenQueues[0] || seenQueues[1]);
        assertFalse(seenQueues[0] && seenQueues[1]);
    }

    /**
     * Implicitly create an account by creating a message.
     */
    public void testGetAccounts() throws ExecutionException, InterruptedException, ExecutionException {
        String messageId = "testGetAccountsMessageId";
        String messageBody = "testGetAccountsMessageBody";
        String accountIds[] = {account.getId()};
        boolean seen[];
        // Create a message to implicitly create the queue and account.
        backend.executeAsync(queue.createMessage(messageId, messageBody));
        seen = scanAccounts(backend.executeAsync(client.getAccounts()).get(), accountIds);
        assertTrue(seen[0]);
    }

    /**
     * Get a message that does not exist.
     */
    public void testGetAMessageThatDoesNotExist() throws InterruptedException, ExecutionException {
        String id = "testGetAMessageThatDoesNotExist";
        try {
            backend.executeAsync(queue.getMessage(id)).get();
            fail("getMessage should have failed");
        } catch (ExecutionException ce) {
            // this is expected.
        }
    }

    /**
     * Create a message, verify the queue exists, then delete all messages in the
     * queue and verify it goes away.
     */
    public void testGetQueuesDeleteMessages() throws InterruptedException, ExecutionException {
        String messageId = "testGetQueues";
        String messageBody = "testGetQueuesMessageBody";
        String queueIds[] = {queue.getId()};
        boolean[] seen;
        try {
            backend.executeAsync(queue.createMessage(messageId, messageBody));
            seen = scanQueues(backend.executeAsync(account.getQueues()).get(), queueIds);
            assertTrue(seen[0]);
        } catch (ExecutionException ce) {
            fail("Initial create/delete should be successful.");
        }
        try {
            backend.executeAsync(queue.deleteMessages().withMatchHidden(true));
            List<Queue> queues = backend.executeAsync(account.getQueues()).get();
            seen = scanQueues(queues, queueIds);
            assertFalse(seen[0]);
        } catch (ExecutionException ce) {
            // Expected, if there are no other queues in the account.
        }
    }

    /**
     * Create hidden messages then use updateMessages to reveal them.
     */
    public void testMultipleUpdateHide() throws InterruptedException, ExecutionException {
        String[] ids = {"testMultipleUpdateHideMessage1", "testMultipleUpdateHideMessage2"};
        String body = "testMultipleUpdateHideMessageBody";
        boolean[] seen;
        backend.executeAsync(queue.createMessage(ids[0], body).withHide(9999));
        backend.executeAsync(queue.createMessage(ids[1], body).withHide(9999));
        // TODO: Remove when getMessages no longer 404s on queues with only hidden
        // messages!
        backend.executeAsync(queue.createMessage("404workaround", "404workaround"));
        seen = scanMessages(backend.executeAsync(queue.getMessages()).get(), ids);
        assertFalse(seen[0]);
        assertFalse(seen[1]);
        backend.executeAsync(queue.updateMessages().withHide(0).withMatchHidden(true));
        seen = scanMessages(backend.executeAsync(queue.getMessages()).get(), ids);
        assertTrue(seen[0]);
        assertTrue(seen[1]);
    }

    /**
     * Create a hidden message then use updateMessage to reveal it.
     */
    public void testUpdateHide() throws InterruptedException, ExecutionException {
        String ids[] = {"testUpdateHideMessage"};
        String body = "testUpdateHideMessageBody";
        boolean[] seen;
        backend.executeAsync(queue.createMessage(ids[0], body).withHide(99999)).get();
        // TODO: Remove when getMessages no longer 404s on queues with only hidden
        // messages!
        backend.executeAsync(queue.createMessage("404workaround", "404workaround"));
        seen = scanMessages(backend.executeAsync(queue.getMessages()).get(), ids);
        assertFalse(seen[0]);
        backend.executeAsync(queue.updateMessage(ids[0]).withHide(0));
        seen = scanMessages(backend.executeAsync(queue.getMessages()).get(), ids);
        assertTrue(seen[0]);
    }

    /**
     * Make sure backend handles nulls sensibly.
     *
     * IllegalArgumentException/ExecutionExceptions (as appropriate) are to be expected here.
     */
    public void testNullHandling() throws ExecutionException, InterruptedException {
        try {
            backend.executeAsync((CreateMessage) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((DeleteAccounts) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((DeleteMessage) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((DeleteMessages) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((DeleteQueues) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((GetAccounts) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((GetMessage) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((GetMessages) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((GetQueues) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((UpdateMessage) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }
        try {
            backend.executeAsync((UpdateMessages) null).get();
        } catch (IllegalArgumentException e) {
            //Expected
        }

        try {
            backend.executeAsync(queue.createMessage(null, "foo")).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.createMessage("foo", null)).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.createMessage(null, null)).get();
        } catch (ExecutionException e) {
            //Expected
        }

        try {
            backend.executeAsync(queue.getMessage(null).withDetail("foo")).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.getMessage("foo").withDetail(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.getMessage(null).withDetail(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }

        try {
            backend.executeAsync(queue.deleteMessage(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }

        try {
            backend.executeAsync(queue.updateMessage(null).withDetail("foo")).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.updateMessage("foo").withDetail(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.updateMessage(null).withDetail(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }

        try {
            backend.executeAsync(queue.deleteMessages().withDetail(null).withMarker("foo")).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.deleteMessages().withDetail("foo").withMarker(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.deleteMessages().withDetail(null).withMarker(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }

        try {
            backend.executeAsync(queue.updateMessages().withDetail(null).withMarker("foo")).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.updateMessages().withDetail("foo").withMarker(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }
        try {
            backend.executeAsync(queue.updateMessages().withDetail(null).withMarker(null)).get();
        } catch (ExecutionException e) {
            //Expected
        }
    }

}
