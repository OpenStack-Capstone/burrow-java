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
import java.util.concurrent.ExecutionException;

import org.openstack.burrow.backend.AsyncBackend;
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

/**
 * Unit tests for the Burrow Client.
 */
abstract class AsyncClientTest extends ClientTest {
  protected AsyncBackend asyncBackend;

  protected AsyncClientTest(String testName, AsyncBackend backend) {
    super(testName, backend);
    this.asyncBackend = backend;
  }

  protected Message execute(CreateMessage request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Account> execute(DeleteAccounts request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected Message execute(DeleteMessage request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Message> execute(DeleteMessages request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Queue> execute(DeleteQueues request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Account> execute(GetAccounts request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected Message execute(GetMessage request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Message> execute(GetMessages request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Queue> execute(GetQueues request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected Message execute(UpdateMessage request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }

  protected List<Message> execute(UpdateMessages request) throws Throwable {
    try {
      return asyncBackend.executeAsync(request).get();
    } catch (ExecutionException e) {
      throw e.getCause();
    }
  }
}
