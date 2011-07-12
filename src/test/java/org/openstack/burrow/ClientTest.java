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

  public ClientTest(String testName) {
    super(testName);
  }

  /**
   * Create a message.
   */
  public void testCreateMessage() {
    Backend backend = new Http("localhost", 8080);
    Client client = new Client(backend);
    client.Account("newAccount").Queue("newQueue").createMessage("messageId", "messageBody")
        .execute();
  }
}
