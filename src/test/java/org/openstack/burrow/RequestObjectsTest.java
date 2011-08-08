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

import org.junit.Test;
import org.openstack.burrow.backend.*;
import org.openstack.burrow.backend.http.Http;
import org.openstack.burrow.client.Account;
import org.openstack.burrow.client.Queue;
import org.openstack.burrow.client.methods.*;

import static junit.framework.Assert.fail;
import static org.mockito.Mockito.*;

public class RequestObjectsTest {

    /**
     * Unit test for the CreateMessage request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testCreateMessage() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       CreateMessage cm = mock(CreateMessage.class);
       stub(cm.getId()).toReturn("msgId");
       stub(cm.getBody()).toReturn("msgBdy");
       stub(cm.getQueue()).toReturn(queue);
       stub(cm.getHide()).toReturn(0L);
       stub(cm.getTtl()).toReturn(0L);
       backend.execute(cm);
       verify(cm).getId();
       verify(cm).getBody();
       verify(cm).getQueue();
       verify(cm).getHide();
       verify(cm).getTtl();
    }

    /**
     * Unit test for the DeleteMessage request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testDeleteMessage() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       DeleteMessage dm = mock(DeleteMessage.class);
       stub(dm.getId()).toReturn("msgId");
       stub(dm.getQueue()).toReturn(queue);
       backend.execute(dm);
       verify(dm).getId();
       verify(dm).getQueue();
    }

    /**
     * Unit test for the DeleteMessages request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testDeleteMessages() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       DeleteMessages dms = mock(DeleteMessages.class);
       stub(dms.getDetail()).toReturn("none");
       stub(dms.getQueue()).toReturn(queue);
       stub(dms.getMarker()).toReturn("marker");
       try {
       backend.execute(dms);
       fail();
       } catch (MessageNotFoundException m) {
          //Expected
       }
       verify(dms).getDetail();
       verify(dms).getQueue();
       verify(dms).getMarker();
    }

   /**
     * Unit test for the GetMessage request object.  The goal is to see if the backend is
     * calling all the correct functions.
     */
    @Test
    public void testGetMessage() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       GetMessage gm = mock(GetMessage.class);
       stub(gm.getId()).toReturn("msgId");
       stub(gm.getQueue()).toReturn(queue);
       stub(gm.getWait()).toReturn(100L);
       stub(gm.getDetail()).toReturn("none");
       stub(gm.getMatchHidden()).toReturn(true);
       try {
       backend.execute(gm);
       fail();
       } catch (MessageNotFoundException m) {
          //Expected
       }
       verify(gm).getDetail();
       verify(gm).getQueue();
       verify(gm).getId();
       verify(gm).getMatchHidden();
       verify(gm).getWait();
    }

     /**
      * Unit test for the GetMessages request object.  The goal is to see if the backend is
      * calling all the correct functions.
      */
    @Test
    public void testGetMessages() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       GetMessages gms = mock(GetMessages.class);
       stub(gms.getMarker()).toReturn("marker");
       stub(gms.getQueue()).toReturn(queue);
       //stub(gms.getWait()).toReturn(100L);
       stub(gms.getDetail()).toReturn("none");
       //stub(gms.getMatchHidden()).toReturn(true);
       try {
       backend.execute(gms);
       fail();
       } catch (MessageNotFoundException m) {
          //Expected
       }
       verify(gms).getDetail();
       verify(gms).getQueue();
       verify(gms).getMarker();
       //verify(gms).getMatchHidden();
       //verify(gms).getWait();
    }


   /**
    * Unit test for the UpdateMessage request object.  The goal is to see if the backend is
    * calling all the correct functions.
    */
   @Test
    public void testUpdateMessage() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       UpdateMessage um = mock(UpdateMessage.class);
       stub(um.getTtl()).toReturn(10L);
       stub(um.getQueue()).toReturn(queue);
       stub(um.getWait()).toReturn(0L);
       stub(um.getDetail()).toReturn("none");
       stub(um.getMatchHidden()).toReturn(true);
       stub(um.getId()).toReturn("msgId");
       try {
       backend.execute(um);
       fail();
       } catch (MessageNotFoundException m) {
          //Expected
       }
       verify(um).getDetail();
       verify(um).getQueue();
       verify(um).getId();
       verify(um).getMatchHidden();
       verify(um).getTtl();
       verify(um).getWait();
    }

    /**
     * Unit test for UpdateMessages request object.  Checks if correct methods are called by
     * the backend.
     */
    @Test
    public void testUpdateMessages() throws BurrowException {
       Backend backend = new Http("localhost", 8080);
       Account account = new Account("acct");
       Queue queue = new Queue(account, "queue");
       UpdateMessages ums = mock(UpdateMessages.class);
       stub(ums.getTtl()).toReturn(10L);
       stub(ums.getQueue()).toReturn(queue);
       stub(ums.getWait()).toReturn(0L);
       stub(ums.getDetail()).toReturn("none");
       stub(ums.getMatchHidden()).toReturn(true);
       stub(ums.getMarker()).toReturn("marker");
       stub(ums.getHide()).toReturn(2L);
       stub(ums.getLimit()).toReturn(2L);
       try {
       backend.execute(ums);
       fail();
       } catch (MessageNotFoundException m) {
          //Expected
       }
       verify(ums).getDetail();
       verify(ums).getQueue();
       verify(ums).getHide();
       verify(ums).getMatchHidden();
       verify(ums).getTtl();
       verify(ums).getWait();
       verify(ums).getLimit();
    }

    /**
     * Unit test for DeleteAccounts request object.  Checks if correct methods are called by
     * the backend.
     * @throws BurrowException
     */
    @Test
     public void testDeleteAccounts() throws BurrowException {
        Backend backend = new Http("localhost", 8080);
        DeleteAccounts das = mock(DeleteAccounts.class);
        stub(das.getDetail()).toReturn("none");
        stub(das.getMarker()).toReturn("marker");
        stub(das.getLimit()).toReturn(2L);
        try {
        backend.execute(das);
        fail();
        } catch (AccountNotFoundException m) {
           //Expected
        }
        verify(das).getDetail();
        verify(das).getLimit();
        verify(das).getMarker();
     }

    /**
     * Unit test for GetAccounts request object.  Checks if correct methods are called by
     * the backend.
     * @throws BurrowException
     */
    @Test
     public void testGetAccounts() throws BurrowException {
        Backend backend = new Http("localhost", 8080);
        GetAccounts gas = mock(GetAccounts.class);
        stub(gas.getDetail()).toReturn("none");
        stub(gas.getMarker()).toReturn("marker");
        stub(gas.getLimit()).toReturn(2L);
        try {
        backend.execute(gas);
        fail();
        } catch (AccountNotFoundException m) {
           //Expected
        }
        verify(gas).getDetail();
        verify(gas).getMarker();
        verify(gas).getLimit();
     }

    /**
     * Unit test for DeleteQueues request object.  Checks if correct methods are called by
     * the backend.
     * @throws BurrowException
     */
    @Test
     public void testDeleteQueues() throws BurrowException {
        Backend backend = new Http("localhost", 8080);
        Account account = new Account("acct");
        DeleteQueues dqs = mock(DeleteQueues.class);
        stub(dqs.getDetail()).toReturn("none");
        stub(dqs.getMarker()).toReturn("marker");
        stub(dqs.getLimit()).toReturn(2L);
        stub(dqs.getAccount()).toReturn(account);
        try {
        backend.execute(dqs);
        fail();
        } catch (QueueNotFoundException m) {
           //Expected
        }
        verify(dqs).getDetail();
        verify(dqs).getLimit();
        verify(dqs).getMarker();
        verify(dqs, times(2)).getAccount();
     }

    /**
     *
     * @throws BurrowException
     */
    @Test
     public void testGetQueues() throws BurrowException {
        Backend backend = new Http("localhost", 8080);
        Account account = new Account("acct");
        GetQueues gqs = mock(GetQueues.class);
        stub(gqs.getMarker()).toReturn("marker");
        stub(gqs.getLimit()).toReturn(2L);
        stub(gqs.getAccount()).toReturn(account);
        try {
        backend.execute(gqs);
        fail();
        } catch (QueueNotFoundException m) {
           //Expected
        }
        verify(gqs).getLimit();
        verify(gqs).getMarker();
        verify(gqs, times(2)).getAccount();
     }
}
