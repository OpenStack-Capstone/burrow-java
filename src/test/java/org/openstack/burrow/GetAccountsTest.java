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
import org.openstack.burrow.client.GetAccounts;

/**
 * Unit tests for the Burrow Client.
 */
public class GetAccountsTest extends TestCase {
  private Backend backend = null;

  public GetAccountsTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(GetAccountsTest.class);
  }

  /**
   * Test that a new memory-backed Client has no accounts.
   */
  public void testDeleteMessages() {
    GetAccounts ga = new GetAccounts(backend);
    GetAccounts ga2 = ga.withMarker("marker");
    assertEquals("withMarker should not return the same GetAccounts object", ga, ga2); //test should fail
  }
}
