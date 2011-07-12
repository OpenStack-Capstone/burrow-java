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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.openstack.burrow.client.DeleteMessages;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the Burrow Client.
 */
public class DeleteMessagesTest extends TestCase {
  private Backend backend = null;

  public DeleteMessagesTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(DeleteMessagesTest.class);
  }

  /**
   * Test that a new memory-backed Client has no accounts.
   */
  public void testDeleteMessages() {
    DeleteMessages dm = new DeleteMessages(backend, "account", "queue");
    DeleteMessages dm2 = dm.matchHidden(false);
    assertEquals("matchHidden should not return same DeleteMessages object", dm, dm2); //test should fail
  }
}
