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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit tests for the Burrow Client.
 */
public class HttpBackendTest extends TestCase {
  public HttpBackendTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(ClientTest.class);
  }

  /**
   * Test that a new memory-backed Client has no accounts.
   */
  public void testCreateMessage() {
    Backend backend = new Http("127.0.0.1", 8080);
    backend.createMessage("my_account", "my_queue", "my_message_id", "my_message_body", null, null);
    backend.createMessage("my_account", "my_queue", "my_message_id", "my_message_body", null, null);
    backend.createMessage("my_account", "my_queue", "my_message_id", "my_message_body", null, null);
  }
}
